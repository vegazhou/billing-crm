SELECT
  tb.pid                order_id,
  tb.effectivestartdate start_date,
  tb.effectiveenddate   end_date,
  csa.attribute_name    attr_name,
  csa.attribute_value   attr_value,
  b.display_name        prod_name
FROM
  b_charge_scheme cs,
  b_charge_scheme_attributes csa,
  b_product b,
  (
    SELECT
      TA.*,
      C.agent_type
    FROM
      (
        SELECT
          O.*,
          C.AGENT_ID
        FROM
          B_ORDER O,
          B_CONTRACT C
        WHERE
          O.CONTRACT_ID = C.PID
          AND O.STATE IN ('IN_EFFECT', 'END_OF_LIFE')
          AND O.EFFECTIVEENDDATE > :start_time
          AND O.EFFECTIVESTARTDATE < :end_time
          AND O.CUSTOMER_ID = :customer_id
        ORDER BY O.CUSTOMER_ID
      ) TA
      LEFT JOIN B_CUSTOMER C ON C.PID = TA.AGENT_ID
  ) tb
WHERE
  tb.charge_id = cs.id
  AND cs.id = csa.scheme_id
  AND b.pid = tb.product_id
  AND cs.type IN (
    'MONTHLY_PAY_BY_HOSTS',
    'MONTHLY_PAY_BY_PORTS'
  )
  AND csa.attribute_name IN (
    'HOSTS_AMOUNT',
    'COMMON_UNIT_PRICE',
    'PORTS_AMOUNT',
    'COMMON_SITE'
  )
  AND COALESCE(TB.AGENT_TYPE, 'AGENT') = 'AGENT'