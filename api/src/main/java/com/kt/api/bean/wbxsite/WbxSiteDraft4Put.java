package com.kt.api.bean.wbxsite;

import com.kt.validation.annotation.InOneOf;
import org.hibernate.validator.constraints.Range;

import java.util.List;

/**
 * Created by Vega Zhou on 2016/3/29.
 */
public class WbxSiteDraft4Put {

    private String siteName;

    private String primaryLanguage;

    private List<String> additionalLanguages;

    @Range(min = 0, max = 156, message = "wbxsitedraft.timeZone.Range")
    private Integer timeZone;

    @InOneOf(candidates = {"KETIAN_CT", "KETIAN_CT_T30", "KETIAN_CU", "KETIAN_CU_T30", "KETIAN_FREE", "KETIAN_FREE_T30", "KETIAN_GLOBAL", "KETIAN_GLOBAL_T30",
    		"TSP_BIZ2101_RP","TSP_BIZ2101_BA","TSP_BIZ2101_ACER","TSP_BIZ2101_JNJCHINA","TSP_BIZ2101_HISUNPFIZER","TSP_BIZ1001_RP","TSP_BIZ1001_BA","TSP_ARK_1","TSP_PGI_APAC_GM3","TSP_INTERCALL"},
    message = "wbxsitedraft.location.InOneOf")
    private String location;

    public String getSiteName() {
        return siteName;
    }

    public void setSiteName(String siteName) {
        this.siteName = siteName;
    }

    public String getPrimaryLanguage() {
        return primaryLanguage;
    }

    public void setPrimaryLanguage(String primaryLanguage) {
        this.primaryLanguage = primaryLanguage;
    }

    public List<String> getAdditionalLanguages() {
        return additionalLanguages;
    }

    public void setAdditionalLanguages(List<String> additionalLanguages) {
        this.additionalLanguages = additionalLanguages;
    }

    public Integer getTimeZone() {
        return timeZone;
    }

    public void setTimeZone(Integer timeZone) {
        this.timeZone = timeZone;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }
}
