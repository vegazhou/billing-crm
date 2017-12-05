package com.kt.repo.mysql.billing;

import com.kt.biz.bean.KeyValueBean;
import com.kt.biz.bean.PortsUsageBean;
import com.kt.entity.mysql.billing.PortsOverflowDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

/**
 * Created by Administrator on 2016/8/31.
 */
public interface PortsOverflowDetailRepositoryCustom{
    List<PortsUsageBean> getSitePortsSetting(Date startDate, Date endDate);
}
