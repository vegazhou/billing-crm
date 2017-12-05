package com.kt.biz.types;

import com.kt.biz.billing.FeeCalculator;
import com.kt.biz.billing.component.*;
import com.kt.biz.model.charge.AbstractChargeScheme;
import com.kt.biz.model.charge.impl.*;
import com.kt.biz.model.order.CollisionDetector;
import com.kt.biz.model.order.compatcheck.*;

/**
 * Created by Vega Zhou on 2016/3/7.
 */
public enum ChargeType implements SchemeType {
    CMR_MONTHLY_PAY(WebExCmrMonthlyPay.class, WebExCmrMonthlyPayFeeCalc.class, CollisionDetectorWebExCmr.class, FeeType.WEBEX_FIRST_INSTALLMENT, FeeType.WEBEX_CONFERENCE_FEE),
    MONTHLY_PAY_BY_ACTIVEHOSTS(WebExConfMonthlyPayByAH.class, WebExConfMonthlyPayByAHFeeCalc.class, CollisionDetectorWebExConfByAH.class, FeeType.WEBEX_FIRST_INSTALLMENT, FeeType.WEBEX_CONFERENCE_FEE),
    MONTHLY_PAY_BY_HOSTS(WebExConfMonthlyPayByHosts.class, WebExConfMonthlyPayByHostsFeeCalc.class, CollisionDetectorWebExConfByHosts.class, FeeType.WEBEX_FIRST_INSTALLMENT, FeeType.WEBEX_CONFERENCE_FEE),
    MONTHLY_PAY_BY_PORTS(WebExConfMonthlyPayByPorts.class, WebExConfMonthlyPayByPortsFeeCalc.class, CollisionDetectorWebExConfByPorts.class, FeeType.WEBEX_FIRST_INSTALLMENT, FeeType.WEBEX_CONFERENCE_FEE),
    MONTHLY_PAY_BY_TOTAL_ATTENDEES(WebExConfMonthlyPayByTotalAttendees.class, WebExConfMonthlyPayByTotalAttendeesFeeCalc.class, CollisionDetectorWebExConfByTotalAttendees.class, FeeType.WEBEX_FIRST_INSTALLMENT, FeeType.WEBEX_CONFERENCE_FEE),


    EC_PAY_PER_USE(WebExECPayPerUse.class, WebExECPayPerUseFeeCalc.class, CollisionDetectorWebExECPPU.class, FeeType.WEBEX_FIRST_INSTALLMENT, FeeType.WEBEX_CONFERENCE_FEE),
    EC_PREPAID(WebExECPrepaid.class, WebExECPrepaidFeeCalc.class, CollisionDetectorWebExECPrepaid.class, FeeType.WEBEX_FIRST_INSTALLMENT, FeeType.WEBEX_CONFERENCE_FEE),
    MONTHLY_PAY_BY_STORAGE(WebExStorageMonthlyPay.class, WebExStorageMonthlyPayFeeCalc.class, CollisionDetectorWebExStorage.class, FeeType.WEBEX_FIRST_INSTALLMENT, FeeType.WEBEX_CONFERENCE_FEE),
    MONTHLY_PAY_BY_STORAGE_O(WebExStorageMonthlyOverflowPay.class, WebExStorageMonthlyOverflowPayFeeCalc.class, CollisionDetectorWebExStorageOverflow.class, FeeType.WEBEX_FIRST_INSTALLMENT, FeeType.WEBEX_CONFERENCE_FEE),
    PSTN_STANDARD_CHARGE(PSTNStandardCharge.class, PSTNStandardChargeFeeCalc.class, CollisionDetectorPSTNStandardCharge.class, FeeType.WEBEX_FIRST_INSTALLMENT, FeeType.WEBEX_CONFERENCE_FEE),
    PSTN_PACKAGE_CHARGE(PSTNStandardCharge.class, PSTNStandardChargeFeeCalc.class, CollisionDetectorPSTNStandardCharge.class, FeeType.WEBEX_FIRST_INSTALLMENT, FeeType.WEBEX_CONFERENCE_FEE),

    PSTN_MONTHLY_PACKET(PSTNMonthlyPacketCharge.class, PSTNMonthlyPacketChargeFeeCalc.class, CollisionDetectorPSTNMonthlyPacket.class, FeeType.WEBEX_FIRST_INSTALLMENT, FeeType.WEBEX_CONFERENCE_FEE),
    PSTN_SINGLE_PACKET_FOR_MULTIPLE_SITES(PSTNSinglePacket4MultiSites.class, PSTNSinglePacket4MultiSitesFeeCalc.class, CollisionDetectorPSTNSinglePacket4MultiSites.class, FeeType.WEBEX_FIRST_INSTALLMENT, FeeType.WEBEX_CONFERENCE_FEE),
    PSTN_SINGLE_PACKET_FOR_ALL_SITES(PSTNSinglePacket4AllSites.class, PSTNSinglePacket4AllSitesFeeCalc.class, CollisionDetectorPSTNSinglePacket4AllSites.class, FeeType.WEBEX_FIRST_INSTALLMENT, FeeType.WEBEX_CONFERENCE_FEE),

