package com.kt.service.billing;

import com.kt.biz.bean.PersonalWebExBill;
import com.kt.repo.mysql.billing.BillFormalDetailRepository;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by Vega Zhou on 2017/5/15.
 */
@Service
public class PersonalWebExBillService {

    @Autowired
    BillFormalDetailRepository billFormalDetailRepository;


    public List<PersonalWebExBill> listPersonWebExBill(String email) {
        if (StringUtils.isBlank(email)) {
            throw new IllegalArgumentException();
        }
        return billFormalDetailRepository.listPersonWebExBill(email.trim().toLowerCase());
    }
}
