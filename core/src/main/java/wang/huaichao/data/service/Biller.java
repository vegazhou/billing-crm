package wang.huaichao.data.service;

import org.apache.velocity.app.event.ReferenceInsertionEventHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import wang.huaichao.Global;
import wang.huaichao.data.ds.*;
import wang.huaichao.data.entity.crm.*;
import wang.huaichao.data.entity.edr.CallDataRecord;
import wang.huaichao.data.repo.*;
import wang.huaichao.exception.InvalidBillingOperationException;
import wang.huaichao.utils.AsyncTaskUtils;
import wang.huaichao.utils.DateBuilder;

import javax.transaction.Transactional;
import java.io.*;
import java.math.BigDecimal;
import java.text.ParseException;
import java.util.*;
import java.util.concurrent.Future;


/**
 * Created by Administrator on 9/7/2016.
 */
public class Biller {
    private static final Logger log = LoggerFactory.getLogger(Biller.class);

    @Autowired
    private EdrDataService edrDataService;

    @Autowired
    private CrmDataService crmDataService;

    @Autowired
    private PstnPackageService pstnPackageService;

    @Autowired
    private MeetingRecordRepository meetingRecordRepository;

    @Autowired
    private PstnPackageUsageRepository pstnPackageUsageRepository;

    @Autowired
    private PstnPackageRepository pstnPackageRepository;

    @Autowired
    private PstnChargeRepository pstnChargeRepository;

    @Autowired
    private BillingProgressRepository billingProgressRepository;


    private int billingPeriod;
    private Date start;
    private Date end;

