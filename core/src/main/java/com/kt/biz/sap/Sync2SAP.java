package com.kt.biz.sap;

import com.kt.biz.bean.SapBillItem;
import com.kt.biz.types.AccountType;
import com.kt.entity.mysql.billing.AccountOperation;
import com.kt.entity.mysql.crm.Customer;
import com.kt.sys.GlobalSettings;
import com.kt.util.MathUtil;
import com.tcl.pi.ZWS_P01_ECC_BSS.Bill.Os_ZFI_BILL_IMP_ReqBindingStub;
import com.tcl.pi.ZWS_P01_ECC_BSS.Bill.Os_ZFI_BILL_IMP_ReqService;
import com.tcl.pi.ZWS_P01_ECC_BSS.Bill.Os_ZFI_BILL_IMP_ReqServiceLocator;
import com.tcl.pi.ZWS_P01_ECC_BSS.Customer.Os_ZFI_CUSTOMER_IMP_ReqBindingStub;
import com.tcl.pi.ZWS_P01_ECC_BSS.Customer.Os_ZFI_CUSTOMER_IMP_ReqService;
import com.tcl.pi.ZWS_P01_ECC_BSS.Customer.Os_ZFI_CUSTOMER_IMP_ReqServiceLocator;
import com.tcl.pi.ZWS_P01_ECC_BSS.Payment.Os_ZFI_PAYMENT_IMP_ReqBindingStub;
import com.tcl.pi.ZWS_P01_ECC_BSS.Payment.Os_ZFI_PAYMENT_IMP_ReqServiceLocator;
import functions.rfc.sap.document.sap_com.*;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import java.math.BigDecimal;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by Vega Zhou on 2016/6/2.
 */
public class Sync2SAP {

    private static final String SUCCESS = "S";

    private static final Logger LOGGER = Logger.getLogger(Sync2SAP.class);


    public static boolean syncPayment(List<AccountOperation> payments) {
        try {
            Os_ZFI_PAYMENT_IMP_ReqServiceLocator locator = new Os_ZFI_PAYMENT_IMP_ReqServiceLocator();
            Os_ZFI_PAYMENT_IMP_ReqBindingStub rpc = (Os_ZFI_PAYMENT_IMP_ReqBindingStub) locator.getHTTP_Port(new URL(GlobalSettings.getSAPPaymentSyncEndpoint()));
            rpc.setUsername(GlobalSettings.getSAPUser());
            rpc.setPassword(GlobalSettings.getSAPPassword());

            ZFI_PAYMENT_IMP req = new ZFI_PAYMENT_IMP();
            req.setT_DATA(convertPayments(payments));
            ZFI_PAYMENT_IMPResponse response = rpc.os_ZFI_PAYMENT_IMP_Req(req);
            if (SUCCESS.equals(response.getRETURN().getTYPE())) {
                return true;
            }
        } catch (Exception e) {
            LOGGER.error("SAP sync payment failed", e);
        }
        return false;
    }


    public static boolean syncCustomer(Customer customer) {
        try {
            Os_ZFI_CUSTOMER_IMP_ReqService locator = new Os_ZFI_CUSTOMER_IMP_ReqServiceLocator();
            Os_ZFI_CUSTOMER_IMP_ReqBindingStub rpc = (Os_ZFI_CUSTOMER_IMP_ReqBindingStub)locator.getHTTP_Port(new URL(GlobalSettings.getSAPCustomerSyncEndpoint()));
            rpc.setUsername(GlobalSettings.getSAPUser());
            rpc.setPassword(GlobalSettings.getSAPPassword());

            ZFI_CUSTOMER_IMP req = new ZFI_CUSTOMER_IMP();
            req.setIT_DATA(convertCustomers(Collections.singletonList(customer)));
            ZFI_CUSTOMER_IMPResponse response = rpc.os_ZFI_CUSTOMER_IMP_Req(req);
            if (SUCCESS.equals(response.getRETURN().getTYPE())) {
                return true;
            }
        } catch (Exception e) {
            LOGGER.error("SAP sync customer failed", e);
        }
        return false;
    }


    public static boolean syncBills(List<SapBillItem> bills) {
        try {
            Os_ZFI_BILL_IMP_ReqService locator = new Os_ZFI_BILL_IMP_ReqServiceLocator();
            Os_ZFI_BILL_IMP_ReqBindingStub rpc = (Os_ZFI_BILL_IMP_ReqBindingStub)locator.getHTTP_Port(new URL(GlobalSettings.getSAPBillSyncEndpoint()));
            rpc.setUsername(GlobalSettings.getSAPUser());
            rpc.setPassword(GlobalSettings.getSAPPassword());
            ZFI_BILL_IMP req = convertBills(bills);
            ZFI_BILL_IMPResponse response = rpc.os_ZFI_BILL_IMP_Req(req);
            if (SUCCESS.equals(response.getRETURN().getTYPE())) {
                return true;
            }
            return false;
        } catch (Exception e) {
            LOGGER.error("SAP sync bill failed", e);
        }
        return false;
    }



