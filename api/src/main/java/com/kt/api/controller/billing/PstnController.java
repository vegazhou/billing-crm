package com.kt.api.controller.billing;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import wang.huaichao.data.entity.crm.BillingProgress;
import wang.huaichao.data.entity.crm.BillingProgressPK;
import wang.huaichao.data.repo.BillingProgressRepository;
import wang.huaichao.data.service.BillingService;
import wang.huaichao.exception.BillingException;
import wang.huaichao.exception.InvalidBillingOperationException;

import java.io.IOException;
import java.text.ParseException;
import java.util.List;
import java.util.concurrent.Future;

/**
 * Created by Administrator on 1/18/2017.
 */

@RestController
@RequestMapping("/pstn")
public class PstnController {
    @Autowired
    private BillingProgressRepository billingProgressRepository;

    @Autowired
    private BillingService billingService;

    @RequestMapping("/status")
    public ActionResult status(
        @RequestParam(defaultValue = "0") int pageNumber,
        @RequestParam(defaultValue = "10") int pageSize) {

        pageNumber = Math.max(0, pageNumber);
        pageSize = Math.max(1, pageSize);

        Page<BillingProgress> progresses =
            billingProgressRepository.findByTypeOrderByBillingPeriodDesc(
                BillingProgressPK.BillingProgressType.BATCH_PSTN_FEE_CALCULATION,
                new PageRequest(pageNumber, pageSize)
            );

        return new ActionResult().addPayload("page", progresses);
    }

    @RequestMapping("/calculate/{billingPeriod}")
    public ActionResult calculate(@PathVariable int billingPeriod) {
        try {
            billingService.batchCalculatingPstnFee(billingPeriod);
            return ActionResult.SUCCESS;
        } catch (Exception e) {
            return new ActionResult(true, e.getMessage());
        }
    }

    @RequestMapping("/status/{billingPeriod}")
    public ActionResult progressStatus(@PathVariable int billingPeriod) {
        List<BillingProgress> progresses = billingProgressRepository.findByBillingPeriodAndType(
            billingPeriod,
            BillingProgressPK.BillingProgressType.BATCH_PSTN_FEE_CALCULATION
        );
        return new ActionResult().addPayload("progresses", progresses);
    }
}