    public Biller(int billingPeriod) throws InvalidBillingOperationException {
        this.billingPeriod = billingPeriod;
        DateBuilder db;
        try {
            db = new DateBuilder(Global.yyyyMM_FMT.parse(billingPeriod + ""));
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
        start = db.beginOfMonth().build();
        end = db.nextMonth().build();

        if (!validBillingPeriod(start, end)) {
            throw new InvalidBillingOperationException("invalid billing period " + billingPeriod);
        }
    }

    @Async
    public Future<Integer> calculatingPstnFeeByCustomer(String customerId)
        throws ParseException, IOException, InvalidBillingOperationException {

        BillingProgressPK.BillingProgressType type = BillingProgressPK.BillingProgressType.SINGLE_PSTN_FEE_CALCULATION;

        BillingProgressPK pk = new BillingProgressPK();
        pk.setBillingPeriod(billingPeriod);
        pk.setCustomerId(customerId);
        pk.setType(type);
        BillingProgress one = billingProgressRepository.findOne(pk);

        if (_isInProgress(one, 10 * 60 * 1000)) {
            return AsyncTaskUtils.CANCELED;
        }

        BillingProgress bp = new BillingProgress();
        bp.setBillingPeriod(billingPeriod);
        bp.setCustomerId(customerId);
        bp.setType(type);
        bp.setStatus(BillingProgress.BillingProgressStatus.IN_PROGRESS);
        bp.setCreatedAt(new Date());
        bp.setStartTime(new Date());
        bp.setTotalTasks(1);
        billingProgressRepository.save(bp);

        Future<Integer> result = null;
        try {
            _calculatingPstnFeeByCustomer(customerId);
            bp.setSucceededTasks(1);
            result = AsyncTaskUtils.SUCCEEDED;
        } catch (Exception e) {
            bp.setFailedTasks(1);
            result = AsyncTaskUtils.FAILED;
            log.error("calculating pstn fee error, customer id " + customerId, e);
            throw e;
        } finally {
            bp.setEndTime(new Date());
            bp.setStatus(BillingProgress.BillingProgressStatus.COMPLETED);
            billingProgressRepository.save(bp);
            return result;
        }
    }

    public void _calculatingPstnFeeByCustomer(String customerId)
        throws ParseException, IOException, InvalidBillingOperationException {

        Map<String, PstnStandardChargeOrder> orderMap =
            crmDataService.findPstnStandardChargeOrderMapByCustomerAndEffectiveDateIntersect(customerId, start, end);

        /**
         * build calculating data structure:
         *
         * customer *-- site *-- price list
         *                *
         *                |
         *            pstn package
         *
         */
        Customer customer = buildCalculatingDataStructure(orderMap);
        customer.setCustomerId(customerId);


        // pstn package orders
        Map<String, PstnPackageOrder> pstnPackageOrderMap =
            crmDataService.findPstnPackagesByCustomerAndBillingPeroid(
                customerId,
                start,
                end
            );

        // pstn packages
        Map<String, List<PstnPackage>> pstnPackageListMap =
            pstnPackageService.getOrCreatePstnPackageForOrders(
                new ArrayList<>(pstnPackageOrderMap.values()),
                billingPeriod
            );

        // edr records
        List<CallDataRecord> callDataRecords = edrDataService.findPstnRecords(
            start, end, new ArrayList<>(customer.getSiteMap().keySet()));

        calculatingSingleCustomerPstnFee(
            customer,
            callDataRecords,
            pstnPackageOrderMap,
            pstnPackageListMap
        );
    }

    private boolean validBillingPeriod(Date start, Date end) {
        boolean valid = true;
        Date date = new DateBuilder().beginOfMonth().build();
        if (date.getTime() == start.getTime() || date.getTime() == end.getTime()) {
            return valid;
        }
        return !valid;
    }


    @Async
    public Future<Integer> redoFailedPstnCalculations() {
        List<BillingProgress> failedTasks = billingProgressRepository.findByBillingPeriodAndFailedTasks(billingPeriod, 1);
        for (BillingProgress failedTask : failedTasks) {
            try {
                calculatingPstnFeeByCustomer(failedTask.getCustomerId()).get();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return AsyncTaskUtils.SUCCEEDED;
    }

    @Async
    public Future batchCalculatingPstnFee() throws IOException, ParseException {
        List<String> customerIds = crmDataService.findStandardChargeCustomerByEffectiveDateIntersect(start, end);

        BillingProgress bp = new BillingProgress();
        bp.setBillingPeriod(billingPeriod);
        bp.setCreatedAt(new Date());
        bp.setStartTime(new Date());
        bp.setType(BillingProgressPK.BillingProgressType.BATCH_PSTN_FEE_CALCULATION);
        bp.setStatus(BillingProgress.BillingProgressStatus.IN_PROGRESS);
        bp.setTotalTasks(customerIds.size());
        billingProgressRepository.save(bp);

        int succeeded = 0;
        int failed = 0;

        for (String customerId : customerIds) {
            try {
                Future<Integer> result = calculatingPstnFeeByCustomer(customerId);

                if (result.get() == AsyncTaskUtils.FAILED_CODE) {
                    throw new RuntimeException("async task failed");
                }

                // increase succeeded tasks
                bp.setSucceededTasks(++succeeded);
                billingProgressRepository.save(bp);

            } catch (Exception e) {

                // increase failed tasks
                bp.setFailedTasks(++failed);
                billingProgressRepository.save(bp);

                log.error("failed to calculate PSTN fee on {}, for customer {}", billingPeriod, customerId);
                log.error("", e);
            }
        }

        // update complete status & end time
        bp.setStatus(BillingProgress.BillingProgressStatus.COMPLETED);
        bp.setEndTime(new Date());
        billingProgressRepository.save(bp);

        return null;
    }

    @Transactional
    public void calculatingSingleCustomerPstnFee(Customer customer,
                                                  List<CallDataRecord> callDataRecords,
                                                  Map<String, PstnPackageOrder> pstnPackageOrderMap,
                                                  Map<String, List<PstnPackage>> pstnPackageListMap)
        throws IOException, ParseException {

        ArrayList<String> siteNames = new ArrayList<>(customer.getSiteMap().keySet());

        // add pstn package/pool to site
        assignPstnPoolToSite(customer, pstnPackageOrderMap, pstnPackageListMap, siteNames);


        // **********************************************************************
        // billing ...
        // **********************************************************************

        // deduct from PSTN pool
        deductFromPstnPackageAndChargeUncoverdMins(callDataRecords, customer);

        // charge over used phone call minutes
//        chargeUncoverdMinutes(callDataRecords, customer);


        // **********************************************************************
        // save result to db ...
        // **********************************************************************

        // =====================================================================
        // save cdr with costs to data base
        // =====================================================================
        saveCdrCallRecordsWithCost(callDataRecords, siteNames, customer);


        // =====================================================================
        // update usage
        // =====================================================================
        updatePstnPackageAndUsage(customer);


        // =====================================================================
        // save standard charge result
        // =====================================================================
        savePstnChargeResult(customer);
    }

    private MeetingRecord CallDataRecord2MeetingRecord(CallDataRecord callDataRecord, String customerId) {
        MeetingRecord meetingRecord = new MeetingRecord();

        meetingRecord.setBillingPeriod(billingPeriod);
        meetingRecord.setConfId(callDataRecord.getConfId());
        meetingRecord.setConfName(callDataRecord.getConfName());
        meetingRecord.setCustomerId(customerId);
        meetingRecord.setSiteName(callDataRecord.getSiteName());
        meetingRecord.setUserName(callDataRecord.getUserName());
        meetingRecord.setHostName(callDataRecord.getHostName());
        meetingRecord.setStartTime(callDataRecord.getStartTime());
        meetingRecord.setEndTime(callDataRecord.getEndTime());
        meetingRecord.setUserNumber(callDataRecord.getUserNumber());
        meetingRecord.setOrderId(callDataRecord.getOrderId());

        String platformNumber = callDataRecord.getPlatformNumber();
        String num400 = PstnMetaData.CallIn400Map.get(platformNumber);
        if (num400 != null) {
            meetingRecord.setAccessNumber(num400);
        } else {
            String xxx = PstnMetaData.CallInAccessNumber2400NumberMap.get(platformNumber);
            meetingRecord.setAccessNumber(xxx == null ? platformNumber : xxx);
        }


        meetingRecord.setCallIn(callDataRecord.isCallIn());
        meetingRecord.setInternational(callDataRecord.isInternationalCall());
        meetingRecord.setAccessType(callDataRecord.getAccessType());
        meetingRecord.setDuration(callDataRecord.getDuration());
        meetingRecord.setCoverdMinutes(callDataRecord.getCoveredMinutes());
        meetingRecord.setUncoverdMinutes(callDataRecord.getUncoverdMinutes());
        meetingRecord.setPrice(callDataRecord.getRate());
        meetingRecord.setCost(callDataRecord.getCost());

        return meetingRecord;
    }

    private Customer buildCalculatingDataStructure(Map<String, PstnStandardChargeOrder> orderMap) {
        Customer customer = new Customer();

        for (PstnStandardChargeOrder order : orderMap.values()) {
            Map<String, Site> customerSiteMap = customer.getSiteMap();
            String siteName = order.getSiteName();
            Site customerSite = customerSiteMap.get(siteName);
            if (customerSite == null) {
                customerSite = new Site();
                customerSite.setSiteName(siteName);
                customerSiteMap.put(siteName, customerSite);
            }

            Map<String, ChargeScheme> siteOrderMap = customerSite.getChargeSchemeMap();
            String siteOrderId = order.getOrderId();
            ChargeScheme siteChargeScheme = siteOrderMap.get(siteOrderId);
            if (siteChargeScheme == null) {
                siteChargeScheme = new ChargeScheme();
                siteChargeScheme.setOrderId(siteOrderId);
                siteOrderMap.put(siteOrderId, siteChargeScheme);
            }
            siteChargeScheme.setStartDate(order.getStartDate());
            siteChargeScheme.setEndDate(order.getEndDate());
            siteChargeScheme.setPriceList(order.getPriceList());
        }

        return customer;
    }

    @Deprecated
    private void chargeUncoverdMinutes(List<CallDataRecord> callDataRecords, Customer customer) {
        for (CallDataRecord callDataRecord : callDataRecords) {

            if (callDataRecord.getUncoverdMinutes() == 0) continue;

            Site site = customer.getSite(callDataRecord.getSiteName());
            ChargeScheme chargeScheme;

            try {
                chargeScheme = site.getChargeSchemeByTime(callDataRecord.getStartTime());
            } catch (Exception e) {
                continue;
            }

            BigDecimal rate = chargeScheme.getPriceList().getRate(
                callDataRecord.isInternationalCall(),
                callDataRecord.isCallIn(),
                callDataRecord.getPlatformNumber(),
                callDataRecord.getDestCountryCode()
            );

            callDataRecord.setOrderId(chargeScheme.getOrderId());
            callDataRecord.setRate(rate);
            site.addCost(callDataRecord.getCost());
            chargeScheme.accumulateCost(callDataRecord.getCost());
            chargeScheme.accumulateUncoverdMinutes(callDataRecord.getUncoverdMinutes());
        }
    }

    private void assignPstnPoolToSite(Customer customer,
                                      Map<String, PstnPackageOrder> pstnPackageOrderMap,
                                      Map<String, List<PstnPackage>> pstnPackageListMap,
                                      ArrayList<String> siteNames) {
        for (List<PstnPackage> pstnPackages : pstnPackageListMap.values()) {
            for (PstnPackage pstnPackage : pstnPackages) {
                PstnPool pstnPool = new PstnPool();

                pstnPool.setOrderId(pstnPackage.getOrderId());
                pstnPool.setPackageId(pstnPackage.getId());

                // re-billing process,
                if (pstnPackage.getLastBillingPeriod() != null && pstnPackage.getLastBillingPeriod() == billingPeriod) {
                    int used = pstnPackage.GetHistoryUsageByBillingPeriod(billingPeriod);
                    pstnPool.setAvailableMinutes(pstnPackage.getLeftMinutes() + used);
                } else {
                    pstnPool.setAvailableMinutes(pstnPackage.getLeftMinutes());
                }

                pstnPool.setStartDate(pstnPackage.getStartDate());
                pstnPool.setEndDate(pstnPackage.getEndDate());
                pstnPool.setPstnPackage(pstnPackage);

                PstnPackageOrder order = pstnPackageOrderMap.get(pstnPackage.getOrderId());

                // assign PSTN package to sites
                if (order.getPackageType() == PstnPackageOrder.PackageType.PSTN_SINGLE_PACKET_FOR_ALL_SITES) {
                    for (String siteName : siteNames) {
                        customer.getSite(siteName).addPstnPool(pstnPool);
                    }
                } else {
                    for (String siteName : order.getSites()) {
                        customer.getSite(siteName).addPstnPool(pstnPool);
                    }
                }
            }
        }

        // sort pstn pool by end date ASC
        for (String siteName : siteNames) {
            Collections.sort(customer.getSite(siteName).getPstnPools(), new Comparator<PstnPool>() {
                @Override
                public int compare(PstnPool o1, PstnPool o2) {
                    return (int) (o1.getEndDate().getTime() - o2.getEndDate().getTime());
                }
            });
        }
    }

    private void deductFromPstnPackageAndChargeUncoverdMins(List<CallDataRecord> callDataRecords, Customer customer) {
        for (CallDataRecord callDataRecord : callDataRecords) {
            callDataRecord.calculate();


            int left;

            if (callDataRecord.isInternationalCall()) {

                left = callDataRecord.getDuration();

                log.debug("=== ignore international call === minutes {} {} {}",
                    left, callDataRecord.getConfId(), callDataRecord.getUserNumber());

            } else {

                String siteName = callDataRecord.getSiteName();

                Site site = customer.getSite(siteName);

                left = site.deductFromPstnPackage(
                    callDataRecord.getStartTime(),
                    callDataRecord.getEndTime(),
                    callDataRecord.getDuration()
                );

            }

            callDataRecord.setUncoverdMinutes(left);


            Site site = customer.getSite(callDataRecord.getSiteName());
            ChargeScheme chargeScheme;

            try {
                chargeScheme = site.getChargeSchemeByTime(callDataRecord.getStartTime());
            } catch (Exception e) {
                log.error("no charge schema {} {}",
                    customer.getCustomerId(),
                    callDataRecord.getConfId());
                continue;
            }

            BigDecimal rate = chargeScheme.getPriceList().getRate(
                callDataRecord.isInternationalCall(),
                callDataRecord.isCallIn(),
                callDataRecord.getPlatformNumber(),
                callDataRecord.getDestCountryCode()
            );

            callDataRecord.setOrderId(chargeScheme.getOrderId());
            callDataRecord.setRate(rate);
            site.addCost(callDataRecord.getCost());
            chargeScheme.accumulateCost(callDataRecord.getCost());
            chargeScheme.accumulateUncoverdMinutes(callDataRecord.getUncoverdMinutes());
        }
    }

    private void saveCdrCallRecordsWithCost(List<CallDataRecord> callDataRecords, List<String> siteNames, Customer customer) {
        // delete cdr call cost for recalculating
        // special case: site is referred by multiple customer
        meetingRecordRepository.deleteByBillingPeriodAndCustomerIdAndSiteNameIn(
            billingPeriod, customer.getCustomerId(), siteNames);

        // saving each cdr call cost to db
        List<MeetingRecord> meetingRecords = new ArrayList<>();
        for (CallDataRecord callDataRecord : callDataRecords) {
            MeetingRecord meetingRecord = CallDataRecord2MeetingRecord(callDataRecord, customer.getCustomerId());
            meetingRecords.add(meetingRecord);

            if (meetingRecords.size() >= 200) {
                meetingRecordRepository.save(meetingRecords);
                meetingRecords.clear();
            }
        }

        if (meetingRecords.size() > 0) {
            meetingRecordRepository.save(meetingRecords);
        }
    }

    private void updatePstnPackageAndUsage(Customer customer) {

        Map<String, PstnPool> map = new HashMap<>();
        for (Site site : customer.getSiteMap().values()) {
            for (PstnPool pstnPool : site.getPstnPools()) {
                if (!map.containsKey(pstnPool.getOrderId())) {
                    map.put(pstnPool.getOrderId(), pstnPool);
                }
            }
        }

        List<PstnPackage> packages = new ArrayList<>();
        List<PstnPackageUsage> usages = new ArrayList<>();

        for (PstnPool pstnPool : map.values()) {
            // not used
//            if (pstnPool.getAvailableMinutes() == pstnPool.getTotalMinutes()) continue;

            PstnPackage pkg = pstnPool.getPstnPackage();
            pkg.setLeftMinutes(pstnPool.getAvailableMinutes());
            pkg.setLastBillingPeriod(billingPeriod);
            if (pkg.getFirstBillingPeriod() == null) {
                pkg.setFirstBillingPeriod(billingPeriod);
            }
            packages.add(pkg);


            PstnPackageUsage usage = new PstnPackageUsage();
            usage.setBillingPeriod(billingPeriod);
            usage.setPackageId(pstnPool.getPackageId());
            usage.setUsedMinutes(pstnPool.getTotalMinutes() - pstnPool.getAvailableMinutes());
            usages.add(usage);
        }

        if (usages.size() > 0) {
            pstnPackageUsageRepository.save(usages);
            pstnPackageRepository.save(packages);
        }
    }

    private void savePstnChargeResult(Customer customer) {
        List<PstnCharge> charges = new ArrayList<>();

        for (Site site : customer.getSiteMap().values()) {
            for (ChargeScheme chargeScheme : site.getChargeSchemeMap().values()) {
                PstnCharge pstnCharge = new PstnCharge();

                pstnCharge.setBillingPeriod(billingPeriod);
                pstnCharge.setCost(chargeScheme.getCost());
                pstnCharge.setSiteName(site.getSiteName());
                pstnCharge.setCustomerId(customer.getCustomerId());
                pstnCharge.setUncoverdMinutes(chargeScheme.getUncoverdMinutes());
                pstnCharge.setOrderId(chargeScheme.getOrderId());
                charges.add(pstnCharge);
            }
        }

        pstnChargeRepository.save(charges);
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


    public static class InsertHanler implements ReferenceInsertionEventHandler {
        public Object referenceInsert(String reference, Object value) {
            if (value == null) return value;
            return value.toString().replaceAll("&", "&amp;");
        }
    }
}
