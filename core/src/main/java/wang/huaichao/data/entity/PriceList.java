package wang.huaichao.data.entity;

import wang.huaichao.data.service.PstnMetaData;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Administrator on 8/17/2016.
 */
public class PriceList {
    private String pstnRatesId;

    private BigDecimal callInLocalFee;
    private BigDecimal callOutLocalFee;

    // access number -> fee rate
    private Map<String, BigDecimal> callInFeeRateMap = new HashMap<>();

    // zone code -> fee rate
    private Map<Integer, BigDecimal> callOutFeeRateMap = new HashMap<>();

    // mappings
    public static final Map<String, String> callIn400Map = new HashMap<>();

    static {
        // =================================================================
        // 400 call in mapping
        // access number - > 400 number
        // =================================================================
        _buildCallIn400Map();
    }

    private static void _buildCallIn400Map() {
        callIn400Map.put("+861064689121", "4006140081");
        callIn400Map.put("+861084518118", "4006140081");
        callIn400Map.put("+861058062000", "4006140081");
        callIn400Map.put("+861058062088", "4008191212");
        callIn400Map.put("+861058062036", "4008191212");
        callIn400Map.put("+861064386066", "4008191212");
    }


    // call in local
    public void setCallInLocalFee(BigDecimal callInLocalFee) {
        this.callInLocalFee = callInLocalFee;
    }

    // call out local
    public void setCallOutLocalFee(BigDecimal callOutLocalFee) {
        this.callOutLocalFee = callOutLocalFee;
    }

    public void addCallInFeeRate(String accessNumber, BigDecimal rate) {
        callInFeeRateMap.put(accessNumber, rate);
    }

    public void addCallOutFeeRate(Integer zoneCode, BigDecimal rate) {
        callOutFeeRateMap.put(zoneCode, rate);
    }

    public BigDecimal getRate(boolean isInternationalCall,
                              boolean isCallIn,
                              String accessNumber,
                              String countryCode) {

        if (isCallIn) {
            BigDecimal bigDecimal = callInFeeRateMap.get(accessNumber);
            if (bigDecimal != null) {
                return bigDecimal;
            } else {
                return callInLocalFee;
            }
        } else {
            if (isInternationalCall) {
                Integer zone = PstnMetaData.CountryAreaCode2ZoneMap.get(countryCode);
                return callOutFeeRateMap.get(zone);
            } else {
                return callOutLocalFee;
            }
        }
    }

    public String getPstnRatesId() {
        return pstnRatesId;
    }

    public void setPstnRatesId(String pstnRatesId) {
        this.pstnRatesId = pstnRatesId;
    }
}
