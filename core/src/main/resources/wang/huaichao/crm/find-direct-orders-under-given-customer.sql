SELECT
  TB.CUSTOMER_ID,
  TB.PID,
  TB.DISPLAY_NAME
FROM
  (
    SELECT
      TA.*,
      C.AGENT_TYPE
    FROM
      (
        SELECT
          O.CUSTOMER_ID,
          O.PID,
          CU.DISPLAY_NAME,
          C.AGENT_ID
        FROM
          B_ORDER O,
          B_CONTRACT C,
          B_CUSTOMER CU
        WHERE
          O.CONTRACT_ID = C.PID
          AND O.CUSTOMER_ID = CU.PID
          AND O.STATE IN ('IN_EFFECT', 'END_OF_LIFE')
          AND O.EFFECTIVEENDDATE > :startDate
          AND O.EFFECTIVESTARTDATE < :endDate
          AND O.CUSTOMER_ID = :customerId
        ORDER BY O.CUSTOMER_ID
      ) TA
      LEFT JOIN B_CUSTOMER C
        ON C.PID = TA.AGENT_ID
  ) TB
WHERE COALESCE(TB.AGENT_TYPE, 'AGENT') = 'AGENT'