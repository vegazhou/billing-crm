package com.kt.service;

import com.kt.biz.bean.ContractBean;
import com.kt.biz.model.charge.AbstractChargeScheme;
import com.kt.biz.model.charge.impl.PSTNStandardCharge;
import com.kt.biz.model.charge.impl.WebExConfMonthlyPayByHosts;
import com.kt.biz.model.charge.impl.WebExConfMonthlyPayByPorts;
import com.kt.biz.model.order.OrderBean;
import com.kt.biz.site.WebExSitePrimaryFields;
import com.kt.biz.types.*;
import com.kt.biz.validators.WebExSiteNameValidator;
import com.kt.entity.mysql.crm.*;
import com.kt.exception.*;
import com.kt.repo.mysql.batch.OrderRepository;
import com.kt.repo.mysql.batch.WebExSiteRepository;
import com.kt.session.PrincipalContext;
import com.kt.util.DateUtil;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.Date;

/**
 * Created by Vega Zhou on 2016/10/26.
 */
@Service
public class MakeupService {

    @Autowired
    ContractService contractService;
    @Autowired
    OrgUserService orgUserService;
    @Autowired
    CustomerService customerService;
    @Autowired
    SalesmanService salesmanService;
    @Autowired
    OrderService orderService;
    @Autowired
    ChargeSchemeService chargeSchemeService;
    @Autowired
    OrderRepository orderRepository;
    @Autowired
    WebExSiteService webExSiteService;
    @Autowired
    WebExSiteRepository webExSiteRepository;


    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public void makeupSite(String customerId, String siteName, Location location) throws WafException {
        if (!WebExSiteNameValidator.isValidSiteName(siteName)) {
            return;
        }
        WebExSite site = webExSiteRepository.findOneBySiteName(siteName.toLowerCase().trim());
        if (site != null) {
            throw new SiteAlreadyExistedException();
        }

        WebExSite newSite = new WebExSite();
        newSite.setState(WebExSiteState.IN_EFFECT.toString());
        newSite.setCustomerId(customerId);

        newSite.setSiteName(siteName.toLowerCase().trim());
        newSite.setPrimaryLanguage(Language.SIMPLIFIED_CHINESE.toString());
        newSite.setAdditionalLanguage(Language.ENGLISH.toString());
        newSite.setTimeZone(TimeZone.BEIJING.toString());
        newSite.setLocation(location.toString());
        newSite.setCountryCode("CN");
        webExSiteRepository.save(newSite);
    }







}
