package com.kt.biz.types;

/**
 * Created by Vega Zhou on 2016/3/25.
 */
public enum Location {

    KETIAN_CT("ketian-CT"),
    KETIAN_CU("ketian-CU"),
    KETIAN_FREE("ketian-free"),
    KETIAN_GLOBAL("ketian-global"),
    KETIAN_CT_T30("ketian-CT-T30"),
    KETIAN_CU_T30("ketian-CU-T30"),
    KETIAN_FREE_T30("ketian-free-T30"),
    KETIAN_GLOBAL_T30("ketian-global-T30"),

    TSP_BIZ2101_RP("tsp-biz2101-rp"),
    TSP_BIZ2101_BA("tsp-biz2101-ba"),
    TSP_BIZ2101_ACER("tsp-biz2101-acer"),
    TSP_BIZ2101_JNJCHINA("tsp-biz2101-jnjchina"),
    TSP_BIZ2101_HISUNPFIZER("tsp-biz2101-hisunpfizer"),
    TSP_BIZ1001_RP("tsp-biz1001-rp"),
    TSP_BIZ1001_BA("tsp-biz1001-ba"),
    TSP_ARK_1("tsp-ark-1"),
    TSP_PGI_APAC_GM3("tsp-pgi-apac-gm3"),
    TSP_INTERCALL("tsp-intercall");

    private String value;

    Location(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

}
