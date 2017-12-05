SELECT o.pid
FROM
  b_order o,
  b_charge_scheme c
WHERE
  o.charge_id = c.ID
  AND O.EFFECTIVEENDDATE > :startDate
  AND O.EFFECTIVESTARTDATE < :endDate
  AND (o.state = 'IN_EFFECT' OR o.state = 'END_OF_LIFE')
  AND c.type IN (
    'MONTHLY_PAY_BY_HOSTS',
    'EC_PAY_PER_USE',
    'MONTHLY_PAY_BY_PORTS',
    'EC_PREPAID',
    'MONTHLY_PAY_BY_ACTIVEHOSTS'
  )
  AND o.CUSTOMER_ID = :customerId