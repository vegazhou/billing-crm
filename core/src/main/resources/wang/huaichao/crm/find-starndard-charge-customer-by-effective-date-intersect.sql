SELECT
  DISTINCT O.CUSTOMER_ID AS customerId
FROM
  B_ORDER O,
  B_CHARGE_SCHEME CS
WHERE
  O.EFFECTIVEENDDATE > :startDate
  AND O.EFFECTIVESTARTDATE < :endDate
  AND O.CHARGE_ID = CS.ID
  AND O.STATE IN ('IN_EFFECT', 'END_OF_LIFE')
  AND CS.TYPE = 'PSTN_STANDARD_CHARGE'