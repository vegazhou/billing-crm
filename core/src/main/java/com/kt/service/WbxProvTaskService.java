package com.kt.service;

import com.kt.biz.types.WbxProvTaskStatus;
import com.kt.biz.types.WbxProvTaskType;
import com.kt.entity.mysql.batch.WbxProvTask;
import com.kt.entity.mysql.crm.ChargeSchemeAttribute;
import com.kt.entity.mysql.crm.WebExSite;
import com.kt.exception.WbxProvTaskNotFoundException;
import com.kt.repo.mysql.batch.ChargeSchemeAttributeRepository;
import com.kt.repo.mysql.batch.WbxProvTaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

/**
 * Created by Administrator on 2016/4/15.
 */
@Service
public class WbxProvTaskService {

    @Autowired
    private WbxProvTaskRepository wbxProvTaskRepository;

    @Autowired
    private OrderService orderService;

    @Autowired
    private WebExSiteService webExSiteService;

    @Autowired
    ChargeSchemeAttributeRepository chargeSchemeAttributeRepository;

    public WbxProvTask updateCallBackResult(String taskId, String siteId, String callBackStr, WbxProvTaskStatus wbts)
            throws WbxProvTaskNotFoundException {
        WbxProvTask task = wbxProvTaskRepository.findOne(taskId);
        if(task == null){
            throw new WbxProvTaskNotFoundException();
        }
        if(siteId != null) {
            task.setSiteId(siteId);
        }
        if(callBackStr != null) {
            task.setCallBackStr(callBackStr);
            task.setCallBackTime(new Date());
        }
        if(wbts != null) {
            task.setState(wbts.toString());
        }
        task = wbxProvTaskRepository.save(task);
        return task;
    }

    public WbxProvTask saveWbxProvTask(WbxProvTask task){
        return wbxProvTaskRepository.save(task);
    }

    public List<WbxProvTask> findTasksByTypeAndState(String type, String state){
        return wbxProvTaskRepository.findByTypeAndStateOrderByCreateTimeAsc(type, state);
    }

    public List<ChargeSchemeAttribute> findChargeSchemeAttributeById(String schemeId){
        return chargeSchemeAttributeRepository.findByEntityId(schemeId);
    }

    //Update site basic configuration, need call this function to generate one site change task.
    public WbxProvTask generateSiteChangeTask(String siteName){
        WbxProvTask task = new WbxProvTask();
        task.setSiteName(siteName);
        task.setType(WbxProvTaskType.SITE.toString());
        task.setState(WbxProvTaskStatus.SITECHANGE.toString());
        task.setCreateTime(new Date());
        return wbxProvTaskRepository.save(task);
    }

    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public boolean updateSiteAudio(String siteName, boolean callInToll, boolean callInTollFree,
                                   boolean callInGlobal, boolean callBack,
                                   boolean callBackGlobal, boolean voip, boolean audioBroadcast) {
        boolean bResult=false;

        WebExSite site = webExSiteService.findBySiteName(siteName);

        site.setTollCallIn(parseBoolean(callInToll));
        site.setTollFreeCallIn(parseBoolean(callInTollFree));
        site.setGlobalCallIn(parseBoolean(callInGlobal));
        site.setCallBack(parseBoolean(callBack));
        site.setInternationalCallBack(parseBoolean(callBackGlobal));
        site.setVoip(parseBoolean(voip));
        site.setAudioBroadCast(parseBoolean(audioBroadcast));

        WebExSite webExSite = webExSiteService.saveWebExSite(site);
        if(webExSite==null){
            return false;
        }
        WbxProvTask wbxProvTask =generateSiteChangeTask(siteName);
        if(wbxProvTask!=null){
            bResult=true;
        }

        return bResult;
    }

    public WbxProvTask findWbxProvTaskById(String taskId)
            throws WbxProvTaskNotFoundException {
        WbxProvTask task = wbxProvTaskRepository.findOne(taskId);
        if(task == null){
            throw new WbxProvTaskNotFoundException();
        }
        return task;
    }

    private static int parseBoolean(boolean value){
        if(value){
            return 1;
        }else{
            return 0;
        }
    }

}
