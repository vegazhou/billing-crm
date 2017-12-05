package com.kt.api.bean.biz;

import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.Size;

/**
 * Created by Vega Zhou on 2016/3/11.
 */
public class BizScheme4Put {
    @Size(min = 0, max = 100, message = "biz.scheme.displayName.Size")
    @NotBlank(message = "biz.scheme.displayName.NotBlank")
    private String displayName;

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }
}
