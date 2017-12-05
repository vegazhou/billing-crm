package com.kt.api.bean.wbxsite;

import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.Size;

/**
 * Created by Vega Zhou on 2016/3/19.
 */
public class WbxSite4Create {
    @Size(min = 0, max = 50, message = "wbxsite.siteName.Size")
    @NotBlank(message = "wbxsite.siteName.NotBlank")
    private String siteName;

    @NotBlank(message = "wbxsite.customerId.NotBlank")
    private String customerId;

    public String getSiteName() {
        return siteName;
    }

    public void setSiteName(String siteName) {
        this.siteName = siteName;
    }

    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }
}
