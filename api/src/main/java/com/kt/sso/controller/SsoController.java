package com.kt.sso.controller;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * Created by Vega Zhou on 2015/10/10.
 */
@Controller
@RequestMapping("/sso")
public class SsoController {


    @RequestMapping("")
    public String index(@RequestParam(value = "mu", required = false) String backUrl) {
        if (StringUtils.isBlank(backUrl)) {
            return "redirect:/";
        } else {
            return "redirect:" + backUrl;
        }
    }
}
