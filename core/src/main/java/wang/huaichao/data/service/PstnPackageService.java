package wang.huaichao.data.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import wang.huaichao.Global;
import wang.huaichao.data.entity.crm.PstnPackage;
import wang.huaichao.data.entity.crm.PstnPackageOrder;
import wang.huaichao.data.entity.crm.PstnPackageUsage;
import wang.huaichao.data.repo.PstnPackageRepository;
import wang.huaichao.exception.InvalidBillingOperationException;
import wang.huaichao.utils.DateBuilder;
import wang.huaichao.utils.DateUtils;
import wang.huaichao.utils.StringUtils;

import javax.transaction.Transactional;
import java.io.IOException;
import java.text.ParseException;
import java.util.*;

/**
 * Created by Administrator on 8/22/2016.
 */
@Service
@Transactional
public class PstnPackageService {
    private static final Logger log = LoggerFactory.getLogger(PstnPackageService.class);

    @Autowired
    private PstnPackageRepository pstnPackageRepository;

    @Autowired
    private CrmDataService crmDataService;

    /**
     * @param orderRemainMinutesMap orderId -> left minutes
     */
    public void importPstnPackageFromBSS1_0(Map<String, Integer> orderRemainMinutesMap, int lastBillingPeriod)
            throws InvalidBillingOperationException, IOException {
        ArrayList<String> orderIds = new ArrayList<>(orderRemainMinutesMap.keySet());
        List<PstnPackage> pkgs = pstnPackageRepository.findByOrderIdIn(orderIds);
        if (pkgs != null && pkgs.size() > 0) {
            throw new InvalidBillingOperationException("import failure: PSTN package exists");
        }
        Map<String, PstnPackageOrder> orders = crmDataService.findPstnPackagesByOrderIds(orderIds);

        pkgs = new ArrayList<>();

        for (PstnPackageOrder order : orders.values()) {
            System.out.println(order.getOrderId() + "," + order.getMinitues());
            Integer leftMinutes = orderRemainMinutesMap.get(order.getOrderId());

            PstnPackage pkg = new PstnPackage();
            pkg.setOrderId(order.getOrderId());
            pkg.setSiteName("@");
            pkg.setTotalMinutes(order.getMinitues());
            pkg.setLeftMinutes(leftMinutes);
            pkg.setJuly2016LeftMinutes(leftMinutes);
            pkg.setCustomerId(order.getCustomerId());
            pkg.setFirstBillingPeriod(lastBillingPeriod);
            pkg.setLastBillingPeriod(lastBillingPeriod);
            pkg.setStartDate(order.getStartDate());
            pkg.setEndDate(order.getEndDate());


            pkgs.add(pkg);
        }

        pstnPackageRepository.save(pkgs);
    }

    @Transactional
    public void updateJuly2016LeftMinutes(Map<String, Integer> orderRemainMinutesMap) throws IOException {

        for (String orderId : orderRemainMinutesMap.keySet()) {
            List<PstnPackage> pkgs = pstnPackageRepository.findByOrderId(orderId);
            if (pkgs == null || pkgs.size() == 0) continue;

            if (pkgs.size() > 1) {
                throw new RuntimeException("====== monthly package ======");
            }

            PstnPackage pkg = pkgs.get(0);

            pkg.setJuly2016LeftMinutes(orderRemainMinutesMap.get(orderId));

            pstnPackageRepository.save(pkg);

        }

    }

