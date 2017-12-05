package com.kt.service;

import com.itextpdf.text.DocumentException;
import com.kt.biz.bean.SapBillItem;
import com.kt.biz.billing.AccountPeriod;
import com.kt.biz.sap.SapPdf;
import com.kt.biz.sap.Sync2SAP;
import com.kt.entity.mysql.billing.AccountOperation;
import com.kt.repo.mysql.billing.AccountOperationRepository;
import com.kt.repo.mysql.billing.BillExportRepository;
import com.kt.repo.mysql.billing.BillFormalDetailRepository;
import com.kt.util.DateUtil;
import com.kt.util.MathUtil;
import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.VelocityEngine;
import org.apache.velocity.app.event.implement.IncludeRelativePath;
import org.apache.velocity.runtime.RuntimeConstants;
import org.apache.velocity.runtime.resource.loader.ClasspathResourceLoader;
import org.apache.velocity.tools.generic.NumberTool;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import wang.huaichao.PdfCreator;
import wang.huaichao.data.service.Biller;

import java.io.ByteArrayOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by Vega Zhou on 2017/4/26.
 */
@Service
public class SapSyncService {

    @Autowired
    BillFormalDetailRepository billFormalDetailRepository;
    @Autowired
    AccountOperationRepository accountOperationRepository;
    @Autowired
    BillExportRepository billExportRepository;

    public List<SapBillItem> findResellerVoipBillsByAccountPeriod(AccountPeriod accountPeriod, String[] resellerCodes) {
        return billFormalDetailRepository.listResellerVoipBills(accountPeriod, resellerCodes);
    }

    public List<SapBillItem> findResellerPstnBillsByAccountPeriod(AccountPeriod accountPeriod, String[] resellerCodes) {
        return billFormalDetailRepository.listResellerPstnBills(accountPeriod, resellerCodes);
    }

    public List<SapBillItem> findSapBillsByAccountPeriod(AccountPeriod accountPeriod) {
        return billFormalDetailRepository.listSapBillReport(accountPeriod);
    }


    public List<AccountOperation> findUnsyncedSapPayments() {
        return accountOperationRepository.findUnsyncedPayments();
    }


    public List<SapBillItem> findSapPrepaidBillsByAccountPeriod(AccountPeriod accountPeriod) {
        return billFormalDetailRepository.listSapPrepaidBillReport(accountPeriod);
    }


    public List<SapBillItem> findSapPostpaidBillsByAccountPeriod(AccountPeriod accountPeriod) {
        return billFormalDetailRepository.listSapPostpaidBillReport(accountPeriod);
    }


    public List<SapBillItem> findSapPostpaidBillsByAccountPeriod(AccountPeriod accountPeriod, List<String> customerCodes) {
        return billFormalDetailRepository.listSapPostpaidBillReport(accountPeriod, customerCodes);
    }


    public List<SapBillItem> findSapCreditNotesByAccountPeriod(AccountPeriod accountPeriod) {
        billExportRepository.findCreditNotesByAccountPeriod(accountPeriod);
        return null;
    }



    public void syncSapBills(AccountPeriod accountPeriod) {
        List<SapBillItem> bills = findSapBillsByAccountPeriod(accountPeriod);
        Sync2SAP.syncBills(bills);
    }

    public void syncSapPrepaidBills(AccountPeriod accountPeriod) {
        List<SapBillItem> bills = findSapPrepaidBillsByAccountPeriod(accountPeriod);
        {
            for (SapBillItem item : bills) {
                List<SapBillItem> part1 = new ArrayList<>();

                part1.add(item);

                Sync2SAP.syncBills(part1);
            }
        }
//
//        {
//            List<SapBillItem> part2 = new ArrayList<>(100);
//            for (int i = 100; i < 200; i++) {
//                part2.add(bills.get(i));
//            }
//            Sync2SAP.syncBills(part2);
//        }
//
//        {
//            List<SapBillItem> part3 = new ArrayList<>(100);
//            for (int i = 200; i < 343; i++) {
//                part3.add(bills.get(i));
//            }
//            Sync2SAP.syncBills(part3);
//        }


//        Sync2SAP.syncBills(bills);
        return;
    }


