package wang.huaichao.data.service;

import com.itextpdf.text.DocumentException;
import com.kt.biz.types.AgentType;
import com.kt.entity.mysql.crm.Customer;
import com.kt.exception.WafException;
import com.kt.repo.mysql.batch.CustomerRepository;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.WorkbookUtil;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.AutowireCapableBeanFactory;
import org.springframework.stereotype.Service;
import wang.huaichao.data.entity.crm.BillingProgress;
import wang.huaichao.data.repo.*;
import wang.huaichao.exception.BillingException;
import wang.huaichao.data.entity.crm.*;
import wang.huaichao.exception.InvalidBillingOperationException;
import wang.huaichao.utils.AsyncTaskUtils;
import wang.huaichao.utils.CollectionUtils;
import wang.huaichao.utils.ExcelCreator;

import java.io.*;
import java.math.BigDecimal;
import java.text.ParseException;
import java.util.*;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

/**
 * Created by Administrator on 8/17/2016.
 */
@Service
public class BillingService {
    private static final Logger log = LoggerFactory.getLogger(BillingService.class);
    @Autowired
    private AutowireCapableBeanFactory autowireCapableBeanFactory;

    @Autowired
    private MeetingRecordRepository meetingRecordRepository;

    @Autowired
    private PstnChargeRepository pstnChargeRepository;

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private BillingProgressRepository billingProgressRepository;

    @Autowired
    private CrmDataService crmDataService;


    public float getPstnFeeByBillingPeriodAndOrderId(String orderId, int billingPeriod)
        throws BillingException {
        PstnCharge charge = pstnChargeRepository.findByBillingPeriodAndOrderId(billingPeriod, orderId);
        if (charge == null) {
            throw new BillingException();
        }
        return charge.getCost().floatValue();
    }

    public float getPstnFeeByBillingPeriodAndCustomerId(String customerId, int billingPeriod)
        throws BillingException {
        List<PstnCharge> charges = pstnChargeRepository.findByBillingPeriodAndCustomerId(billingPeriod, customerId);
        if (charges == null || charges.size() == 0) {
            throw new BillingException();
        }
        BigDecimal total = BigDecimal.ZERO;
        for (PstnCharge charge : charges) {
            total = total.add(charge.getCost());
        }
        return total.floatValue();
    }


    public Biller createBiller(int billingPeriod) throws InvalidBillingOperationException {
        Biller biller = new Biller(billingPeriod);
        autowireCapableBeanFactory.autowireBean(biller);
        return (Biller) autowireCapableBeanFactory.initializeBean(biller, "biller");
    }

    public BillPdfRender createBillPdfRender(int billingPeriod) throws InvalidBillingOperationException {
        BillPdfRender x = new BillPdfRender(billingPeriod);
        autowireCapableBeanFactory.autowireBean(x);
        return (BillPdfRender) autowireCapableBeanFactory.initializeBean(x, "billPdfRender");
    }


    public Future<Integer> calculatingPstnFeeByBillingPeriodAndCustomerId(int billingPeriod, String customerId)
        throws ParseException, IOException, InvalidBillingOperationException {
        Biller biller = createBiller(billingPeriod);
        return biller.calculatingPstnFeeByCustomer(customerId);
    }

    public Future<Integer> batchCalculatingPstnFee(int billingPeriod)
        throws IOException, ParseException, InvalidBillingOperationException, BillingException {

        BillingProgressPK pk = new BillingProgressPK();
        pk.setType(BillingProgressPK.BillingProgressType.BATCH_PSTN_FEE_CALCULATION);
        pk.setBillingPeriod(billingPeriod);

        BillingProgress progress = billingProgressRepository.findOne(pk);

        if (_isInProgress(progress, 3600 * 1000)) {
            throw new BillingException("task in progress");
        }

        Biller biller = createBiller(billingPeriod);
        return biller.batchCalculatingPstnFee();
    }

    public Future<Integer> redoFailedPstnCalculations(int billingPeriod)
        throws InvalidBillingOperationException {

        Biller biller = createBiller(billingPeriod);
        return biller.redoFailedPstnCalculations();
    }

    public Future<Integer> generateBillPdf(int billingPeriod, String customerId)
        throws Exception {
        BillPdfRender billPdfRender = createBillPdfRender(billingPeriod);
        return billPdfRender.generatePdfByCustomer(customerId);
    }