    public List<PstnPackage> getOrCreatePstnPackage(PstnPackageOrder order, int billingPeriod) {
        String orderId = order.getOrderId();

        List<PstnPackage> pkgs = pstnPackageRepository.findByOrderId(orderId);

        // check package info (effective date range, minutes)
        // consists with that of order, not modified
        if (pkgs != null && pkgs.size() > 0) {
            boolean used = checkIfPackageUsed(pkgs, billingPeriod);

            // used &
            // total minutes changed
            if (used && pkgs.get(0).getTotalMinutes() != order.getMinitues()) {
                throw new RuntimeException(
                        StringUtils.replace(
                                "pkg used & mins modified, order id {}",
                                order.getOrderId()
                        )
                );
            }

            if (order.getPackageType() == PstnPackageOrder.PackageType.PSTN_MONTHLY_PACKET) {

                // month amount changed
                if (pkgs.size() != order.getMonths()) {
                    if (used) {
                        throw new RuntimeException(
                                StringUtils.replace(
                                        "purchased months changed, order id {}",
                                        orderId
                                )
                        );
                    } else {
                        pstnPackageRepository.deleteByOrderId(orderId);
                        pkgs = createPstnPackages(order);
                        pstnPackageRepository.save(pkgs);
                    }
                }

                // exclude packages out of current billing month, to speed up calculating pstn fee
                pkgs = reduceMonthlyPackages(pkgs, billingPeriod);
            }

            Date start = _getStartDate(pkgs);
            Date end = _getEndDate(pkgs);

            // order effective date range changed, update package start/end date accordingly
            if (start.equals(order.getStartDate()) == false || end.equals(order.getEndDate()) == false) {
                for (PstnPackage pkg : pkgs) {
                    pkg.setStartDate(DateUtils.max(pkg.getStartDate(), order.getStartDate()));
                    pkg.setEndDate(DateUtils.min(pkg.getEndDate(), order.getEndDate()));
                }
                pstnPackageRepository.save(pkgs);
            }
        } else {
            pkgs = createPstnPackages(order);
            pstnPackageRepository.save(pkgs);
        }

        return pkgs;
    }

    private Date _getStartDate(List<PstnPackage> packages) {
        Date min = null, date;

        for (PstnPackage pkg : packages) {
            date = pkg.getStartDate();
            if (min == null) min = date;
            else {
                min = min.compareTo(date) > 0 ? date : min;
            }
        }

        return new Date(min.getTime());
    }

    private Date _getEndDate(List<PstnPackage> packages) {
        Date max = null, date;

        for (PstnPackage pkg : packages) {
            date = pkg.getStartDate();
            if (max == null) max = date;
            else {
                max = max.compareTo(date) > 0 ? max : date;
            }
        }

        return new Date(max.getTime());
    }

    private List<PstnPackage> reduceMonthlyPackages(List<PstnPackage> packages, int billPeriod) {
        DateBuilder db;

        try {
            db = new DateBuilder(Global.yyyyMM_FMT.parse(billPeriod + ""));
        } catch (ParseException e) {
            throw new RuntimeException("incorrect billing period format: " + billPeriod);
        }

        Date start = db.beginOfMonth().build();
        Date end = db.nextMonth().build();

        List<PstnPackage> rst = new ArrayList<>();

        for (PstnPackage pkg : packages) {
            if (start.getTime() < pkg.getEndDate().getTime() && end.getTime() > pkg.getStartDate().getTime())
                rst.add(pkg);
        }
        return rst;
    }

    public Map<String, List<PstnPackage>> getOrCreatePstnPackageForOrders(
            List<PstnPackageOrder> orders, int billingPeriod) {

        Map<String, List<PstnPackage>> map = new HashMap<>();

        for (PstnPackageOrder order : orders) {
            List<PstnPackage> list = getOrCreatePstnPackage(order, billingPeriod);


            // ignore exhaust package

            List<PstnPackage> pkgsWithMinutes = new ArrayList<>();

            for (PstnPackage pkg : list) {
                if (pkg.getLastBillingPeriod() != null &&
                        pkg.getLastBillingPeriod() < billingPeriod &&
                        pkg.getLeftMinutes() == 0)
                    continue;
                pkgsWithMinutes.add(pkg);
            }

            if (pkgsWithMinutes.size() == 0) continue;

            // end ignore

            map.put(order.getOrderId(), pkgsWithMinutes);
        }

        return map;
    }

