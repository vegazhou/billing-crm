package wang.huaichao.data.ds;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Administrator on 8/18/2016.
 */
public class Customer {
    private String customerId;
    private Map<String, Site> siteMap = new HashMap<>();


    public Site getSite(String siteName) {
        return siteMap.get(siteName);
    }

    public void addSite(Site site) {
        siteMap.put(site.getSiteName(), site);
    }

    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    public Map<String, Site> getSiteMap() {
        return siteMap;
    }

    public void setSiteMap(Map<String, Site> siteMap) {
        this.siteMap = siteMap;
    }
}