    MONTHLY_PAY_PERSONAL_WEBEX(WebExConfMonthlyPayPersonal.class, WebExConfMonthlyPayPersonalFeeCalc.class, CollisionDetectorWebExConfPersonal.class, FeeType.WEBEX_FIRST_INSTALLMENT, FeeType.WEBEX_CONFERENCE_FEE),
    PSTN_PERSONAL_CHARGE(PSTNPersonalCharge.class, PSTNPersonalChargeFeeCalc.class, CollisionDetectorPSTNPersonalCharge.class, FeeType.WEBEX_FIRST_INSTALLMENT, FeeType.WEBEX_CONFERENCE_FEE),
    PSTN_PERSONAL_PACKET(PSTNPersonalPacket.class, PSTNPersonalPacketFeeCalc.class, CollisionDetectorPSTNPersonalPacket.class, FeeType.WEBEX_FIRST_INSTALLMENT, FeeType.WEBEX_CONFERENCE_FEE),

    TELECOM_CHARGE(TelecomCharge.class, TelecomChargeFeeCalc.class, CollisionDetectorTelecom.class, FeeType.WEBEX_FIRST_INSTALLMENT, FeeType.WEBEX_CONFERENCE_FEE),
    MISC_CHARGE(MiscCharge.class, MiscChargeFeeCalc.class, CollisionDetectorMisc.class, FeeType.WEBEX_FIRST_INSTALLMENT, FeeType.WEBEX_CONFERENCE_FEE),

    CC_CALLCENTER_MONTHLY_PAY(CcCallCenterMonthlyPayCharge.class, CcCallCenterMonthlyPayChargeFeeCalc.class, CollisionDetectorCcCallCenter.class, FeeType.CC_FIRST_INSTALLMENT, FeeType.CC_FEE),
    CC_CALLCENTER_NUMBER_MONTHLY_PAY(CcCallCenterNumberMonthlyPayCharge.class, CcCallCenterNumberMonthlyPayChargeFeeCalc.class, CollisionDetectorCcCallCenterNumberMonthlyPay.class, FeeType.CC_FIRST_INSTALLMENT, FeeType.CC_FEE),
    CC_CALLCENTER_PSTN(CcCallCenterPstnCharge.class, CcCallCenterPstnChargeFeeCalc.class, CollisionDetectorCcCallCenterPstn.class, FeeType.CC_FIRST_INSTALLMENT, FeeType.CC_FEE),
    CC_CALLCENTER_PSTN_PACKAGE(CcCallCenterPstnPackageCharge.class, CcCallCenterPstnPackageChargeFeeCalc.class, CollisionDetectorCcCallCenterPstnPackage.class, FeeType.CC_FIRST_INSTALLMENT, FeeType.CC_FEE),
    CC_CALLCENTER_PSTN_MONTHLY_PACKAGE(CcCallCenterPstnMonthlyPackageCharge.class, CcCallCenterPstnMonthlyPackageChargeFeeCalc.class, CollisionDetectorCcCallCenterPstnMonthlyPackage.class, FeeType.CC_FIRST_INSTALLMENT, FeeType.CC_FEE),
    CC_PSTN_MONTHLY_MIN_CHARGE_PACKAGE(CcCallCenterPstnMinMonthlyPackageCharge.class, CcCallCenterPstnMinMonthlyPackageChargeFeeCalc.class, CollisionDetectorCcCallCenterPstnMinMonthlyPackage.class, FeeType.CC_FIRST_INSTALLMENT, FeeType.CC_FEE),
    CC_CALLCENTER_OLS_MONTHLY_PAY(CcCallCenterOlsMonthlyPayCharge.class, CcCallCenterOlsMonthlyPayChargeFeeCalc.class, CollisionDetectorCcCallCenterOls.class, FeeType.CC_FIRST_INSTALLMENT, FeeType.CC_FEE);



    Class<? extends AbstractChargeScheme> chargeSchemeClass;

    Class<? extends FeeCalculator> prepaidFeeCalc;

    Class<? extends CollisionDetector> collisionDetector;

    FeeType firstInstallmentFeeType;

    FeeType periodicFeeType;

    ChargeType(Class<? extends AbstractChargeScheme> chargeSchemeClass,
               Class<? extends FeeCalculator> prepaidFeeCalc,
               Class<? extends CollisionDetector> collisionDetector,
               FeeType firstInstallmentFeeType,
               FeeType periodicFeeType) {
        this.chargeSchemeClass = chargeSchemeClass;
        this.prepaidFeeCalc = prepaidFeeCalc;
        this.collisionDetector = collisionDetector;
        this.firstInstallmentFeeType = firstInstallmentFeeType;
        this.periodicFeeType = periodicFeeType;
    }


    public Class<? extends FeeCalculator> getFeeCalc() {
        return prepaidFeeCalc;
    }

    public Class<? extends CollisionDetector> getCollisionDetector() {
        return collisionDetector;
    }

    public Class<? extends AbstractChargeScheme> getChargeSchemeClass() {
        return chargeSchemeClass;
    }

    public FeeType getFirstInstallmentFeeType() {
        return firstInstallmentFeeType;
    }

    public FeeType getPeriodicFeeType() {
        return periodicFeeType;
    }
}
