package com.kt.test.billing;

import com.kt.biz.bean.AgentRebateBean;
import com.kt.biz.billing.AccountPeriod;
import com.kt.biz.sap.Sync2SAP;
import com.kt.entity.mysql.crm.Contract;
import com.kt.entity.mysql.crm.Customer;
import com.kt.exception.OrderCollisionsDetectedException;
import com.kt.exception.OrderIncompleteException;
import com.kt.exception.WafException;
import com.kt.repo.mysql.batch.CustomerRepository;
import com.kt.service.ContractService;
import com.kt.service.CustomerService;
import com.kt.service.OrderService;
import com.kt.service.ProductService;
import com.kt.service.billing.BillService;
import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.CreationHelper;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.ParseException;
import java.util.List;

/**
 * Created by Vega Zhou on 2016/11/18.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration
public class TestAgent {
    @Configuration
    @ComponentScan(basePackages = {"com.kt"})
    static class ContextConfiguration {
    }

    @Autowired
    ProductService productService;
    @Autowired
    OrderService orderService;
    @Autowired
    ContractService contractService;
    @Autowired
    BillService billService;
    @Autowired
    CustomerService customerService;
    @Autowired
    CustomerRepository customerRepository;

    private static final String vega = "4f889254-7785-43ed-85bd-fae261dae757";
    private static final String agent2 = "d429e99b-878f-4c09-8105-10eb2e7197e1";
    private static final String MC_BY_PORTS_51_100 = "5419db22-9df9-41a1-88ed-d2526893a06e";
    private static final String AGENT_PRODUCT = "994f2b79-b9d3-48d7-b3f0-1ee9dbe41f25";
    private static final String AGENT_PRODUCT2 = "88fabf81-0e2c-4fe1-a61e-41ca478d61c0";



    @org.junit.Test
    public void test2() throws ParseException, WafException {
//        List<Customer> allCustomers = customerService.listAllCustomerByList();
        AccountPeriod accountPeriod = new AccountPeriod("201708");
        int total = 0;
//        for (Customer customer : allCustomers) {
//            List<Contract> contracts = contractService.findAccountableContractsOfCustomer("909b24fc-239c-4949-ac2b-e27d369637b9", accountPeriod.previousPeriod());
//            total += contracts.size();
//        }
//        System.out.println(total);
        return;
    }

    @org.junit.Test
    public void test3() throws ParseException, WafException {

        List<AgentRebateBean> results = billService.calculateAgentRebate("81b4b082-6f7c-4185-afa5-61fb0d7de6bb", new AccountPeriod("201709"));
        return;
    }


    @org.junit.Test
    public void test() throws WafException, ParseException, IOException {
//        contractService.deleteByForce("9bd2b893-b422-4c0b-8082-15cc9dbb17ad");
//        productService.draftAgentProduct(agent2, MC_BY_PORTS_51_100);
//        productService.offShelfAgentProduct(AGENT_PRODUCT);
//        productService.onShelfAgentProduct(AGENT_PRODUCT2);

//        List<AgentRebateBean> bills = billService.calculateAgentRebate("4a7e68db-c07f-4307-a865-81806c997861", new AccountPeriod("201612"));
        List<AgentRebateBean> bills = billService.calculateAgentRebate(new AccountPeriod("201710"));

        HSSFWorkbook workbook = new HSSFWorkbook(TestAgent.class.getResourceAsStream("/agent/rebate.xls"));
        HSSFSheet sheet = workbook.getSheetAt(0);
        CellStyle floatStyle = createNumberStyle(workbook);
        CellStyle textStyle = createTextStyle(workbook);
        int cursor = 1;
        for (AgentRebateBean bill : bills) {
            HSSFRow row = sheet.createRow(cursor);
            dumpRebateRow(bill, row, floatStyle, textStyle);
            cursor++;
        }


        FileOutputStream out = new FileOutputStream(new File("F:\\rebate.xls"));
        workbook.write(out);


        return;
    }


    private CellStyle createNumberStyle(HSSFWorkbook workbook) {
        final CellStyle style = workbook.createCellStyle();
        CreationHelper createHelper = workbook.getCreationHelper();
        style.setDataFormat(createHelper.createDataFormat().getFormat("#,##0.00"));
        HSSFFont font = workbook.createFont();
        font.setFontName("宋体");
        font.setFontHeightInPoints((short) 9);
        style.setFont(font);
        style.setBorderBottom(HSSFCellStyle.BORDER_THIN);
        style.setBorderTop(HSSFCellStyle.BORDER_THIN);
        style.setBorderLeft(HSSFCellStyle.BORDER_THIN);
        style.setBorderRight(HSSFCellStyle.BORDER_THIN);
        return style;
    }

    private CellStyle createTextStyle(HSSFWorkbook workbook) {
        final CellStyle style = workbook.createCellStyle();
        HSSFFont font = workbook.createFont();
        font.setFontName("宋体");
        font.setFontHeightInPoints((short) 9);
        style.setFont(font);
        style.setBorderBottom(HSSFCellStyle.BORDER_THIN);
        style.setBorderTop(HSSFCellStyle.BORDER_THIN);
        style.setBorderLeft(HSSFCellStyle.BORDER_THIN);
        style.setBorderRight(HSSFCellStyle.BORDER_THIN);
        return style;
    }

    private void dumpRebateRow(AgentRebateBean bill, HSSFRow row, CellStyle floatStyle, CellStyle textStyle) {
        HSSFCell cell0 = row.createCell(0);
        HSSFCell cell1 = row.createCell(1);
        HSSFCell cell2 = row.createCell(2);
        HSSFCell cell3 = row.createCell(3);
        HSSFCell cell4 = row.createCell(4);
        HSSFCell cell5 = row.createCell(5);
        HSSFCell cell6 = row.createCell(6);
        HSSFCell cell7 = row.createCell(7);
        HSSFCell cell8 = row.createCell(8);
        HSSFCell cell9 = row.createCell(9);
        HSSFCell cell10 = row.createCell(10);
        HSSFCell cell11 = row.createCell(11);
        HSSFCell cell12 = row.createCell(12);
        HSSFCell cell13 = row.createCell(13);
        HSSFCell cell14 = row.createCell(14);

        cell0.setCellValue(bill.getAgentName());
        cell1.setCellValue(bill.getCustomerName());
        cell2.setCellValue(bill.getProductName());
        cell3.setCellValue(bill.getIsRegistered() == 1 ? "是" : "否");
        cell4.setCellValue(bill.getUnit());
        cell5.setCellValue(bill.getAmount());
        cell6.setCellValue(bill.getUnitPrice());
        cell7.setCellValue(bill.getAgentUnitPrice());     //  最低单价
        cell8.setCellValue(bill.getAgentTotalPrice());    //  最低总价
        cell9.setCellValue(bill.getRebateWithRegister());                           //  有注册返利
        cell10.setCellValue(bill.getRebateWithoutRegister());                          //  无注册返利
        cell11.setCellValue(bill.getEffectiveStartDate());
        cell12.setCellValue(bill.getEffectiveEndDate());
        cell13.setCellValue(bill.getClearedDate());
        cell14.setCellValue(bill.getSite());

        cell0.setCellStyle(textStyle);
        cell1.setCellStyle(textStyle);
        cell2.setCellStyle(textStyle);
        cell3.setCellStyle(textStyle);
        cell4.setCellStyle(textStyle);
        cell5.setCellStyle(floatStyle);
        cell6.setCellStyle(floatStyle);
        cell7.setCellStyle(floatStyle);
        cell8.setCellStyle(floatStyle);
        cell9.setCellStyle(floatStyle);
        cell10.setCellStyle(floatStyle);
        cell11.setCellStyle(textStyle);
        cell12.setCellStyle(textStyle);
        cell13.setCellStyle(textStyle);
        cell14.setCellStyle(textStyle);
    }


}
