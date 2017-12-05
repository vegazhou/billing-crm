package com.kt.service;

import com.kt.common.dbhelp.page.Page;
import com.kt.entity.mysql.crm.ChargeScheme;
import com.kt.entity.mysql.crm.Contract;
import com.kt.entity.mysql.crm.Salesman;
import com.kt.exception.PrimaryPropertyMissingException;
import com.kt.exception.SalesmanHasContractException;
import com.kt.exception.SalesmanNotFoundException;
import com.kt.exception.WafException;
import com.kt.repo.mysql.batch.ContractRepository;
import com.kt.repo.mysql.batch.OrderRepository;
import com.kt.repo.mysql.batch.SalesmanRepository;
import com.kt.session.PrincipalContext;
import com.kt.util.DateUtil;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by Vega Zhou on 2016/6/1.
 */
@Service
public class SalesmanService {

    @Autowired
    SalesmanRepository salesmanRepository;
    @Autowired
    ContractRepository contractRepository;


    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public Salesman createSalesman(String name, String email) throws WafException {
        if (StringUtils.isBlank(name)) {
            throw new PrimaryPropertyMissingException("name missing");
        }

        if (StringUtils.isBlank(email)) {
            throw new PrimaryPropertyMissingException("email missing");
        }

        Salesman salesman = new Salesman();
        salesman.setName(name.trim());
        salesman.setEmail(email.trim().toLowerCase());
        salesman.setEnabled(1);
        salesman.setCreatedDate(DateUtil.now());
        salesman.setCreatedBy(PrincipalContext.getCurrentUserName());
        salesman.setUpdatedDate(DateUtil.now());
        salesman.setUpdatedBy(PrincipalContext.getCurrentUserName());
        return salesmanRepository.save(salesman);
    }

    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public void updateSalesman(String id, String name, String email,int enabled) throws WafException {
        Salesman salesman = findById(id);
        if (StringUtils.isNotBlank(name)) {
            salesman.setName(name);
        }
        if (StringUtils.isNotBlank(email)) {
            salesman.setEmail(email);
        }
        
       
            salesman.setEnabled(enabled);
      
        salesman.setUpdatedDate(DateUtil.now());
        salesman.setUpdatedBy(PrincipalContext.getCurrentUserName());
        salesmanRepository.save(salesman);
    }


    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public void deleteSalesman(String id) throws WafException {
        Salesman salesman = findById(id);
        assertSalesmanHasNoContract(id);
        salesmanRepository.delete(salesman);
    }





    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public void enableSalesman(String id) throws WafException {
        Salesman salesman = findById(id);
        salesman.setEnabled(1);
        salesmanRepository.save(salesman);
    }

    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public void disableSalesman(String id) throws WafException {
        Salesman salesman = findById(id);
        salesman.setEnabled(0);
        salesmanRepository.save(salesman);
    }

    public Salesman findByEmail(String email) throws WafException {
        List<Salesman> salesman = salesmanRepository.findByEmail(email);
        if (salesman.size()==0) {
            throw new SalesmanNotFoundException();
        }
        return salesman.get(0);
    }


    public Salesman findById(String id) throws WafException {
        Salesman salesman = salesmanRepository.findOne(id);
        if (salesman == null) {
            throw new SalesmanNotFoundException();
        }
        return salesman;
    }


    public List<Salesman> listAllEnabledSalesmen() {
        return salesmanRepository.findByEnabledOrderByNameAsc(1);
    }
    
    public Page<Salesman> listAll(int curPage, SearchFilter search) {
        return salesmanRepository.listAll(curPage, search);
    }


    private void assertSalesmanHasNoContract(String salesmanId) throws SalesmanHasContractException {
        Contract contract = contractRepository.findFirstBySalesman(salesmanId);
        if (contract != null) {
            throw new SalesmanHasContractException();
        }
    }
}
