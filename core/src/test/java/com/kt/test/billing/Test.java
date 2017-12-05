package com.kt.test.billing;

import com.csvreader.CsvReader;
import com.itextpdf.text.DocumentException;
import com.kt.biz.bean.FormalBillDetailBean;
import com.kt.biz.bean.SapBillItem;
import com.kt.biz.billing.AccountPeriod;
import com.kt.biz.billing.BillItem;
import com.kt.biz.model.charge.impl.WebExConfMonthlyPayByHosts;
import com.kt.biz.model.charge.impl.WebExConfMonthlyPayByPorts;
import com.kt.biz.sap.SapPdf;
import com.kt.biz.sap.Sync2SAP;
import com.kt.biz.types.AccountType;
import com.kt.biz.types.PayInterval;
import com.kt.common.dbhelp.page.Page;
import com.kt.entity.mysql.billing.BillFormalDetail;
import com.kt.entity.mysql.billing.CustomerAccount;
import com.kt.entity.mysql.crm.Contract;
import com.kt.entity.mysql.crm.Customer;
import com.kt.entity.mysql.crm.Order;
import com.kt.exception.FormalBillNotFoundException;
import com.kt.exception.OrderCollisionsDetectedException;
import com.kt.exception.OrderIncompleteException;
import com.kt.exception.WafException;
import com.kt.repo.mysql.batch.ChargeSchemeRepository;
import com.kt.repo.mysql.batch.ContractRepository;
import com.kt.repo.mysql.batch.CustomerRepository;
import com.kt.repo.mysql.batch.WebExSiteRepository;
import com.kt.repo.mysql.billing.BillFormalDetailRepository;
import com.kt.service.*;
import com.kt.service.billing.AccountService;
import com.kt.service.billing.BillService;
import com.kt.service.mail.MailService;
import com.kt.service.telerate.TeleRateImportService;
import com.kt.util.DateUtil;
import com.kt.util.MathUtil;
import org.apache.commons.lang.StringUtils;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import wang.huaichao.data.entity.crm.MeetingRecord;
import wang.huaichao.data.mongo.BillPdfRepository;
import wang.huaichao.data.repo.MeetingRecordRepository;
import wang.huaichao.data.service.BillingService;
import wang.huaichao.exception.BillingException;
import wang.huaichao.exception.InvalidBillingOperationException;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.nio.charset.Charset;
import java.text.ParseException;
import java.util.*;
import java.util.concurrent.ExecutionException;

