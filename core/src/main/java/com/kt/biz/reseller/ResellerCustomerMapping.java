package com.kt.biz.reseller;

import org.apache.commons.lang.StringUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * Created by Vega on 2017/10/31.
 */
public class ResellerCustomerMapping {

    private static final ArrayList<List<String>> RESELLER_CODES = new ArrayList<>();
    static {
        //北京兆维电子（集团）有限责任公司
        RESELLER_CODES.add(Arrays.asList("KT01491", "KT00739"));
        //中国电信股份有限公司上海分公司
        RESELLER_CODES.add(Arrays.asList("KT01379", "KT00723"));
        //北京阳光金网科技发展有限公司
        RESELLER_CODES.add(Arrays.asList("KT01263", "KG80569HB4"));
        //集丰怡网络科技（北京）有限公司
        RESELLER_CODES.add(Arrays.asList("KT01108", "SM40600HB8"));
        //广州京谷光大信息技术有限公司
        RESELLER_CODES.add(Arrays.asList("KT01107", "SM40194HN1"));
        //尚阳科技股份有限公司
        RESELLER_CODES.add(Arrays.asList("KT01106", "SM40156HN8"));
        //上海会畅通讯股份有限公司
        RESELLER_CODES.add(Arrays.asList("KT01105", "KT00366"));
        //北京神州云科信息服务有限公司
        RESELLER_CODES.add(Arrays.asList("KT01104", "SM40574HB9"));
        //北京兆维博安科技有限公司
        RESELLER_CODES.add(Arrays.asList("KT01103", "SM40078HB2"));
        //广州华工中云信息技术有限公司
        RESELLER_CODES.add(Arrays.asList("KT01102", "SM40125HN4"));
        //上海华万通信科技有限公司
        RESELLER_CODES.add(Arrays.asList("SM40089HD4", "KT01844"));
        //深圳市华运通科技股份有限公司
        RESELLER_CODES.add(Arrays.asList("SM40524HN3", "KT02200"));
        //上海云学科技有限公司
        RESELLER_CODES.add(Arrays.asList("SM40115HD3", "KT03885"));
        //北京晓通网络科技有限公司
        RESELLER_CODES.add(Arrays.asList("KT04515"));
        //韦史德（上海）通信技术有限公司
        RESELLER_CODES.add(Arrays.asList("KT04373", "KT04455"));
    }

    public static List<String> mapResellerCodes(String input) {
        for (List<String> codes : RESELLER_CODES) {
            for (String code : codes) {
                if (StringUtils.equalsIgnoreCase(code, input)) {
                    return codes;
                }
            }
        }
        return Collections.singletonList(input);
    }
}
