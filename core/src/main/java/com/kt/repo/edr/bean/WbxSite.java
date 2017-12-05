package com.kt.repo.edr.bean;

/**
 * Created by jianf on 2016/7/5.
 */
public class WbxSite {
    public String getSiteName() {
        return siteName;
    }

    public void setSiteName(String siteName) {
        this.siteName = siteName;
    }

    public int getSiteId() {
        return siteId;
    }

    public void setSiteId(int siteId) {
        this.siteId = siteId;
    }

    private String siteName;
    private int siteId;

}
