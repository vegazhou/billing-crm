package com.kt.api.controller;

import com.kt.api.bean.rate.PstnRate;
import com.kt.api.bean.rate.PstnRates4Get;
import com.kt.api.bean.rate.PstnRates4Put;
import com.kt.api.common.APIConstants;
import com.kt.biz.bean.PstnRateBean;
import com.kt.common.exception.ApiException;
import com.kt.entity.mysql.crm.Rate;
import com.kt.exception.WafException;
import com.kt.service.PstnRateService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by Vega Zhou on 2016/4/28.
 */
@Controller
@RequestMapping("/pstnrate")
public class PstnRateController extends BaseController {

    private static final Logger LOGGER = Logger.getLogger(PstnRateController.class);

    @Autowired
    PstnRateService pstnRateService;

    @RequestMapping(value = "/{id}", method = RequestMethod.GET, produces = APIConstants.MEDIATYPE)
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public PstnRates4Get get(@PathVariable String id) {
        try {
            List<PstnRateBean> rates = pstnRateService.findById(id);
            return convert2bean(id, rates);
        } catch (WafException e) {
            LOGGER.error("error occurred while retrieving Product " + id, e);
            throw new ApiException(e.getKey());
        }
    }


    @RequestMapping(value = "/{id}", method = RequestMethod.PUT, consumes = APIConstants.MEDIATYPE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void update(@Valid @RequestBody PstnRates4Put bean, @PathVariable String id) {
        try {
            pstnRateService.updatePriceList(bean.getPid(), covert2entity(bean.getRates()));
        } catch (WafException e) {
            LOGGER.error("error occurred while updating PstnRate " + id, e);
            throw new ApiException(e.getKey());
        }
    }


    private PstnRates4Get convert2bean(String id, List<PstnRateBean> rates) {
        PstnRates4Get result = new PstnRates4Get();
        result.setPid(id);
        for (PstnRateBean rate : rates) {
            result.add(convert2bean(rate));
        }
        return result;
    }

    private PstnRate convert2bean(PstnRateBean rate) {
        PstnRate result = new PstnRate();
        result.setCode(rate.getCode());
        result.setRate(rate.getRate());
        result.setListPriceRate(rate.getListPriceRate());
        result.setListPriceServiceRate(rate.getListPriceServiceRate());
        result.setServiceRate(rate.getServiceRate());
        result.setDisplayName(rate.getDisplayName());
        return result;
    }

    private List<Rate> covert2entity(List<PstnRate> beans) {
        List<Rate> result = new LinkedList<Rate>();
        for (PstnRate bean : beans) {
            result.add(convert2entity(bean));
        }
        return result;
    }

    private Rate convert2entity(PstnRate bean) {
        Rate result = new Rate();
        result.setCode(bean.getCode());
        result.setDisplayName(bean.getDisplayName());
        result.setRate(bean.getRate());
        result.setServiceRate(bean.getServiceRate());
        return result;
    }
}
