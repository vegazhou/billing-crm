package com.kt.api.bean.biz;

import com.kt.validation.annotation.InOneOf;
import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.Size;

/**
 * Created by Vega Zhou on 2016/3/11.
 */
public class BizScheme4Create {

    @Size(min = 0, max = 100, message = "biz.scheme.displayName.Size")
    @NotBlank(message = "biz.scheme.displayName.NotBlank")
    private String displayName;

    @InOneOf(candidates = {"WEBEX_MC", "WEBEX_EC", "WEBEX_TC", "WEBEX_SC", "WEBEX_EE", "WEBEX_STORAGE", "WEBEX_PSTN"},
            message = "biz.scheme.bizType.Invalid")
    private String bizType;


    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public String getBizType() {
        return bizType;
    }

    public void setBizType(String bizType) {
        this.bizType = bizType;
    }
}