    private static ZFI_BILL_IMP convertBills(List<SapBillItem> bills) {
        ZFI_BILL_IMP req  = new ZFI_BILL_IMP();
        List<ZSFI_BILL_IMP> converted = new ArrayList<>(bills.size());
        for (SapBillItem bill : bills) {
            converted.add(convert(bill));
        }
        req.setIT_BILLS(converted.toArray(new ZSFI_BILL_IMP[converted.size()]));
        return req;
    }



    private static ZSFI_BILL_IMP convert(SapBillItem bill) {
        ZSFI_BILL_IMP bean = new ZSFI_BILL_IMP();
        bean.setBYEAR(bill.getAccountPeriod().substring(0, 4));
        bean.setBMONT(bill.getAccountPeriod().substring(4));
        bean.setBILNO(bill.getBillNumber());
//        bean.setBILNO(bill.getSequence());
        bean.setCUSNO(bill.getCustomerCode());
        bean.setITMTY(bill.getFeeType());
        BigDecimal totalAmount =  new BigDecimal(bill.getTotalAmount());
        BigDecimal paidAmount = new BigDecimal(bill.getPaidAmount());
//        if ("KG80110HN7".equals(bill.getCustomerCode())) {
//            // 全周至程
//            //TODO: 2017年4月1日的SAP数据需要加 181.37元
//            totalAmount = totalAmount.subtract(new BigDecimal(18.65f));
//            paidAmount = paidAmount.subtract(new BigDecimal(18.65f));
//        } else if ("MG60274XN9".equals(bill.getCustomerCode())) {
//            totalAmount = new BigDecimal(305.18f);
//            paidAmount = new BigDecimal(305.18f);
//        }
        bean.setAMTTT(MathUtil.scale(totalAmount));
        bean.setAMTPR(MathUtil.scale(paidAmount));
        bean.setAMTAC(MathUtil.scale(new BigDecimal(bill.getUnpaidAmount())));

        return bean;
    }




    private static ZSFI_CUSTOMER_IMP[] convertCustomers(List<Customer> customers) {
        List<ZSFI_CUSTOMER_IMP> beans = new ArrayList<>();
        for (Customer customer : customers) {
            beans.add(convert(customer));
        }
        return beans.toArray(new ZSFI_CUSTOMER_IMP[beans.size()]);
    }

    private static ZSFI_CUSTOMER_IMP convert(Customer customer) {
        ZSFI_CUSTOMER_IMP bean = new ZSFI_CUSTOMER_IMP();
        bean.setKUNNR(customer.getCode());
        bean.setNAME1(customer.getDisplayName());
        bean.setSORTL(customer.getDisplayName());
        bean.setSTRAS(fixNullAddress(customer.getAddress()));
        bean.setTELF1(fixNull(customer.getPhone()));
        bean.setSTCEG(fixNull(customer.getVatNo()));
        bean.setKOINH(fixNull(customer.getBank()));
        bean.setBANKN(fixNull(customer.getBankAccount()));
        bean.setSTATU("02");
        bean.setRELAT(customer.getIsRel() == 1 ? "01" : "02");
//        bean.setRELAT("01");         // "01" : 表示关联公司  "02" 表示非关联公司
        bean.setITYPE(customer.getIsVat() == 1 ? "02" : "01");   // 2 增值税  1 普通
        return bean;
    }

    private static ZSFI_PAYMENT[] convertPayments(List<AccountOperation> payments ) {
        List<ZSFI_PAYMENT> beans = new ArrayList<>();
        for (AccountOperation payment : payments) {
            beans.add(convertPayment(payment));
        }
        return beans.toArray(new ZSFI_PAYMENT[beans.size()]);
    }

    private static ZSFI_PAYMENT convertPayment(AccountOperation payment) {
        ZSFI_PAYMENT bean = new ZSFI_PAYMENT();
        bean.setBELNR(String.valueOf(payment.getSequenceId()));   // 流水号
        bean.setBUDAT(new Date(payment.getOperateTime()));   // 缴费时间
        bean.setCUSNO(payment.getCustomerCode());   // 客户编号
        bean.setKXXZ(AccountType.valueOf(payment.getAccountType()) == AccountType.PREPAID ? "01" : "02");    // 款项性质  1 预付  2 预存
        bean.setAMTTT(MathUtil.scale(new BigDecimal(payment.getCurrentAmount())));   // 金额
        return bean;
    }


    private static String fixNull(String maybeNull) {
        if (StringUtils.isBlank(maybeNull)) {
            return "";
        } else {
            return maybeNull.trim();
        }
    }

    private static String fixNullAddress(String maybeNull) {
        if (StringUtils.isBlank(maybeNull)) {
            return "未提供";
        } else {
            return maybeNull.trim();
        }
    }
}
