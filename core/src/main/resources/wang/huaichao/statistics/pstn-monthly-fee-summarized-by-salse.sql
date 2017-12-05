SELECT
  name,
  email,
  sum(UNCOVERD_MINUTES) mins,
  sum(cost) cost
FROM
  (
    SELECT
      s.name,
      s.email,
      pc.UNCOVERD_MINUTES,
      pc.cost
    FROM
      pstn_charges pc,
      b_order o,
      b_contract contr,
      b_sales s
    WHERE
      pc.billing_period = :billingPeriod
      AND pc.cost > 0
      AND pc.order_id = o.pid
      AND o.contract_id = contr.pid
      AND contr.SALESMAN = s.id
  ) a
GROUP BY name, email
ORDER BY cost DESC