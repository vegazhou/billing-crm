package wang.huaichao.data.service;

import com.itextpdf.text.DocumentException;
import com.kt.biz.billing.AccountPeriod;
import com.kt.biz.types.FeeType;
import com.kt.entity.mysql.billing.*;
import com.kt.entity.mysql.crm.WebExSite;
import com.kt.exception.WafException;
import com.kt.repo.mysql.batch.CustomerRepository;
import com.kt.service.EdrService;
import com.kt.service.OrderService;
import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.VelocityEngine;
import org.apache.velocity.app.event.ReferenceInsertionEventHandler;
import org.apache.velocity.app.event.implement.IncludeRelativePath;
import org.apache.velocity.runtime.RuntimeConstants;
import org.apache.velocity.runtime.resource.loader.ClasspathResourceLoader;
import org.apache.velocity.tools.generic.DateTool;
import org.apache.velocity.tools.generic.NumberTool;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.scheduling.annotation.Async;
import wang.huaichao.Global;
import wang.huaichao.PdfCreator;
import wang.huaichao.data.ds.*;
import wang.huaichao.data.ds.details.UsageDetails;
import wang.huaichao.data.entity.crm.*;
import wang.huaichao.data.entity.edr.CallDataRecord;
import wang.huaichao.data.mongo.BillPdf;
import wang.huaichao.data.mongo.BillPdfRepository;
import wang.huaichao.data.repo.*;
import wang.huaichao.exception.InvalidBillingOperationException;
import wang.huaichao.utils.AsyncTaskUtils;
import wang.huaichao.utils.BillingProfiler;
import wang.huaichao.utils.CollectionUtils;
import wang.huaichao.utils.DateBuilder;
import wang.huaichao.utils.velocity.EscapeToolCustom;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.StringWriter;
import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.Future;


/**
 * Created by Administrator on 9/7/2016.
 */
public class BillPdfRender {
    private static final Logger log = LoggerFactory.getLogger(BillPdfRender.class);

    @Autowired
    private EdrDataService edrDataService;

    @Autowired
    private CrmDataService crmDataService;

    @Autowired
    private MeetingRecordRepository meetingRecordRepository;

    @Autowired
    @Qualifier("WebExSiteRepository2")
    private WebExSiteRepository webExSiteRepository;

    @Autowired
    private OrderService orderService;

    @Autowired
    @Qualifier("BillFormalDetailRepository2")
    private BillFormalDetailRepository billFormalDetailRepository;

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private EdrService edrService;

    @Autowired
    private BillPdfRepository billPdfRepository;

    @Autowired
    private BillingProgressRepository billingProgressRepository;

    @Autowired
    @Qualifier("BillConfirmationRepository2")
    private BillConfirmationRepository billConfirmationRepository;

    @Autowired
    @Qualifier("PortsOverflowDetailRepository2")
    private PortsOverflowDetailRepository portsOverflowDetailRepository;

    @Autowired
    @Qualifier("storageUsageRepo")
    private BssStorageUsageLogRepository storageUsageRepo;


    private int billingPeriod;
    private Date start;
    private Date end;
    private Date middleOfMonth;