    private boolean checkIfOrderEffectivePeriodModified(PstnPackageOrder pstnPackageOrder,
                                                        List<PstnPackage> pstnPackages) {
        final boolean modified = true;

        // PSTN monthly package, we split it into multiple single package each with only one month validity

        if (pstnPackageOrder.getPackageType() == PstnPackageOrder.PackageType.PSTN_MONTHLY_PACKET) {

            Date start = pstnPackages.get(0).getStartDate();
            Date end = pstnPackages.get(0).getEndDate();

            // union date range of separate package

            for (PstnPackage pstnPackage : pstnPackages) {
                if (start.getTime() > pstnPackage.getStartDate().getTime()) {
                    start = pstnPackage.getStartDate();
                }

                if (end.getTime() < pstnPackage.getEndDate().getTime()) {
                    end = pstnPackage.getEndDate();
                }
            }

            // monthly package has been modified
            if (pstnPackageOrder.getMonths() != pstnPackages.size() ||
                    pstnPackageOrder.getMinitues() != pstnPackages.get(0).getTotalMinutes() ||
                    pstnPackageOrder.getStartDate().getTime() != start.getTime() ||
                    pstnPackageOrder.getEndDate().getTime() != end.getTime()) {
                return modified;
            }


        } else {
            if (pstnPackages.size() != 1) {
                return modified;
            }

            PstnPackage pstnPackage = pstnPackages.get(0);

            if (pstnPackage.getStartDate().getTime() != pstnPackageOrder.getStartDate().getTime() ||
                    pstnPackage.getEndDate().getTime() != pstnPackageOrder.getEndDate().getTime()) {
                return modified;
            }
        }

        return !modified;
    }

    private boolean checkIfPackageUsed(List<PstnPackage> pstnPackages, int billPeriod) {
        boolean used = true;

        for (PstnPackage pstnPackage : pstnPackages) {
            if (pstnPackage.getLastBillingPeriod() == null || pstnPackage.getFirstBillingPeriod() == billPeriod)
                continue;
            return used;
        }

        return !used;
    }

    private boolean checkIfAllowDeductForBillingPeriod(List<PstnPackage> pstnPackages, int billPeriod) {
        boolean allow = true;
        for (PstnPackage pstnPackage : pstnPackages) {
            Integer lastBillingPeriod = pstnPackage.getLastBillingPeriod();
            if (lastBillingPeriod != null && lastBillingPeriod > billPeriod)
                return !allow;
        }
        return allow;
    }

    private List<PstnPackage> createPstnPackages(PstnPackageOrder order) {
        Date startDate = order.getStartDate();
        Date endDate = order.getEndDate();
        List<PstnPackage> pstnPackages = new ArrayList<>();
        int minitues = order.getMinitues();
        String orderId = order.getOrderId();

        if (order.getPackageType() == PstnPackageOrder.PackageType.PSTN_MONTHLY_PACKET) {
            int months = order.getMonths();
            int cnt = 0;

            DateBuilder dateBuilder = new DateBuilder(startDate);
            while (dateBuilder.build().getTime() < endDate.getTime() && (cnt++ < months)) {
                PstnPackage pstnPackage = new PstnPackage();

                pstnPackage.setStartDate(dateBuilder.build());
                pstnPackage.setEndDate(DateUtils.min(dateBuilder.nextMonth().build(), endDate));
                pstnPackage.setTotalMinutes(minitues);
                pstnPackage.setOrderId(orderId);

                pstnPackage.setCustomerId(order.getCustomerId());
                pstnPackage.setSiteName("fix me");
                pstnPackage.setLeftMinutes(minitues);

                pstnPackages.add(pstnPackage);
            }
        } else {
            PstnPackage pstnPackage = new PstnPackage();

            pstnPackage.setStartDate(startDate);
            pstnPackage.setEndDate(endDate);
            pstnPackage.setTotalMinutes(minitues);
            pstnPackage.setOrderId(orderId);

            pstnPackage.setCustomerId(order.getCustomerId());
            pstnPackage.setSiteName("fix me");
            pstnPackage.setLeftMinutes(minitues);

            pstnPackages.add(pstnPackage);
        }

        return pstnPackages;
    }
}