    public void syncSapPostpaidBills(AccountPeriod accountPeriod) {
        List<SapBillItem> bills = findSapPostpaidBillsByAccountPeriod(accountPeriod);
        for (SapBillItem bill : bills) {
            Sync2SAP.syncBills(Collections.singletonList(bill));
        }

        return;
    }

    public void synSapCreditNotes() {

    }

    public void synSapCreditNote(SapBillItem creditNote) {
        Sync2SAP.syncBills(Collections.singletonList(creditNote));
    }


    public void synSapPayments() {
        List<AccountOperation> payments = findUnsyncedSapPayments();
        boolean syncResult = Sync2SAP.syncPayment(payments);
        if (syncResult) {
            for (AccountOperation payment : payments) {
                payment.setSapSynced(1);
                accountOperationRepository.save(payment);
            }
        }
        return;
    }

    private static final String PRINTABLE_END = "</body>\n" +
            "</html>";
    private static final String PRINTABLE_HEADER = "<html>\n" +
            "<head>\n" +
            "    <meta charset=\"UTF-8\"/>\n" +
            "    <title>月账单 </title>\n" +
            "    <style type=\"text/css\">\n" +
            "        * {\n" +
            "            font-family: \"SimSun\";\n" +
            "        }\n" +
            "\n" +
            "        body {\n" +
            "            font-size: 12px;\n" +
            "        }\n" +
            "\n" +
            "        p.title {\n" +
            "            text-align: center;\n" +
            "            font-size: 2.4em;\n" +
            "            font-weight: bold;\n" +
            "        }\n" +
            "\n" +
            "        p.subtitle {\n" +
            "            text-align: center;\n" +
            "            font-size: 1.8em;\n" +
            "            font-weight: bold;\n" +
            "        }\n" +
            "\n" +
            "        .page-break {\n" +
            "            page-break-after: always;\n" +
            "        }\n" +
            "\n" +
            "        table {\n" +
            "            width: 100%;\n" +
            "            border-collapse: collapse;\n" +
            "            -fs-table-paginate: paginate;\n" +
            "            border-spacing: 0;\n" +
            "            table-layout: fixed;\n" +
            "        }\n" +
            "\n" +
            "        table.details {\n" +
            "            table-layout: auto;\n" +
            "        }\n" +
            "\n" +
            "        table td, table th {\n" +
            "            -fs-keep-with-inline: keep;\n" +
            "            font-weight: normal;\n" +
            "            line-height: 15px;\n" +
            "            border: 0;\n" +
            "            border-bottom: .1px solid #000;\n" +
            "        }\n" +
            "\n" +
            "        table td:first-child, table th:first-child {\n" +
            "            padding-left: 2px;\n" +
            "        }\n" +
            "\n" +
            "        table thead tr td, table thead tr th {\n" +
            "            background: #acf;\n" +
            "            text-align: left;\n" +
            "        }\n" +
            "\n" +
            "        table thead tr:nth-child(2) td, table thead tr:nth-child(2) th {\n" +
            "            background: #ccc;\n" +
            "        }\n" +
            "\n" +
            "        td.ta-l, th.ta-l {\n" +
            "            text-align: left;\n" +
            "        }\n" +
            "\n" +
            "        td.ta-c, th.ta-c {\n" +
            "            text-align: center;\n" +
            "        }\n" +
            "\n" +
            "        td.ta-r, th.ta-r {\n" +
            "            text-align: right;\n" +
            "        }\n" +
            "\n" +
            "        td:last-child.ta-r, th:last-child.ta-r {\n" +
            "            padding-right: 2px;\n" +
            "        }\n" +
            "\n" +
            "        table.page-header {\n" +
            "            width: 100%;\n" +
            "            color: #888;\n" +
            "        }\n" +
            "\n" +
            "        table.page-header td {\n" +
            "            border: 0;\n" +
            "        }\n" +
            "\n" +
            "        table.recp-info {\n" +
            "            width: auto;\n" +
            "            font-size: inherit;\n" +
            "            word-break: keep-all;\n" +
            "            white-space: nowrap;\n" +
            "        }\n" +
            "\n" +
            "        table.recp-info td {\n" +
            "            border: 0;\n" +
            "        }\n" +
            "\n" +
            "        li {\n" +
            "            margin-bottom: 1em;\n" +
            "        }\n" +
            "\n" +
            "        /*div.page-header {*/\n" +
            "        /*padding: 10px 0;*/\n" +
            "        /*display: block;*/\n" +
            "        /*position: running(header);*/\n" +
            "        /*color: #888;*/\n" +
            "        /*}*/\n" +
            "\n" +
            "        /*div.page-header td {*/\n" +
            "        /*border: 0;*/\n" +
            "        /*}*/\n" +
            "\n" +
            "        /*div.page-header td:last-child {*/\n" +
            "        /*text-align: right;*/\n" +
            "        /*}*/\n" +
            "\n" +
            "        /*@page {*/\n" +
            "        /*margin: 50px;*/\n" +
            "        /*@top-center {*/\n" +
            "        /*content: element(header)*/\n" +
            "        /*}*/\n" +
            "        /*}*/\n" +
            "\n" +
            "\n" +
            "    </style>\n" +
            "</head>\n" +
            "<body>";
    public void generatePrintablePdf(List<SapPdf> entities) throws IOException, DocumentException {
        StringBuilder total = new StringBuilder();
        for (SapPdf pdf : entities) {
            String page = generateSapAttachmentPdf(pdf, "sap_printable.vm");
            total.append(page);
        }

        saveAsPdf(PRINTABLE_HEADER + total.toString() + PRINTABLE_END, "E:\\zilupdfs\\allinone.pdf");
    }