    public BillPdfRender(int billingPeriod) throws InvalidBillingOperationException {
        if (billingPeriod < 201608) {
            throw new InvalidBillingOperationException("invalid billing period " + billingPeriod);
        }

        this.billingPeriod = billingPeriod;
        DateBuilder db;
        try {
            db = new DateBuilder(Global.yyyyMM_FMT.parse(billingPeriod + ""));
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
        start = db.beginOfMonth().build();
        end = db.nextMonth().build();
        middleOfMonth = db.prevMonth().setDate(16).build();
    }


    @Async
    public Future<Integer> generatePdfByCustomer(String customerId) throws Exception {
        BillingProgressPK pk = new BillingProgressPK();
        pk.setBillingPeriod(billingPeriod);
        pk.setCustomerId(customerId);
        pk.setType(BillingProgressPK.BillingProgressType.SINGLE_PDF_GENERATION);
        BillingProgress one = billingProgressRepository.findOne(pk);

        if (_isInProgress(one, 10 * 60 * 1000)) {
            return AsyncTaskUtils.CANCELED;
        }

        BillingProgress bp = new BillingProgress();
        bp.setBillingPeriod(billingPeriod);
        bp.setCustomerId(customerId);
        bp.setType(BillingProgressPK.BillingProgressType.SINGLE_PDF_GENERATION);
        bp.setStatus(BillingProgress.BillingProgressStatus.IN_PROGRESS);
        bp.setCreatedAt(new Date());
        bp.setStartTime(new Date());
        bp.setTotalTasks(1);
        billingProgressRepository.save(bp);

        Future<Integer> result = null;
        try {
            _generatePdfByCustomer(customerId);
            bp.setSucceededTasks(1);
            result = AsyncTaskUtils.SUCCEEDED;
        } catch (Exception e) {
            bp.setFailedTasks(1);
            result = AsyncTaskUtils.FAILED;
            log.error("generate pdf error, customer id " + customerId, e);
            throw e;
        } finally {
            bp.setEndTime(new Date());
            bp.setStatus(BillingProgress.BillingProgressStatus.COMPLETED);
            billingProgressRepository.save(bp);
            return result;
        }
    }

    public boolean _isInProgress(BillingProgress one, long threshold) {
        final boolean inProgress = true;

        if (one == null) return !inProgress;

        long diff = System.currentTimeMillis() - one.getStartTime().getTime();

        if (one.getStatus() == BillingProgress.BillingProgressStatus.IN_PROGRESS && diff < threshold) {
            return inProgress;
        } else {
            return !inProgress;
        }
    }

    private void _generatePdfByCustomer(String customerId) throws Exception {
        AccountPeriod accountPeriod = new AccountPeriod(billingPeriod + "");
        accountPeriod = accountPeriod.nextPeriod();
        BillConfirmationPrimaryKey pk = new BillConfirmationPrimaryKey();
        pk.setCustomerId(customerId);
        pk.setAccountPeriod(accountPeriod.toString());

        BillConfirmation one = billConfirmationRepository.findOne(pk);
        if (one == null) {
            throw new RuntimeException("bill has not been confirmed " + customerId + ", " + billingPeriod);
        }


        com.kt.entity.mysql.crm.Customer customer = customerRepository.findOne(customerId);

        DateFormat df = new SimpleDateFormat("yyyy年MM月dd日");

        RptConfig rptConfig = new RptConfig();

        rptConfig.setCustomerId(customerId);
        rptConfig.setCustomerCode(customer.getCode());

        rptConfig.setPayeeName("广州科天视畅信息科技有限公司");
        rptConfig.setPayeeAddress("广州市科学城科学大道187号 科学城商业广场A2栋8楼");
        rptConfig.setPayeeBankName("中国建设银行广州凯得广场支行");
        rptConfig.setPayeeBankNo("4400 1101 9040 5250 0295");

        rptConfig.setPayerName(customer.getDisplayName());
        rptConfig.setPayerNo(customer.getCode());
        rptConfig.setPayerContactName(customer.getContactName());
        rptConfig.setStartTime(df.format(start));

        Date endOfMonth = new Date(end.getTime() - 1);
        rptConfig.setEndTime(df.format(endOfMonth));

        rptConfig.setBillNo(customer.getCode() + "-" + billingPeriod);
        rptConfig.setBillDate("fixme");      // fixme: ...


        BillingProfiler profiler = new BillingProfiler();

        profiler.boot();

//        profiler.reset();
//        List<OrderBean> orders = orderService.findMainProductOrdersByCustomer(customerId, accountPeriod);
//        profiler.stop("find all main product orders");
//        List<String> orderIds = CollectionUtils.collectString(orders, "id");
        List<String> orderIds = crmDataService.findMainProductOrderIds(customerId, billingPeriod);

        // main product fee
//        _getMainProductFee(orderIds, rptConfig);

        // product/conference fee
        // & pstn fee
        profiler.reset();
        _getBillFormalFees(customerId, rptConfig, accountPeriod);
        profiler.stop("get pstn/conference/overflow? fees");


        // overflow fee by site
        // & overflow total fee
        profiler.reset();
//        _getPortsOverflowFee(orderIds, accountPeriod, rptConfig, customerId);
        _getPortsOverflowFee2(rptConfig, customerId);
        profiler.stop("list ports overflow fees");


        // storage overflow fee
        profiler.reset();
        _getSiteStorageUsageFee(rptConfig, customerId);
        profiler.stop("list storage overflow fees");


        // product monthly paid fee
        // & set total ports here
        profiler.reset();
        _generateProductMonthlyPayFee(customerId, rptConfig);
        profiler.stop("_generateProductMonthlyPayFee");

        // pstn fee
//        float pstnFee = getPstnFeeByCustomer(customerId);
//        rptConfig.setPstnFee(pstnFee);
//        rptConfig.setUnpaidPstnFee(pstnFee);

        // current balance
        float[] balances = crmDataService.getCustomerBalance(customerId);
        rptConfig.setDeposit(balances[0]);
        rptConfig.setPrepaid(balances[1]);

        // pstn fee by host
        profiler.reset();
//        List<MeetingRecord> records = meetingRecordRepository.findByCustomerIdAndBillingPeriod(customerId, billingPeriod);
        // todo: get pstn olny orders
        List<OrderCustomer> directOrdersUnderGivenCustomer = crmDataService.findDirectOrdersUnderGivenCustomer(customerId, billingPeriod);
        List<String> oids = CollectionUtils.collectString(directOrdersUnderGivenCustomer, "orderId");
        List<MeetingRecord> records = meetingRecordRepository.findByBillingPeriodAndOrderIds(billingPeriod, oids);

        _generatePstnFeeByHost(records, rptConfig);
        profiler.stop("_generatePstnFeeByHost");

        List<WebExSite> sites = webExSiteRepository.findByCustomerIdAndStateIn(customerId, "IN_EFFECT", "SUSPENDED");
        List<String> siteNames = CollectionUtils.collectString(sites, "siteName");

        // conference fee by host
        profiler.reset();
        List<CallDataRecord> dataRecords = edrDataService.findDataRecords(start, end, siteNames);
        _generateDataUsageByHost(dataRecords, rptConfig);
        profiler.stop("_generateDataUsageByHost");

        // details
        profiler.reset();
        List<CallDataRecord> voipRecords = edrDataService.findVoipRecords(start, end, siteNames);
        _generateDetails(records, dataRecords, voipRecords, rptConfig);
        profiler.stop("_generateDetails");

        profiler.reset();
        doGeneratePdf(rptConfig);
        profiler.stop("doGeneratePdf");

        profiler.terminate();
    }

    @Async
    public Future batchGeneratePdf() throws IOException, ParseException, DocumentException, WafException {
        AccountPeriod accountPeriod = new AccountPeriod(billingPeriod + "");
        accountPeriod = accountPeriod.nextPeriod();

        List<BillConfirmation> confirmations = billConfirmationRepository.findByAccountPeriod(accountPeriod.toString());
        List<String> customerIds = CollectionUtils.collectString(confirmations, "customerId");
        List<String> customerIdsOrderInEffect = crmDataService.findCustomerWithEffectiveOrder(this.start, this.end);
        List<String> f2fUsers = crmDataService.findF2fUsers();

        customerIds.retainAll(customerIdsOrderInEffect);
        customerIds.removeAll(f2fUsers);


        BillingProgress bp = new BillingProgress();
        bp.setBillingPeriod(billingPeriod);
        bp.setCreatedAt(new Date());
        bp.setStartTime(new Date());
        bp.setType(BillingProgressPK.BillingProgressType.BATCH_PDF_GENERATION);
        bp.setStatus(BillingProgress.BillingProgressStatus.IN_PROGRESS);
        bp.setTotalTasks(customerIds.size());
        billingProgressRepository.save(bp);

        int succeeded = 0;
        int failed = 0;

        for (String customerId : customerIds) {
            log.debug("generating pdf for customer {}", customerId);
            try {
                Future<Integer> future = generatePdfByCustomer(customerId);

                if (future.get() == AsyncTaskUtils.FAILED_CODE) {
                    throw new RuntimeException("async task failed");
                }

                bp.setSucceededTasks(++succeeded);
                billingProgressRepository.save(bp);
            } catch (Exception e) {
                bp.setFailedTasks(++failed);
                billingProgressRepository.save(bp);
            }
        }

        bp.setEndTime(new Date());
        bp.setStatus(BillingProgress.BillingProgressStatus.COMPLETED);
        billingProgressRepository.save(bp);

        return null;
    }

    private void _generatePstnFeeByHost(List<MeetingRecord> records, RptConfig rptConfig) {
        HostUsage hostUsage = new HostUsage();

        Collections.sort(records, new Comparator<MeetingRecord>() {
            @Override
            public int compare(MeetingRecord o1, MeetingRecord o2) {
                return o1.getSiteName().compareTo(o2.getSiteName())
                    * 1000
                    + o1.getHostName().compareTo(o2.getHostName());
            }
        });

        for (MeetingRecord record : records) {
            hostUsage.addUsage(
                record.getConfId(),
                record.getHostName(),
                record.getSiteName(),
                record.getDuration(),
                record.getCost()
            );
        }

        rptConfig.setPstnHostUsage(hostUsage);
    }

    private void _generateDataUsageByHost(List<CallDataRecord> records, RptConfig rptConfig) throws IOException {
        HostUsage hostUsage = new HostUsage();

        Collections.sort(records, new Comparator<CallDataRecord>() {
            @Override
            public int compare(CallDataRecord o1, CallDataRecord o2) {
                return o1.getSiteName().compareTo(o2.getSiteName())
                    * 1000
                    + o1.getHostName().compareTo(o2.getHostName());
            }
        });

        for (CallDataRecord record : records) {
            hostUsage.addUsage(
                record.getConfId(),
                record.getHostName(),
                record.getSiteName(),
                record.getDuration(),
                BigDecimal.ZERO
            );
        }

        rptConfig.setDataHostUsage(hostUsage);
    }

    private void _generateProductMonthlyPayFee(String customerId, RptConfig rptConfig) throws IOException {
        Map<String, ProductMonthlyFee> productMonthlyFeeMap =
            crmDataService.findProductByCustomerId(customerId, start, end);

        // set total ports
        // this map is filled previously
        Map<String, PortsUsageInfo> siteFeeMap = rptConfig.getSiteFeeMap();
        for (ProductMonthlyFee productMonthlyFee : productMonthlyFeeMap.values()) {
            PortsUsageInfo portsUsageInfo = siteFeeMap.get(productMonthlyFee.getSiteName());
            if (portsUsageInfo == null) continue;
            portsUsageInfo.setTotalPorts(productMonthlyFee.getPorts());
        }


        // display only those orders placed before middle of month

        Set<String> igns = new HashSet<>();

        for (String orderId : productMonthlyFeeMap.keySet()) {
            ProductMonthlyFee pmf = productMonthlyFeeMap.get(orderId);
            if (pmf.getStartDate().compareTo(middleOfMonth) >= 0 ||
                pmf.getEndDate().compareTo(middleOfMonth) < 0) {
                igns.add(orderId);
            }
        }

        for (String ign : igns) {
            productMonthlyFeeMap.remove(ign);
        }

        // end ...


        ProductUsage productUsage = new ProductUsage();
        productUsage.setProductMonthlyFeeMap(productMonthlyFeeMap);
        rptConfig.setProductUsage(productUsage);

//        BigDecimal t = BigDecimal.ZERO;
//        for (ProductMonthlyFee productMonthlyFee : productMonthlyFeeMap.values()) {
//            int x = productMonthlyFee.getHosts() + productMonthlyFee.getPorts();
//            t = t.add(productMonthlyFee.getPrice().multiply(new BigDecimal(x)));
//        }
//        rptConfig.setProductFee(t.floatValue());
    }


    private void _generateDetails(List<MeetingRecord> pstnRecords,
                                  List<CallDataRecord> dataRecord,
                                  List<CallDataRecord> voipRecords,
                                  RptConfig rptConfig) throws IOException {

        for (CallDataRecord record : dataRecord) {
            pstnRecords.add(dataRecord2MeetingRecord(record));
        }

        for (CallDataRecord record : voipRecords) {
            pstnRecords.add(voipRecord2MeetingRecord(record));
        }

        Collections.sort(pstnRecords);

        UsageDetails usageDetails = new UsageDetails();

        for (MeetingRecord pstnRecord : pstnRecords) {
            usageDetails.add(pstnRecord);
        }

        usageDetails.calculate();

        rptConfig.setUsageDetails(usageDetails);

    }

    private MeetingRecord dataRecord2MeetingRecord(CallDataRecord record) {
        MeetingRecord meetingRecord = new MeetingRecord();

        meetingRecord.setSiteName(record.getSiteName());
        meetingRecord.setUserName(record.getUserName());
        meetingRecord.setHostName(record.getHostName());
        meetingRecord.setConfId(record.getConfId());
        meetingRecord.setConfName(record.getConfName());
        meetingRecord.setStartTime(record.getStartTime());
        meetingRecord.setEndTime(record.getEndTime());
        meetingRecord.setUserNumber("0");
        meetingRecord.setAccessNumber("0");
        meetingRecord.setAccessType("纯数据");
        meetingRecord.setDuration(record.getDuration());
        meetingRecord.setCost(BigDecimal.ZERO);

        return meetingRecord;
    }

    private MeetingRecord voipRecord2MeetingRecord(CallDataRecord record) {
        MeetingRecord meetingRecord = dataRecord2MeetingRecord(record);
        meetingRecord.setAccessType("VOIP");

        return meetingRecord;
    }


    private void _getBillFormalFees(String customerId, RptConfig rptConfig, AccountPeriod accountPeriod) {
        List<BillFormalDetail> details = billFormalDetailRepository.findByAccountPeriodAndCustomerId(
            accountPeriod.toString(), customerId);

        float productFee = 0;
        float productUnpaidFee = 0;
        float pstnFee = 0;
        float overflowFee = 0;
        float storageFee = 0;
        float storageUnpaidFee = 0;

        for (BillFormalDetail detail : details) {
            float amount = detail.getAmount();
            float unpaidAmount = detail.getUnpaidAmount();

            switch (FeeType.valueOf(detail.getFeeType())) {
                case WEBEX_CONFERENCE_FEE:
                case WEBEX_CMR_FEE:
                    productFee += amount;
                    productUnpaidFee += unpaidAmount;
                    break;
                case WEBEX_STORAGE_FEE:
                    storageFee += amount;
                    storageUnpaidFee += unpaidAmount;
                    break;

                case WEBEX_PSTN_FEE:
                    pstnFee += amount;
                    break;

                case WEBEX_OVERFLOW_FEE:
                    overflowFee += amount;
                    break;

                case WEBEX_EC_DEPOSIT:
                case WEBEX_FIRST_INSTALLMENT:
                    break;
            }
        }

        rptConfig.setPstnFee(pstnFee);
        rptConfig.setUnpaidPstnFee(pstnFee);
        rptConfig.setProductFee(productFee);
        rptConfig.setUnpaidProductFee(productUnpaidFee);

        rptConfig.setStorageMonthlyFee(storageFee);
    }

    private void _getSiteStorageUsageFee(
        RptConfig rptConfig,
        String customerId)
        throws Exception {

        // find sites
        List<BssStorageUsageLog> storageUsages = storageUsageRepo.findByCustomerIdAndAccountPeriod(
            customerId,
            billingPeriod
        );

        Map<String, SiteStorageUsage> siteStorageFeeMap = new HashMap<>();

        for (BssStorageUsageLog fee : storageUsages) {

            SiteStorageUsage usage = new SiteStorageUsage();

            double x = ((fee.getStorageOverflowSize() + 4) / 5)
                * fee.getStorageOverflowUnitPrice();

            usage.setFee(x);

            usage.setOrderSize(fee.getStorageOrderSize());
            usage.setOverflowSize(fee.getStorageOverflowSize());
            usage.setUsedSize(fee.getStorageUsedSize());
            usage.setPrice(fee.getStorageOverflowUnitPrice());
            usage.setSiteName(fee.getSiteName());

            siteStorageFeeMap.put(fee.getSiteName(), usage);
        }

        rptConfig.setSiteStorageUsageFee(siteStorageFeeMap);

    }


    private void _getPortsOverflowFee2(
        RptConfig rptConfig,
        String customerId) throws Exception {


        Map<String, List<PortChargeSchema>> chargeMap =
            crmDataService.findPortChargeSchema(customerId, billingPeriod);


        List<PortsOverflowDetail> details = portsOverflowDetailRepository
            .findByCustomerIdAndBillPeriod(customerId, billingPeriod);

        Map<String, PortsUsageInfo> sitePortOverflowFee =
            _getSitePortOverflowFee(chargeMap, details);


        Map<String, SitePortsOverflowStatistics> sitePortsOverflowDetails =
            _getSitePortsOverflowDetails(chargeMap, details);

        rptConfig.setSiteFeeMap(sitePortOverflowFee);
        rptConfig.setSitePortsOverflowStatisticsMap(
            sitePortsOverflowDetails);

    }

    private Map<String, SitePortsOverflowStatistics>
    _getSitePortsOverflowDetails(Map<String, List<PortChargeSchema>> chargeMap,
                                 List<PortsOverflowDetail> details) {

        Map<String, SitePortsOverflowStatistics> map
            = new HashMap<>();


        for (PortsOverflowDetail overflowDetail : details) {
            String siteName = overflowDetail.getSiteName();

            PortChargeSchema charge = _getCharge(
                chargeMap, overflowDetail, siteName);

            if (charge.getPorts() >= overflowDetail.getPeakNumber()) continue;

            SitePortsOverflowStatistics s;

            if (map.containsKey(siteName)) {
                s = map.get(siteName);
            } else {
                s = new SitePortsOverflowStatistics();
                map.put(siteName, s);
            }


            s.add(
                overflowDetail.getConfId(),
                overflowDetail.getConfName(),
                overflowDetail.getPeakTime(),
                overflowDetail.getMeetingPeakNumber(),
                overflowDetail.getUserName(),
                overflowDetail.getUserEmail(),
                overflowDetail.getMeetingPeakNumber()
            );

        }

        return map;

    }

    private Map<String, PortsUsageInfo> _getSitePortOverflowFee(
        Map<String, List<PortChargeSchema>> chargeMap,
        List<PortsOverflowDetail> details) {

        // port overflow fee per site
        Map<String, PortsUsageInfo> siteFeeMap = new HashMap<>();


        for (PortsOverflowDetail detail : details) {

            String siteName = detail.getSiteName();
            PortChargeSchema charge = _getCharge(chargeMap, detail, siteName);

            if (detail.getPeakNumber() <= charge.getPorts()) continue;

            if (siteFeeMap.containsKey(siteName)) continue;


            float fee = charge.getOverflowPrice().multiply(BigDecimal.valueOf(
                detail.getPeakNumber() - charge.getPorts()))
                .floatValue();


            PortsUsageInfo portsUsageInfo = new PortsUsageInfo();
            portsUsageInfo.setSiteName(siteName);
            portsUsageInfo.setTotalPorts(charge.getPorts());
            portsUsageInfo.setUsedPorts(detail.getPeakNumber());
            portsUsageInfo.setOverflowFee(fee);

            siteFeeMap.put(siteName, portsUsageInfo);
        }

        return siteFeeMap;
    }

    private PortChargeSchema _getCharge(Map<String, List<PortChargeSchema>> chargeMap,
                                        PortsOverflowDetail overflowDetail,
                                        String siteName) {

        PortChargeSchema charge = null;

        List<PortChargeSchema> portChargeSchemas = chargeMap.get(siteName);

        if (portChargeSchemas != null && portChargeSchemas.size() > 0) {
            for (PortChargeSchema x : portChargeSchemas) {
                if (x.getStartDate().compareTo(overflowDetail.getEndTime()) <= 0 &&
                    x.getEndDate().compareTo(overflowDetail.getStartTime()) >= 0) {
                    charge = x;
                    break;
                }
            }
        }

        if (charge == null) {
            throw new RuntimeException(
                "ports charge schema not found for site " + siteName);
        }

        return charge;
    }

    @Deprecated
    private void _getPortsOverflowFee(List<String> orderIds, AccountPeriod accountPeriod,
                                      RptConfig rptConfig, String customerId) throws Exception {
        List<BillFormalDetail> overflows = billFormalDetailRepository.findByAccountPeriodAndFeeTypeAndOrderIdIn(
            accountPeriod.toString(), FeeType.WEBEX_OVERFLOW_FEE.getValue(), orderIds);

        if (overflows == null || overflows.size() == 0) return;

        Map<String, PortsUsageInfo> siteFeeMap = new HashMap<>();
        Map<String, String> orderIdSiteNameMap = crmDataService.getSiteNameByOrderIds(orderIds);


        for (BillFormalDetail overflow : overflows) {
            String siteName = orderIdSiteNameMap.get(overflow.getOrderId());
            if (siteName == null) {
                throw new RuntimeException("site not found for order " + overflow.getOrderId());
            }
            PortsUsageInfo portsUsageInfo = siteFeeMap.get(siteName);
            if (portsUsageInfo == null) {
                portsUsageInfo = new PortsUsageInfo();
                portsUsageInfo.setSiteName(siteName);
                portsUsageInfo.setOverflowFee(overflow.getAmount());
                siteFeeMap.put(siteName, portsUsageInfo);
            } else {
                portsUsageInfo.setOverflowFee(
                    portsUsageInfo.getOverflowFee() + overflow.getAmount()
                );
            }
        }


        for (PortsUsageInfo portsUsageInfo : siteFeeMap.values()) {
            String siteName = portsUsageInfo.getSiteName();

            int maxPorts = edrService.getMaxPorts(customerId, siteName, billingPeriod);
            portsUsageInfo.setUsedPorts(maxPorts);
            // portsUsageInfo.setTotalPorts("this is set in ...");
        }

        Map<String, SitePortsOverflowStatistics> sitePortsOverflowStatisticsMap = new HashMap<>();

        List<String> overflowedSiteNames = CollectionUtils.collectString(siteFeeMap.values(), "siteName");
        List<PortsOverflowDetail> overflowDetails = portsOverflowDetailRepository.findByCustomerIdAndBillPeriodAndSiteNameIn(
            customerId,
            billingPeriod,
            overflowedSiteNames
        );

        Collections.sort(overflowDetails, new Comparator<PortsOverflowDetail>() {
            @Override
            public int compare(PortsOverflowDetail o1, PortsOverflowDetail o2) {
                return o1.getStartTime().compareTo(o2.getStartTime());
            }
        });

        for (PortsOverflowDetail overflowDetail : overflowDetails) {
            String siteName = overflowDetail.getSiteName();

            SitePortsOverflowStatistics sitePortsOverflowStatistics = sitePortsOverflowStatisticsMap.get(siteName);


            if (sitePortsOverflowStatistics == null) {
                sitePortsOverflowStatistics = new SitePortsOverflowStatistics();
                sitePortsOverflowStatisticsMap.put(siteName, sitePortsOverflowStatistics);
            }

            sitePortsOverflowStatistics.add(
                overflowDetail.getConfId(),
                overflowDetail.getConfName(),
                overflowDetail.getPeakTime(),
                overflowDetail.getMeetingPeakNumber(),
                overflowDetail.getUserName(),
                overflowDetail.getUserEmail(),
                overflowDetail.getMeetingPeakNumber()
            );

        }

        rptConfig.setSitePortsOverflowStatisticsMap(sitePortsOverflowStatisticsMap);
        rptConfig.setSiteFeeMap(siteFeeMap);
    }

    private void doGeneratePdf(RptConfig rptConfig) throws IOException, DocumentException {
        VelocityEngine ve = new VelocityEngine();
        ve.setProperty(RuntimeConstants.RESOURCE_LOADER, "classpath");
        ve.setProperty("classpath.resource.loader.class", ClasspathResourceLoader.class.getName());
        ve.setProperty("eventhandler.referenceinsertion.class", InsertHanler.class.getName());
        ve.setProperty(RuntimeConstants.EVENTHANDLER_INCLUDE, IncludeRelativePath.class.getName());

        ve.init();

        Template template = ve.getTemplate("wang/huaichao/html/report.vm", "utf-8");

        DateTool dateTool = new DateTool();
        Map<String, String> config = new HashMap<>();
        config.put(DateTool.TIMEZONE_KEY, "GMT+16");
        dateTool.configure(config);

        VelocityContext vctx = new VelocityContext();
        vctx.put("config", rptConfig);
        vctx.put("numberTool", new NumberTool());
        vctx.put("dateTool", dateTool);
//        vctx.put("esc", new EscapeTool());
        vctx.put("esc", new EscapeToolCustom());


        StringWriter sw = new StringWriter();
        template.merge(vctx, sw);

        String html = sw.toString();


        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        PdfCreator.render(html, baos);

        BillPdf billPdf = new BillPdf();
        billPdf.setId(billingPeriod + ":" + rptConfig.getCustomerId());
        billPdf.setCustomerId(rptConfig.getCustomerId());
        billPdf.setCustomerCode(rptConfig.getCustomerCode());
        billPdf.setPdfContent(baos.toByteArray());
        billPdf.setCreatedAt(new Date());
        billPdf.setBillingPeriod(billingPeriod);

        billPdfRepository.save(billPdf);

    }


    public static class InsertHanler implements ReferenceInsertionEventHandler {
        public Object referenceInsert(String reference, Object value) {
            if (value == null) return value;
            return value.toString().replaceAll("&", "&amp;");
        }
    }
}
