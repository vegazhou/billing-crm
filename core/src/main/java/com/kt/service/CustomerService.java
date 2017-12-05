package com.kt.service;

import java.util.List;

import com.kt.biz.auth.PrivilegeChecker;
import com.kt.biz.customer.CustomerOptionalFields;
import com.kt.biz.customer.CustomerPrimaryFields;
import com.kt.biz.sap.Sync2SAP;
import com.kt.biz.types.AccountType;
import com.kt.biz.types.AgentType;
import com.kt.common.dbhelp.page.Page;
import com.kt.entity.mysql.crm.Contract;
import com.kt.entity.mysql.crm.Customer;
import com.kt.entity.mysql.crm.Product;
import com.kt.exception.*;
import com.kt.repo.mysql.batch.CustomerRepository;
import com.kt.service.billing.AccountService;
import com.kt.session.PrincipalContext;
import com.kt.util.DateUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by Vega Zhou on 2016/3/7.
 */
@Service
public class CustomerService {

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private ContractService contractService;
    @Autowired
    private AccountService accountService;
    @Autowired
    private ProductService productService;

    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public Customer provisionCustomer(CustomerPrimaryFields primaryFields, CustomerOptionalFields optionalFields) throws WafException {
        return provisionCustomer(primaryFields, optionalFields, true);
    }

    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public Customer provisionCustomer(CustomerPrimaryFields primaryFields, CustomerOptionalFields optionalFields, boolean syn2Sap) throws WafException {
        PrivilegeChecker.isOperator();
        primaryFields.validate();
        Customer customer = new Customer();

        primaryFields.fillCustomerEntity(customer);
        optionalFields.fillCustomerEntity(customer);

        customer.setCreateDate(DateUtil.now());
        customer.setLastModifiedDate(DateUtil.now());
        customer.setCreatedBy(PrincipalContext.getCurrentUserName());
        customer.setLastModifiedBy(PrincipalContext.getCurrentUserName());
        customer.setCode(customerRepository.nextCode());
        Customer result = customerRepository.save(customer);

        provisionAccount(result.getPid());

        if (syn2Sap) {
            syncCustomer2SAP(result.getPid());
        }
        return result;
    }


    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public boolean syncCustomer2SAP(String pid) throws WafException {
        Customer customer = findByCustomerId(pid);
        boolean success = Sync2SAP.syncCustomer(customer);
        if (success) {
            customer.setSapSynced(1);
            customerRepository.save(customer);
            return true;
        }
        return false;
    }


    public AgentType getAgentType(String customerId) throws WafException {
        Customer customer = findByCustomerId(customerId);
        return AgentType.valueOf(customer.getAgentType());
    }


    public Customer findByCustomerId(String pid) throws WafException {
        Customer customer = customerRepository.findOne(pid);
        if (customer == null) {
            throw new CustomerNotFoundException();
        }
        return customer;
    }

    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public void deleteByCustomerId(String id) throws WafException {
        PrivilegeChecker.isOperator();
        Customer customer = findByCustomerId(id);
        if (customer == null) {
            throw new CustomerNotFoundException();
        }

        AgentType agentType = AgentType.valueOf(customer.getAgentType());
        if (agentType == AgentType.AGENT) {
            assertAgentHasNoProduct(customer.getPid());
            assertAgentHasNoContract(customer.getPid());
        } else if (agentType == AgentType.RESELLER2) {
            assertAgentHasNoContract(customer.getPid());
        }

        if (contractService.hasContract(id)) {
            throw new CustomerHasSignedContractException();
        }

        //TODO: delete wbxsites if there is any
        customerRepository.delete(customer);
    }


    //仅供测试使用，不要随意调用
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public void deleteByForce(String customerId) throws WafException {
        Customer customer = findByCustomerId(customerId);
        if (customer == null) {
            throw new CustomerNotFoundException();
        }

        if (contractService.hasContract(customerId)) {
            List<Contract> contracts = contractService.findAllContractsByCustomerId(customerId);
            for (Contract contract : contracts) {
                contractService.deleteByForce(contract.getPid());
            }
        }

        customerRepository.delete(customer);
    }


    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public void updateCustomer(String id, CustomerPrimaryFields newPrimary, CustomerOptionalFields newOptional) throws WafException {
        PrivilegeChecker.isOperator();
        Customer customer = findByCustomerId(id);
        String agentType = customer.getAgentType();
        newPrimary.fillCustomerEntity(customer);
        newOptional.fillCustomerEntity(customer);
        customer.setAgentType(agentType);
        customer.setLastModifiedDate(DateUtil.now());
        customer.setSapSynced(0);
        customerRepository.save(customer);

        syncCustomer2SAP(customer.getPid());
    }

    private void provisionAccount(String customerId) {
        accountService.findOrCreate(customerId, AccountType.PREPAID);
        accountService.findOrCreate(customerId, AccountType.DEPOSIT);
    }

    public List<Customer> listAllVat2Customers() {
        return customerRepository.findByIsVat(2);
    }

    public List<Customer> listAllVat1EmptyVatNoCustomers() {
        return customerRepository.findByIsVatAndVatNo(1, null);
    }
    
    
    public Page<Customer> listAllNonAgentByPage(int curPage, SearchFilter search) {
        return customerRepository.listAllNonAgentCustomers(curPage, search);
    }

    public Page<Customer> listAllAgentsByPage(int curPage, SearchFilter search) {
        return customerRepository.listAllAgentCustomers(curPage, search);
    }

    public Page<Customer> listAllReseller2ByPage(int curPage, SearchFilter search) {
        return customerRepository.paginateReseller2(curPage, search);
    }

    public List<Customer> listAllAgentsAndReseller2() {
        return customerRepository.listAllAgentsAndReseller2();
    }

    public List<Customer> listAllAgents() {
        return customerRepository.listAllAgents();
    }
    
    public List<Customer> listAllCustomerByList() {
        return customerRepository.findAll();
    }

    public Customer findFirstCustomerByCode(String code){
        return customerRepository.findFirstCustomerByCode(code);
    }

    public List<Customer> findAllRelatedCustomers() {
        return customerRepository.findByIsRel(1);
    }

    private void assertAgentHasNoProduct(String agentId) throws WafException {
        List<Product> products = productService.findAgentProducts(agentId);
        if (products.size() > 0) {
            throw new AgentHasAgentProduct();
        }
    }

    private void assertAgentHasNoContract(String agentId) throws AgentHasContract {
        List<Contract> contracts = contractService.findAgentContracts(agentId);
        if (contracts.size() > 0) {
            throw new AgentHasContract();
        }
    }
}