    public Future<Integer> generateAllBillPdf(int billingPeriod)
        throws WafException, DocumentException, ParseException, IOException {
        BillingProgressPK pk = new BillingProgressPK();
        pk.setType(BillingProgressPK.BillingProgressType.BATCH_PDF_GENERATION);
        pk.setBillingPeriod(billingPeriod);

        BillingProgress progress = billingProgressRepository.findOne(pk);

        if (_isInProgress(progress, 3600 * 1000)) {
            throw new BillingException("task in progress");
        }

        BillPdfRender billPdfRender = createBillPdfRender(billingPeriod);
        return billPdfRender.batchGeneratePdf();
    }

    private boolean _isInProgress(BillingProgress progress, long threshold) {
        final boolean inProgress = true;

        if (progress == null) return !inProgress;

        long diff = System.currentTimeMillis() - progress.getStartTime().getTime();

        if (progress.getStatus() == BillingProgress.BillingProgressStatus.IN_PROGRESS && diff < threshold) {
            return inProgress;
        } else {
            return !inProgress;
        }
    }

    public void exportPstnFeeAsExcel(String customerId, int billingPeriod, OutputStream os)
        throws IOException, ParseException, InvalidBillingOperationException,
        ExecutionException, InterruptedException, BillingException {

        Customer customer = customerRepository.findOne(customerId);

        if (customer == null) {
            _createErrorExcel(customer, "customer not found", os);
        } else if (
            AgentType.RESELLER2.name().equals(customer.getAgentType()) ||
                AgentType.AGENT.name().equals(customer.getAgentType())) {
            List<OrderCustomer> customerOrders = crmDataService.findOrdersUnderGivenAgent(customerId, billingPeriod);
            _exportPstnFeeOfTheseOrders2Excel(customerOrders, billingPeriod, os);
        } else {
            List<OrderCustomer> customerOrders = crmDataService.findDirectOrdersUnderGivenCustomer(customerId, billingPeriod);
            _exportPstnFeeOfTheseOrders2Excel(customerOrders, billingPeriod, os);
        }
    }

    private void _exportPstnFeeOfTheseOrders2Excel(
        List<OrderCustomer> customerOrders,
        int billingPeriod,
        OutputStream os
    ) throws
        IOException,
        InterruptedException,
        ExecutionException,
        BillingException,
        InvalidBillingOperationException,
        ParseException {

        if (customerOrders == null || customerOrders.size() == 0) {
            _createErrorExcel(null, "未查到PSTN记录！", os);
            return;
        }

        ExcelCreator excelCreator = new ExcelCreator();
        excelCreator.createSheet(null);
        excelCreator.setCellStyle(9, ExcelCreator.DATE_FMT_STR);
        excelCreator.setCellStyle(10, ExcelCreator.DATE_FMT_STR);
        excelCreator.setCellStyle(12, ExcelCreator.NUMER_FMT_STR);

        String[] headers = "客户名,站点名称,起会账号,会议ID,会议名称,PSTN类型,PSTN类型代码,平台号码,用户号码,开始时间,结束时间,分钟数,费用".split(",");

        for (int i = 0; i < headers.length; i++) {
            excelCreator.setColumnWidth(i, 20);
        }

        // header
        excelCreator.createRow(headers);
        long h8ms = 8 * 3600 * 1000;

        List<String> orderIds = CollectionUtils.collectString(customerOrders, "orderId");


        Map<String, String> customerIdToName = new HashMap<>();

        for (OrderCustomer customerOrder : customerOrders) {
            customerIdToName.put(
                customerOrder.getCustomerId(),
                customerOrder.getCustomerName()
            );
        }

        List<MeetingRecord> records;

        if (billingPeriod < 201703) {
            List<String> cids = new ArrayList<>();
            cids.addAll(customerIdToName.keySet());
            records = meetingRecordRepository.findByCustomerIdsAndBillingPeriodJoinPstnCode(
                cids, billingPeriod
            );
        } else {
            records = meetingRecordRepository.findByCustomerIdAndBillingPeriodJoinPstnCode(billingPeriod, orderIds);
        }

        for (MeetingRecord record : records) {
            excelCreator.createRow(
                customerIdToName.get(record.getCustomerId()),
                record.getSiteName(),
                record.getHostName(),
                record.getConfId(),
                record.getConfName(),
                record.getAccessType(),
                record.getPstnCode(),
                record.getAccessNumber(),
                record.getUserNumber(),
                new Date(record.getStartTime().getTime() + h8ms),
                new Date(record.getEndTime().getTime() + h8ms),
                record.getDuration(),
                record.getCost()
            );
        }

        excelCreator.write(os);
    }

    private void _exportPstnFeeAsExcelUnderGivenAgent(
        String customerId, int billingPeriod, OutputStream os)
        throws IOException, ParseException, InterruptedException,
        ExecutionException, BillingException, InvalidBillingOperationException {
        _exportPstnFeeAsExcelUnderGivenAgent(
            customerRepository.findOne(customerId),
            billingPeriod,
            os
        );
    }