    public String generateCreditNotePdf(SapPdf entity) {
        if (entity == null) {
            return "";
        }
        VelocityEngine ve = new VelocityEngine();
        ve.setProperty(RuntimeConstants.RESOURCE_LOADER, "classpath");
        ve.setProperty("classpath.resource.loader.class", ClasspathResourceLoader.class.getName());
        ve.setProperty("eventhandler.referenceinsertion.class", Biller.InsertHanler.class.getName());
        ve.setProperty(RuntimeConstants.EVENTHANDLER_INCLUDE, IncludeRelativePath.class.getName());
        ve.init();

        Template template = ve.getTemplate("sap/credit_note.vm", "utf-8");

        VelocityContext vctx = new VelocityContext();
        vctx.put("numberTool", new NumberTool());

        vctx.put("customerCode", entity.getCustomerCode());
        vctx.put("accountPeriod", entity.getAccountPeriod().toString());
        vctx.put("customerName", entity.getCustomerName());
        vctx.put("customerContact", entity.getCustomerContact());
        vctx.put("startTime", entity.getCreditNoteStartDate());
        vctx.put("endTime", entity.getCreditNoteEndDate());

        vctx.put("originalAmount", MathUtil.scale(entity.getOriginalAmount()));
        vctx.put("refundAmount", MathUtil.scale(entity.getRefundAmount()));

        StringWriter sw = new StringWriter();
        template.merge(vctx, sw);
        String html = sw.toString();
        return html;
    }


