package com.kt.biz.types;



/**
 * Created by Vega Zhou on 2016/3/25.
 */
public enum TimeZone {
    BEIJING(45, "北京（中国时间，GMT+08:00）", 8),
    MARSHALL_ISLANDS(0, "马歇尔群岛（国际日期变更线时间，GMT-12:00）", -12),
    SAMOA(1, "萨摩亚（萨摩亚时间，GMT-11:00）", -11),
    HONOLULU(2, "火奴鲁鲁（夏威夷时间，GMT-10:00）", -10),
    ANCHORAGE(3, "安克雷奇（阿拉斯加夏令时，GMT-08:00）", -8),
    SAN_FRANCISCO(4, "旧金山（太平洋夏令时，GMT-07:00）", -7),
    ARIZONA(5, "亚利桑那（山地时间，GMT-07:00）", -7),
    TIJUANA(131, "提华纳（太平洋墨西哥沿岸夏令时，GMT-07:00）", -7),
    DENVER(6, "丹佛（山地夏令时，GMT-06:00）", -6),
    SASKATCHEWAN(9, "萨斯喀彻温（中部时间，GMT-06:00）", -6),
    CHIHUAHUA(132, "奇瓦瓦（墨西哥山地夏令时，GMT-06:00）", -6),
    TEGUCIGALPA(137, "特古西加尔巴（洪都拉斯时间，GMT-06:00）", -6),
    CHICAGO(7, "芝加哥（中部夏令时，GMT-05:00）", -5),
    MEXICO(8, "墨西哥（墨西哥夏令时，GMT-05:00）", -5),
    BOGOTA(10, "波哥大（南美太平洋时间，GMT-05:00）", -5),
    PANAMA(146, "巴拿马（东部时间，GMT-05:00）", -5),
    CARACAS(133, "加拉加斯（南美西部时间，GMT-04:30）", -4.5f),
    NEW_YORK(11, "纽约（东部夏令时，GMT-04:00）", -4),
    INDIANA(12, "印地安那（东部夏令时，GMT-04:00）", -4),
    LA_PAZ(14, "拉巴斯（南美西部时间，GMT-04:00）", -4),
    ASUNCION(148, "亚松森（巴拉圭时间，GMT-04:00）", -4),
    HALIFAX(13, "哈利法克斯（大西洋夏令时，GMT-03:00）", -3),
    BRASILIA(16, "巴西利亚（南美东部标准时间，GMT-03:00）", -3),
    BUENOS_AIRES(17, "布宜诺斯艾利斯（南美东部时间，GMT-03:00）", -3),
    RECIFE(135, "累西腓（南美东部时间，GMT-03:00）", -3),
    SANTIAGO(145, "圣地亚哥（南美西部时间，GMT-03:00）", -3),
    MONTEVIDEO(149, "蒙得维的亚（乌拉圭时间，GMT-03:00）", -3),
    NEWFOUNDLAND(15, "纽芬兰（纽芬兰夏令时，GMT-02:30）", -2.5f),
    MID_ATLANTIC(18, "中大西洋（中大西洋时间，GMT-02:00）", -2),
    NUUK(138, "努克（格林兰夏令时，GMT-02:00）", -2),
    CAPE_VERDE(150, "佛得角（佛得角群岛时间，GMT-01:00）", -1),
    AZORES(19, "亚述尔群岛（亚速尔群岛夏令时，GMT）", 0),
    REYKJAVIK(20, "雷克雅维克（格林威治时间，GMT）", 0),
    LONDON(21, "伦敦（GMT夏令时，GMT+01:00）", 1),
    CASABLANCA(136, "卡萨布兰卡（摩洛哥夏令时，GMT+01:00）", 1),
    WEST_AFRICA(143, "西非（西非时间，GMT+01:00）", 1),
    WINDHOEK(151, "温得和克（西非时间，GMT+01:00）", 1),
    AMSTERDAM(22, "阿姆斯特丹（欧洲夏令时，GMT+02:00）", 2),
    PARIS(23, "巴黎（欧洲夏令时，GMT+02:00）", 2),
    BERLIN(25, "柏林（欧洲夏令时，GMT+02:00）", 2),
    CAIRO(28, "开罗（埃及时间，GMT+02:00）", 2),
    PRETORIA(29, "比勒陀利亚（南非时间，GMT+02:00）", 2),
    STOCKHOLM(130, "斯德哥尔摩（瑞典夏令时，GMT+02:00）", 2),
    ROME(142, "罗马（欧洲夏令时，GMT+02:00）", 2),
    MADRID(144, "马德里（欧洲夏令时，GMT+02:00）", 2),
    BRUSSELS(147, "布鲁塞尔（欧洲夏令时，GMT+02:00）", 2),
    ATHENS(26, "雅典（希腊夏令时，GMT+03:00）", 3),
    HELSINKI(30, "赫尔辛基（北欧夏令时，GMT+03:00）", 3),
    TEL_AVIV(31, "特拉维夫（以色列夏令时，GMT+03:00）", 3),
    RIYADH(32, "利雅得（沙特阿拉伯时间，GMT+03:00）", 3),
    MOSCOW(33, "莫斯科（俄罗斯时间，GMT+03:00）", 3),
    NAIROBI(34, "内罗毕（内罗毕时间，GMT+03:00）", 3),
    OMAN(139, "阿曼（约旦夏令时，GMT+03:00）", 3),
    ISTANBUL(140, "伊斯坦布尔（东欧夏令时，GMT+03:00）", 3),
    ABU_DHABI(36, "阿布扎比（阿拉伯时间，GMT+04:00）", 4),
    YEREVAN(155, "耶烈万（亚美尼亚时间，GMT+04:00）", 4),
    TEHERAN(35, "德黑兰（伊朗夏令时，GMT+04:30）", 4.5f),
    KABUL(38, "喀布尔（阿富汗时间，GMT+04:30）", 4.5f),
    BAKU(37, "巴库（巴库夏令时，GMT+05:00）", 5),
    ISLAMABAD(40, "伊斯兰堡（西亚时间，GMT+05:00）", 5),
    BOMBAY(41, "孟买（印度时间，GMT+05:30）", 5.5f),
    COLOMBO(42, "科伦坡（哥伦比亚时间，GMT+05:30）", 5.5f),
    KATMANDU(141, "加德满都（尼泊尔时间，GMT+05:45）", 5.75f),
    EKATERINBURG(39, "叶卡捷琳堡（西亚时间，GMT+06:00）", 6),
    ALMA_ATA(43, "阿拉木图（中亚时间，GMT+06:00）", 6),
    NOVOSIBIRSK(156, "新西伯利亚（新西伯利亚时间，GMT+06:00）", 6),
    RANGOON(152, "仰光（缅甸时间，GMT+06:30）", 6.5f),
    BANGKOK(44, "曼谷（曼谷时间，GMT+07:00）", 7),
    JAKARTA(154, "雅加达（西印度尼西亚时间，GMT+07:00）", 7),
    PERTH(46, "珀斯（澳大利亚西部标准时间，GMT+08:00）", 8),
    SINGAPORE(47, "新加坡（新加坡时间，GMT+08:00）", 8),
    TAIPEI(48, "台北（台北时间，GMT+08:00）", 8),
    KUALA_LUMPUR(135, "吉隆坡（马来西亚时间，GMT+08:00）", 8),
    TOKYO(49, "东京（日本时间，GMT+09:00）", 9),
    SEOUL(50, "首尔（韩国时间，GMT+09:00）", 9),
    ADELGUIDE(52, "阿得莱德（澳大利亚中部标准时间，GMT+09:30）", 9.5f),
    DARWIN(53, "达尔文（澳大利亚中部时间，GMT+09:30）", 9.5f),
    YAKUTSK(51, "雅库茨克（雅库茨克时间，GMT+10:00）", 10),
    BRISBANE(54, "布里斯班（澳大利亚东部时间，GMT+10:00）", 10),
    SYDNEY(55, "悉尼（澳大利亚东部标准时间，GMT+10:00）", 10),
    GUAM(56, "关岛（西太平洋时间，GMT+10:00）", 10),
    HOBART(57, "霍巴特（塔斯马尼亚标准时间，GMT+10:00）", 10),
    VLADIVOSTOK(58, "符拉迪沃斯托克（符拉迪沃斯托克时间，GMT+11:00）", 11),
    SOLOMON_ISLANDS(59, "所罗门群岛（中太平洋时间，GMT+11:00）", 11),
    WELLINGTON(60, "惠灵顿（新西兰标准时间，GMT+12:00）", 12),
    FIJI(61, "斐济（斐济标准时间，GMT+12:00）", 12),
    TONGA(153, "汤加（汤加时间，GMT+13:00）", 13);


    private String displayName;

    private int value;

    private float offset;

    TimeZone(int value, String displayName, float offset) {
        this.displayName = displayName;
        this.value = value;
        this.offset = offset;
    }

    public String getDisplayName() {
        return displayName;
    }

    public int getValue() {
        return value;
    }

    public static TimeZone valueOf(int index) {
        for(TimeZone tz : TimeZone.values()) {
            if (tz.value == index) {
                return tz;
            }
        }
        return null;
    }

    public float getOffset() {
        return offset;
    }



}
