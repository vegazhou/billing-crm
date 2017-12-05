package com.kt.biz.model.biz;

import com.kt.biz.model.IScheme;
import com.kt.biz.model.biz.impl.*;
import com.kt.biz.types.BizType;

/**
 * Created by Vega Zhou on 2016/3/10.
 */
public class BizSchemeManager {

    public static AbstractBizScheme newInstance(BizType bizType) {
        switch (bizType) {
            case WEBEX_MC:
                return new BizWebExMC();
            case WEBEX_EC:
                return new BizWebExEC();
            case WEBEX_TC:
                return new BizWebExTC();
            case WEBEX_SC:
                return new BizWebExSC();
            case WEBEX_EE:
                return new BizWebExEE();
            case WEBEX_STORAGE:
                return new BizWebExStorage();
            case WEBEX_PSTN:
                return new BizWebExPSTN();
            case WEBEX_CMR:
                return new BizWebExCmr();
            case MISC:
                return new BizMisc();
            case CC:
                return new BizCallCenter();
        }
        return null;
    }
}