    public String generateResellerVoipPdf(SapPdf entity, String templateName) {
        if (entity == null) {
            return "";
        }
        VelocityEngine ve = new VelocityEngine();
        ve.setProperty(RuntimeConstants.RESOURCE_LOADER, "classpath");
        ve.setProperty("classpath.resource.loader.class", ClasspathResourceLoader.class.getName());
        ve.setProperty("eventhandler.referenceinsertion.class", Biller.InsertHanler.class.getName());
        ve.setProperty(RuntimeConstants.EVENTHANDLER_INCLUDE, IncludeRelativePath.class.getName());
        ve.init();

        Template template = ve.getTemplate("sap/" + templateName, "utf-8");

        VelocityContext vctx = new VelocityContext();
        vctx.put("numberTool", new NumberTool());
        vctx.put("feeCount", entity.getFeeCount());

        vctx.put("customerCode", entity.getCustomerCode());
        vctx.put("accountPeriod", entity.getAccountPeriod().toString());
        vctx.put("customerName", entity.getCustomerName());
        vctx.put("customerContact", entity.getCustomerContact());
        vctx.put("startTime", entity.getAccountPeriod().toFormat2() + "-09");
        vctx.put("endTime", entity.getAccountPeriod().nextPeriod().toFormat2() + "-08");

        vctx.put("pstnFee", MathUtil.scale(entity.getPstnFee()));
        vctx.put("monthlyFee", MathUtil.scale(entity.getMonthlyFee()));
        vctx.put("overageFee", MathUtil.scale(entity.getOverageFee()));
        vctx.put("onetimeFee", MathUtil.scale(entity.getOnetimeFee()));
        vctx.put("totalFee", MathUtil.scale(entity.getTotalAmount()));


        StringWriter sw = new StringWriter();
        template.merge(vctx, sw);
        String html = sw.toString();
        return html;
    }

    public String generateSapAttachmentPdf(SapPdf entity, String templateName) {
        if (entity == null) {
            return "";
        }
        VelocityEngine ve = new VelocityEngine();
        ve.setProperty(RuntimeConstants.RESOURCE_LOADER, "classpath");
        ve.setProperty("classpath.resource.loader.class", ClasspathResourceLoader.class.getName());
        ve.setProperty("eventhandler.referenceinsertion.class", Biller.InsertHanler.class.getName());
        ve.setProperty(RuntimeConstants.EVENTHANDLER_INCLUDE, IncludeRelativePath.class.getName());
        ve.init();

        Template template = ve.getTemplate("sap/" + templateName, "utf-8");

        VelocityContext vctx = new VelocityContext();
        vctx.put("numberTool", new NumberTool());
        vctx.put("feeCount", entity.getFeeCount());

        vctx.put("customerCode", entity.getCustomerCode());
        vctx.put("accountPeriod", entity.getAccountPeriod().toString());
        vctx.put("customerName", entity.getCustomerName());
        vctx.put("customerContact", entity.getCustomerContact());
        vctx.put("startTime", DateUtil.formatShortDate(entity.getAccountPeriod().beginOfThisPeriod()));
        vctx.put("endTime", DateUtil.formatShortDate(entity.getAccountPeriod().endOfThisPeriod()));

        vctx.put("pstnFee", MathUtil.scale(entity.getPstnFee()));
        vctx.put("monthlyFee", MathUtil.scale(entity.getMonthlyFee()));
        vctx.put("overageFee", MathUtil.scale(entity.getOverageFee()));
        vctx.put("onetimeFee", MathUtil.scale(entity.getOnetimeFee()));
        vctx.put("totalFee", MathUtil.scale(entity.getTotalAmount()));


        StringWriter sw = new StringWriter();
        template.merge(vctx, sw);
        String html = sw.toString();
        return html;
    }

    public void saveAsPdf(String content, String absoluteFilePath) throws IOException, DocumentException {

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        PdfCreator.render(content, baos);
        FileOutputStream fileOutputStream = new FileOutputStream(absoluteFilePath);
//        FileOutputStream fileOutputStream = new FileOutputStream("E:\\zilupdfs\\" + entity.getCustomerCode() +".pdf");
        fileOutputStream.write(baos.toByteArray());
        fileOutputStream.flush();
        fileOutputStream.close();
    }
}