/**
 * Created by Vega Zhou on 2016/3/30.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration
public class Test {
    @Configuration
    @ComponentScan(basePackages = {"com.kt"})
    static class ContextConfiguration {
    }

    @Autowired
    ContractService contractService;
    @Autowired
    ContractRepository contractRepository;
    @Autowired
    OrderService orderService;
    @Autowired
    ChargeSchemeService chargeSchemeService;
    @Autowired
    WebExSiteService webExSiteService;
    @Autowired
    ProductService productService;
    @Autowired
    OrgUserService orgUserService;
    @Autowired
    BillService billService;
    @Autowired
    AccountService accountService;
    @Autowired
    PstnRateService pstnRateService;

    @Autowired
    CustomerRepository customerRepository;
    @Autowired
    SalesmanService salesmanService;
    @Autowired
    CustomerService customerService;

    @Autowired
    WebExSiteRepository webExSiteRepository;
    @Autowired
    MailService mailService;
    @Autowired
    TeleRateImportService teleRateImportService;
    @Autowired
    ChargeSchemeRepository chargeSchemeRepository;
    @Autowired
    SapReportService sapReportService;
    @Autowired
    SapSyncService sapSyncService;
    @Autowired
    MakeupService makeupService;
    @Autowired
    public BillPdfRepository billPdfRepository;
    @Autowired
    private MeetingRecordRepository meetingRecordRepository;
    @Autowired
    BillFormalDetailRepository billFormalDetailRepository;

    @Autowired
    private BillingService billingService;



    @org.junit.Test
    public void test() throws WafException, OrderIncompleteException, OrderCollisionsDetectedException, ParseException, IOException {

        return;
    }


    @org.junit.Test
    public void prepare() throws WafException, ParseException, OrderCollisionsDetectedException, OrderIncompleteException, FileNotFoundException {

    }









    @org.junit.Test
    public void chargeFirstInstallmentBill() throws WafException {
        orderService.deleteByForce("0fb68961-3055-42a7-a4de-3e399061cd09");
    }

    @org.junit.Test
    public void chargePostpaidBill() {
        List<MeetingRecord> records = meetingRecordRepository.findByCustomerIdAndBillingPeriodJoinPstnCode("8143", 201702);
        return;
    }


    @org.junit.Test
    public void calcAgentRebate() throws ParseException, WafException {
//        Contract c = contractService.findByContractId("debea469-6648-4c7d-aefd-38184f3ded07");
//        BigDecimal refund = contractService.calcTotalRefundAmount(c);
        BillItem refund = billService.calcRefund("409b153f-3402-41ef-9634-e79751927d28", DateUtil.toDate("2017-10-01 00:00:00"));
        try {
            BillFormalDetail bill =
                    billService.findFormalBill("40d7090c-4dae-489d-a39e-b0780ea6757a", refund.getOrderId(), refund.getAccountPeriod(), refund.getFeeType());
            BigDecimal currentAmount = new BigDecimal(bill.getAmount());
            BigDecimal currentUnpaidAmount = new BigDecimal(bill.getUnpaidAmount());
            BigDecimal currentPaidAmount = currentAmount.subtract(currentUnpaidAmount);
            BigDecimal newAmount = currentAmount.add(refund.getAmount());
//                    if (currentPaidAmount.compareTo(newAmount) > 0) {
            BigDecimal refundAmount = currentPaidAmount.subtract(newAmount);
            int a = 1;
//                    }
        } catch (FormalBillNotFoundException ignore) {
            ignore.printStackTrace();
        }
        return;
    }



    @org.junit.Test
    public void synSapBills() throws ParseException, WafException {
//        Customer customer = customerService.findFirstCustomerByCode("KT01057");
//        Sync2SAP.syncCustomer(customer);
//        List<Customer> customers = customerService.listAllCustomerByList();
//        for (Customer customer : customers) {
//            Sync2SAP.syncCustomer(customer);
//        }

//        List<Customer> customers = customerService.findAllRelatedCustomers();
//        for (Customer customer : customers) {
//            Sync2SAP.syncCustomer(customer);
//        }
//        return;

//        Customer customer = customerService.findFirstCustomerByCode("KS80292HN1");
//        Sync2SAP.syncCustomer(customer);
//        return;

//        List<Customer> customers = customerService.findAllRelatedCustomers();
//
//        return;


//        List<MeetingRecord> records = meetingRecordRepository.findByCustomerIdAndBillingPeriod("d671beff-8977-41a4-9ca0-da519cc5c17a", new AccountPeriod("201611").toInt());

//        sapSyncService.syncSapPrepaidBills(new AccountPeriod("201711"));
//        sapSyncService.syncSapPostpaidBills(new AccountPeriod("201710"));
        sapSyncService.synSapPayments();

//        List<SapBillItem> bills  = sapReportService.findSapPostpaidBillsByAccountPeriod(new AccountPeriod("201707"),
//                Arrays.asList("KT01844"));
//        List<SapBillItem> bills  = sapReportService.findSapPrepaidBillsByAccountPeriod(new AccountPeriod("201707"),
//                Arrays.asList("KT01844"));


//        for (SapBillItem bill : bills) {
//            Sync2SAP.syncBills(Collections.singletonList(bill));
//        }
        return;

//        SapBillItem creditNote = createCreditNote();
//        sapReportService.synSapCreditNote(creditNote);
//        return;


//        Customer c = customerService.findByCustomerId("6c108769-5582-4046-bae2-3c5ea30ef9fd");
//        Sync2SAP.syncCustomer(c);
//        sapReportService.synSapPayments(new AccountPeriod("201609"));
    }

    private SapBillItem createCreditNote() throws ParseException {
        SapBillItem creditNote = new SapBillItem();
        String customerCode = "KG80276HN2";
        Customer customer = customerService.findFirstCustomerByCode(customerCode);
        creditNote.setCustomerName(customer.getDisplayName());
        creditNote.setCustomerCode(customerCode);
        creditNote.setAccountPeriod("201611");
        creditNote.setBillNumber("KG80276HN2-20161101-1-C");
        creditNote.setFeeType("01");
        creditNote.setTotalAmount(-360f);
        creditNote.setUnpaidAmount(0f);
        creditNote.setPaidAmount(-360f);
        return creditNote;
    }


    private static final String MC25_TRIAL = "9ca59a3d-f2b1-4feb-89b8-1a3e23c1eb0a";
    private static final String MC8 = "d9cb6790-e555-4e9b-85bb-8b1a66d2f31f";
    private static final String MC50 = "33e398a9-eec7-431b-9bb0-fb8961d5752e";
    private static final String MC25 = "00c25fd3-f7d2-458a-98eb-5b43340bd93b";
    private static final String MC100 = "7539bed6-e931-401a-b5d4-e8b73ca2022c";

    private static final String MC1_50 = "3aebbd39-797f-40f5-a6e1-279a16689abd";

    private static final String PSTN25 = "ec8a66b3-5a12-4197-bfc5-a1e23fc022c4";
    private static final String PSTN26 = "10f3e717-6c2d-4a06-9a1d-cb8b825eae01";
    private static final String PSTN30 = "58151e11-9432-46ec-9800-8ede1a895718";
    private static final String PSTN36 = "af44d185-b05c-419f-8333-1d864e4f6950";
    private static final String PSTN_7ZHE = "b1d96ccf-6ac9-4e01-928c-eb8057839885";

    @org.junit.Test
    public void makeupContracts() throws Exception {


        return;

//        makeupService.makeupPTContract("4590", "MC1-50 kaiquan", "120711", MC1_50, PSTN36, DateUtil.toShortDate("2016-09-18"), PayInterval.ONE_TIME,
//                makeByPorts("kaiquan", 12, 10, 60f, 70f));
    }

    private WebExConfMonthlyPayByHosts makeByHosts(String siteName, int month, int hosts, float unitPrice) {
        WebExConfMonthlyPayByHosts charge = new WebExConfMonthlyPayByHosts();
        charge.setMonths(month);
        charge.setHosts(hosts);
        charge.setUnitPrice(unitPrice);
        charge.setSiteName(siteName);
        return charge;
    }

    private WebExConfMonthlyPayByPorts makeByPorts(String siteName, int month, int ports, float unitPrice, float overflowUnitPrice) {
        WebExConfMonthlyPayByPorts charge = new WebExConfMonthlyPayByPorts();
        charge.setMonths(month);
        charge.setPorts(ports);
        charge.setUnitPrice(unitPrice);
        charge.setOverflowUnitPrice(overflowUnitPrice);
        charge.setSiteName(siteName);
        return charge;
    }


    private SapPdf dump(AccountPeriod accountPeriod, String customerCode, List<SapBillItem> buf) throws ParseException, IOException, DocumentException {
        if (StringUtils.isBlank(customerCode) || buf.size() == 0) {
            return null;
        }
        SapPdf pdf = new SapPdf();
        Customer customer = customerService.findFirstCustomerByCode(customerCode);
        //TODO: 这里每次要改一下
        pdf.setAccountPeriod(accountPeriod);
        pdf.setCustomerCode(customer.getCode());
        pdf.setCustomerName(customer.getDisplayName());
        pdf.setCustomerContact(customer.getContactName());
        for (SapBillItem item : buf) {
            if ("01".equals(item.getFeeType())) {
                pdf.setOverageFee(MathUtil.scale(new BigDecimal(item.getTotalAmount())));
            }
            if ("02".equals(item.getFeeType())) {
                pdf.setPstnFee(MathUtil.scale(new BigDecimal(item.getTotalAmount())));
            }
            if ("03".equals(item.getFeeType())) {
                pdf.setMonthlyFee(MathUtil.scale(new BigDecimal(item.getTotalAmount())));
            }
            if ("05".equals(item.getFeeType())) {
                pdf.setOnetimeFee(MathUtil.scale(new BigDecimal(item.getTotalAmount())));
            }
        }
        String content = sapReportService.generateSapAttachmentPdf(pdf, "sap.vm");
        buf.clear();
        sapReportService.saveAsPdf(content, "E:\\zilupdfs\\" + customerCode + ".pdf");
        return pdf;
    }



    private static final ArrayList<String[]> RESELLER_CODES = new ArrayList<>();
    static {
        //北京兆维电子（集团）有限责任公司
        RESELLER_CODES.add(new String[]{"KT01491", "KT00739"});
        //中国电信股份有限公司上海分公司
        RESELLER_CODES.add(new String[]{"KT01379", "KT00723"});
        //北京阳光金网科技发展有限公司
        RESELLER_CODES.add(new String[]{"KT01263", "KG80569HB4"});
        //集丰怡网络科技（北京）有限公司
        RESELLER_CODES.add(new String[]{"KT01108", "SM40600HB8"});
        //广州京谷光大信息技术有限公司
        RESELLER_CODES.add(new String[]{"KT01107", "SM40194HN1"});
        //尚阳科技股份有限公司
        RESELLER_CODES.add(new String[]{"KT01106", "SM40156HN8"});
        //上海会畅通讯股份有限公司
        RESELLER_CODES.add(new String[]{"KT01105", "KT00366"});
        //北京神州云科信息服务有限公司
        RESELLER_CODES.add(new String[]{"KT01104", "SM40574HB9"});
        //北京兆维博安科技有限公司
        RESELLER_CODES.add(new String[]{"KT01103", "SM40078HB2"});
        //广州华工中云信息技术有限公司
        RESELLER_CODES.add(new String[]{"KT01102", "SM40125HN4"});
        //上海华万通信科技有限公司
        RESELLER_CODES.add(new String[]{"SM40089HD4", "KT01844"});
        //深圳市华运通科技股份有限公司
        RESELLER_CODES.add(new String[]{"SM40524HN3", "KT02200"});
        //上海云学科技有限公司
        RESELLER_CODES.add(new String[]{"SM40115HD3", "KT03885"});
        //北京晓通网络科技有限公司
        RESELLER_CODES.add(new String[]{"KT04515"});
        //韦史德（上海）通信技术有限公司
        RESELLER_CODES.add(new String[]{"KT04373", "KT04455"});
    }
    @org.junit.Test
    public void exportResellerPdf() throws ParseException, IOException, DocumentException, InterruptedException, ExecutionException, BillingException, InvalidBillingOperationException {
        AccountPeriod accountPeriod = new AccountPeriod("201710");
        for (String[] codes : RESELLER_CODES) {
            List<SapBillItem> items = sapReportService.findResellerVoipBillsByAccountPeriod(accountPeriod, codes);
            exportResellerVoipPdf(accountPeriod, codes[0], items);

            List<SapBillItem> pstnItems = sapReportService.findResellerPstnBillsByAccountPeriod(accountPeriod.nextPeriod(), codes);
            exportResellerPstnPdf(accountPeriod, codes[0], pstnItems);

            int index = 1;
            for (String code : codes) {
                exportResellerPstnDetail(accountPeriod, code, index);
                index++;
            }
        }
        return;
    }

    private void exportResellerPstnDetail(AccountPeriod accountPeriod, String customerCode, int index)
            throws InterruptedException, ParseException, IOException, ExecutionException, BillingException, InvalidBillingOperationException {

        Customer customer = customerService.findFirstCustomerByCode(customerCode);
        String outputFile = "E:\\zilupdfs\\" + customer.getDisplayName() + "-" +  index +".xlsx";
        FileOutputStream fos = new FileOutputStream(outputFile);
        billingService.exportPstnFeeAsExcel(customer.getPid(), accountPeriod.toInt(), fos);

    }

    private void exportResellerVoipPdf(AccountPeriod accountPeriod, String customerCode, List<SapBillItem> buf) throws IOException, DocumentException {
        if (StringUtils.isBlank(customerCode) || buf.size() == 0) {
            return;
        }
        SapPdf pdf = new SapPdf();
        Customer customer = customerService.findFirstCustomerByCode(customerCode);
        pdf.setAccountPeriod(accountPeriod);
        pdf.setCustomerCode(customer.getCode());
        pdf.setCustomerName(customer.getDisplayName());
        pdf.setCustomerContact(customer.getContactName());
        for (SapBillItem item : buf) {
            if ("1".equals(item.getFeeType()) ||
                    "10".equals(item.getFeeType()) ||
                    "12".equals(item.getFeeType()) ||
                    "15".equals(item.getFeeType())) {
                pdf.addMonthlyFee(MathUtil.scale(new BigDecimal(item.getTotalAmount())));
            }
            if ("11".equals(item.getFeeType())) {
                pdf.addPstnFee(MathUtil.scale(new BigDecimal(item.getTotalAmount())));
            }
            if ("13".equals(item.getFeeType())) {
                pdf.addOverageFee(MathUtil.scale(new BigDecimal(item.getTotalAmount())));
            }
//            if ("5".equals(item.getFeeType())) {
//                pdf.setOnetimeFee(MathUtil.scale(new BigDecimal(item.getTotalAmount())));
//            }
        }
        String content = sapReportService.generateResellerVoipPdf(pdf, "reseller_voip.vm");
        buf.clear();
        sapReportService.saveAsPdf(content, "E:\\zilupdfs\\" + customer.getDisplayName() + "-VOIP.pdf");

    }


    private void exportResellerPstnPdf(AccountPeriod accountPeriod, String customerCode, List<SapBillItem> buf) throws IOException, DocumentException {
        if (StringUtils.isBlank(customerCode) || buf.size() == 0) {
            return;
        }
        SapPdf pdf = new SapPdf();
        Customer customer = customerService.findFirstCustomerByCode(customerCode);
        pdf.setAccountPeriod(accountPeriod);
        pdf.setCustomerCode(customer.getCode());
        pdf.setCustomerName(customer.getDisplayName());
        pdf.setCustomerContact(customer.getContactName());
        for (SapBillItem item : buf) {
            if ("1".equals(item.getFeeType()) ||
                    "10".equals(item.getFeeType()) ||
                    "12".equals(item.getFeeType()) ||
                    "15".equals(item.getFeeType())) {
                pdf.addMonthlyFee(MathUtil.scale(new BigDecimal(item.getTotalAmount())));
            }
            if ("11".equals(item.getFeeType())) {
                pdf.addPstnFee(MathUtil.scale(new BigDecimal(item.getTotalAmount())));
            }
            if ("13".equals(item.getFeeType())) {
                pdf.addOverageFee(MathUtil.scale(new BigDecimal(item.getTotalAmount())));
            }
//            if ("5".equals(item.getFeeType())) {
//                pdf.setOnetimeFee(MathUtil.scale(new BigDecimal(item.getTotalAmount())));
//            }
        }
        String content = sapReportService.generateSapAttachmentPdf(pdf, "sap.vm");
        buf.clear();
        sapReportService.saveAsPdf(content, "E:\\zilupdfs\\" + customer.getDisplayName() + "-PSTN.pdf");
    }



    @org.junit.Test
    public void exportZiluPdf() throws ParseException, IOException, DocumentException {
        AccountPeriod accountPeriod = new AccountPeriod("201710");
//        List<SapBillItem> bills = sapReportService.findSapBillsByAccountPeriod(accountPeriod);
        List<SapBillItem> bills = sapReportService.findSapPostpaidBillsByAccountPeriod(accountPeriod);
        List<SapPdf> pdfs = new LinkedList<>();

        List<SapBillItem> buf = new LinkedList<>();
        String customerCode = null;
        Iterator<SapBillItem> iter = bills.iterator();
        do {
            SapBillItem next = iter.next();
            if (next.getCustomerCode().equals(customerCode)) {
                buf.add(next);
            } else {
                pdfs.add(dump(accountPeriod, customerCode, buf));
                customerCode = next.getCustomerCode();
                buf.add(next);
            }
        } while (iter.hasNext());

        if (customerCode != null) {
            pdfs.add(dump(accountPeriod, customerCode, buf));
        }

        sapReportService.generatePrintablePdf(pdfs);


//        AccountPeriod accountPeriod=new AccountPeriod("201611");
//        List<SapBillItem> items = sapReportService.findSapPostpaidBillsByAccountPeriod(accountPeriod.previousPeriod());
//        List<String> customerCodes = CollectionUtils.collectString(items, "customerCode");
//
//        int accountPeriodInt = accountPeriod.previousPeriod().toInt();
//        List<BillPdf> pdfs = billPdfRepository.findByBillingPeriod(accountPeriodInt);
//
//        FileOutputStream fileOutputStream = new FileOutputStream("e:\\zilu.zip");
//        ZipOutputStream out = new ZipOutputStream(fileOutputStream);
//
//        for (BillPdf pdf : pdfs) {
//            if (customerCodes.contains(pdf.getCustomerCode())) {
//                ZipEntry zipEntry = new ZipEntry(pdf.getCustomerCode() + "-" + accountPeriod.toString() + ".pdf");
//                out.putNextEntry(zipEntry);
//                out.write(pdf.getPdfContent());
//                out.closeEntry();
//            }
//        }
//
//        out.close();
//        out.flush();
    }


    private SapPdf createPdf() throws ParseException {
        SapPdf pdf = new SapPdf();
        pdf.setAccountPeriod(new AccountPeriod("201611"));
        pdf.setCustomerCode("MG60532HD2");
        pdf.setCustomerName("TCL通讯(深圳)有限公司");
        pdf.setCustomerContact("周维嘉");
        pdf.setCreditNoteEndDate("2016-09-01");
        pdf.setPstnFee(new BigDecimal(4839.20f));
        pdf.setOverageFee(new BigDecimal(90));
        pdf.setMonthlyFee(new BigDecimal(200));
        pdf.setOnetimeFee(new BigDecimal(1500));
        return pdf;
    }

    private SapPdf createCreditNotePdf() throws ParseException {
        SapPdf pdf = new SapPdf();
        pdf.setAccountPeriod(new AccountPeriod("201611"));
        pdf.setCustomerCode("KG80276HN2");
        pdf.setCustomerName("雅芳(中国)有限公司");
        pdf.setCustomerContact("Paul Chen");
        pdf.setCreditNoteStartDate("2016-09-01");
        pdf.setCreditNoteEndDate("2016-09-30");
        pdf.setOriginalAmount(new BigDecimal(1710f));
        pdf.setRefundAmount(new BigDecimal(360f));
        return pdf;
    }

    @org.junit.Test
    public void exportCreditNotePdf() throws ParseException, IOException, DocumentException {
        String content = sapReportService.generateCreditNotePdf(createCreditNotePdf());
        sapReportService.saveAsPdf(content, "E:\\zilupdfs\\KG80276HN2-201611-1-C.pdf");
    }

    @org.junit.Test
    public void sendCreditNoteMail() throws WafException, IOException {
//        mailService.sendCreditNote("xiaowen.liu@ketianyun.com", new File("E:\\zilupdfs\\MG60532HD2-201610-1-C.pdf"));
        CsvReader csvReader = new CsvReader("F:\\物料excel.csv", ':', Charset.forName("GBK"));
        while (csvReader.readRecord()) {
            for (int i = 0 ; i < csvReader.getColumnCount(); i++) {
                System.out.print(csvReader.get(i));
                System.out.print("  ||  ");
            }
            System.out.println("\n------------");
        }


    }
}
