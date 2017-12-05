package com.kt.biz.types;

/**
 * Created by Vega Zhou on 2016/3/25.
 */
public enum Language {
    SIMPLIFIED_CHINESE("zh-cn", 1),
    TRADITIONAL_CHINESE("zh-tw", 1 << 1),
    ENGLISH("en-us", 1 << 2),
    JAPANESE("jp", 1 << 3),
    KOREAN("ko", 1 << 4),
    FRENCH("fr", 1 << 5),
    GERMAN("de", 1 << 6),
    ITALIAN("it", 1 << 7),
    SPANISH("es-me", 1 << 8),
    SPANISH_CASTILLA("es-sp", 1 << 9),
    SWEDISH("sw", 1 << 10),
    HOLLAND("nl", 1 << 11),
    PORTUGUESE("pt-br", 1 << 12),
    RUSSIAN("ru", 1 << 13),
    TURKEY("tr-tr", 1 << 14),
    DANISH("da-dk", 1 << 15);


    private String value;

    private int bit;

    Language(String value, int bit) {
        this.value = value;
        this.bit = bit;
    }

    public String getValue() {
        return value;
    }

    public int getBit() {
        return bit;
    }
}
