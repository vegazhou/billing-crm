package com.kt.api.controller.billing;

import com.kt.entity.mysql.crm.Customer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import wang.huaichao.data.entity.crm.BillingProgress;
import wang.huaichao.data.entity.crm.BillingProgressPK;
import wang.huaichao.data.repo.BillingProgressRepository;
import wang.huaichao.data.repo.CustomerRepository;
import wang.huaichao.data.service.EmailService;
import wang.huaichao.utils.CollectionUtils;
import wang.huaichao.utils.StringUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by Administrator on 1/18/2017.
 */

@RestController
@RequestMapping("/email")
public class EmailController {
    private static final Logger log = LoggerFactory.getLogger(
        EmailController.class);

    @Autowired
    private BillingProgressRepository billingProgressRepository;

    @Autowired
    private EmailService emailService;

    @Autowired
    @Qualifier("CustomerRepo2")
    private CustomerRepository customerRepository;

    @RequestMapping("/status")
    public ActionResult status(
        @RequestParam(defaultValue = "0") int pageNumber,
        @RequestParam(defaultValue = "10") int pageSize) {

        pageNumber = Math.max(0, pageNumber);
        pageSize = Math.max(1, pageSize);

        Page<BillingProgress> progresses =
            billingProgressRepository.findByTypeOrderByBillingPeriodDesc(
                BillingProgressPK.BillingProgressType.BATCH_PDF_BILL_MAILING,
                new PageRequest(pageNumber, pageSize)
            );

        return new ActionResult().addPayload("page", progresses);
    }

    @RequestMapping("/send/{billingPeriod}")
    public ActionResult calculate(
        @PathVariable int billingPeriod,
        @RequestParam(required = false) String sendTo,
        @RequestParam(required = false) String ignores) {

        String[] split = ignores.split("[\r\n]+");
        List<String> strings = new ArrayList<>();

        for (String s : split) {
            s = s.trim();
            if (s.isEmpty()) {
                continue;
            }
            strings.add(s);
        }


        List<Customer> igns = customerRepository.findByDisplayNameIn(strings);

        if (igns.size() != strings.size()) {
            strings.removeAll(CollectionUtils.collectString(igns, ""));
            String notfound = StringUtils.join(strings, ", ");
            String message = "customers not found: " + notfound;
            log.error(message);
            return new ActionResult(true, message);
        }

        List<String> ignIds = CollectionUtils.collectString(igns, "pid");

        List<String> mailingCustomers = emailService.getMailingCustomers(billingPeriod);
        mailingCustomers.removeAll(ignIds);

        if (sendTo != null && sendTo.trim().isEmpty()) {
            sendTo = null;
        }

        try {

            emailService.batchSendPdfBill(
                mailingCustomers,
                billingPeriod,
                sendTo,
                true
            );

            return ActionResult.SUCCESS;


        } catch (Exception e) {

            return new ActionResult(true, e.getMessage());

        }
    }

    @RequestMapping("/status/{billingPeriod}")
    public ActionResult progressStatus(@PathVariable int billingPeriod) {

        List<BillingProgress> progresses =
            billingProgressRepository.findByBillingPeriodAndType(
                billingPeriod,
                BillingProgressPK.BillingProgressType.BATCH_PDF_BILL_MAILING
            );

        return new ActionResult().addPayload("progresses", progresses);
    }
}