    private void _exportPstnFeeAsExcelUnderGivenAgent(
        Customer customer, int billingPeriod, OutputStream os)
        throws IOException, ParseException, InterruptedException,
        ExecutionException, BillingException, InvalidBillingOperationException {

        List<String> customerIds = crmDataService.findCustomerUnderGivenAgent(
            customer.getPid(), billingPeriod);

        _exportTheseCustomersPstnFee2Excel(customerIds, billingPeriod, os);
    }

    private void _exportTheseCustomersPstnFee2Excel(
        List<String> customerIds, int billingPeriod, OutputStream os)
        throws IOException, InterruptedException, ExecutionException,
        BillingException, InvalidBillingOperationException, ParseException {

        ExcelCreator excelCreator = new ExcelCreator();
        excelCreator.createSheet(null);
        excelCreator.setCellStyle(9, ExcelCreator.DATE_FMT_STR);
        excelCreator.setCellStyle(10, ExcelCreator.DATE_FMT_STR);
        excelCreator.setCellStyle(12, ExcelCreator.NUMER_FMT_STR);

        String[] headers = "客户名,站点名称,起会账号,会议ID,会议名称,PSTN类型,PSTN类型代码,平台号码,用户号码,开始时间,结束时间,分钟数,费用".split(",");

        for (int i = 0; i < headers.length; i++) {
            excelCreator.setColumnWidth(i, 20);
        }

        // header
        excelCreator.createRow(headers);
        long h8ms = 8 * 3600 * 1000;

        for (String customerId : customerIds) {
            try {
                _calculatePstnFeeIfNecessary(customerId, billingPeriod);
            } catch (Exception e) {
                continue;
            }
            Customer endUser = customerRepository.findOne(customerId);
            List<MeetingRecord> records = meetingRecordRepository.findByCustomerIdAndBillingPeriodJoinPstnCode(
                customerId, billingPeriod);
            for (MeetingRecord record : records) {
                excelCreator.createRow(
                    endUser.getDisplayName(),
                    record.getSiteName(),
                    record.getHostName(),
                    record.getConfId(),
                    record.getConfName(),
                    record.getAccessType(),
                    record.getPstnCode(),
                    record.getAccessNumber(),
                    record.getUserNumber(),
                    new Date(record.getStartTime().getTime() + h8ms),
                    new Date(record.getEndTime().getTime() + h8ms),
                    record.getDuration(),
                    record.getCost()
                );
            }
        }

        excelCreator.write(os);
    }


    private void _exportDirectCustomer(Customer customer, int billingPeriod, OutputStream os)
        throws InterruptedException, ParseException, IOException, ExecutionException,
        BillingException, InvalidBillingOperationException {
        _exportTheseCustomersPstnFee2Excel(Arrays.asList(customer.getPid()), billingPeriod, os);
    }

    private void _calculatePstnFeeIfNecessary(String customerId, int billingPeriod)
        throws ParseException, InvalidBillingOperationException, IOException, BillingException,
        ExecutionException, InterruptedException {

        // check if PSTN fee calculated
        BillingProgressPK pk = new BillingProgressPK(
            customerId, billingPeriod, BillingProgressPK.BillingProgressType.SINGLE_PSTN_FEE_CALCULATION);
        BillingProgress progress = billingProgressRepository.findOne(pk);

        if (progress == null) {
            long ts = System.currentTimeMillis();

            Future<Integer> result = calculatingPstnFeeByBillingPeriodAndCustomerId(billingPeriod, customerId);

            // wait for task succeeded
            Integer r = result.get();

            if (r == AsyncTaskUtils.FAILED.get()) {
                throw new BillingException("PSTN fee calculating failed");
            } else if (r == AsyncTaskUtils.SUCCEEDED.get()) {
                log.debug("calculating PSTN fee for {}, time used {}ms", customerId, System.currentTimeMillis() - ts);
            }
        } else if (progress.getStatus() == BillingProgress.BillingProgressStatus.IN_PROGRESS) {
            throw new BillingException("PSTN fee calculating in progress");
        }
    }


    private void _createErrorExcel(Customer customer, String msg, OutputStream os) throws IOException {
        Workbook wb = new XSSFWorkbook();
        String safeSheetName = WorkbookUtil.createSafeSheetName(customer == null ? "Sheet 001" : customer.getDisplayName());
        Sheet sheet = wb.createSheet(safeSheetName);
        Row row = sheet.createRow(0);
        Cell cell = row.createCell(0);
        cell.setCellValue(msg);
        wb.write(os);
    }
}
