SELECT
  DISTINCT customer_id
FROM
  b_order
WHERE
  EFFECTIVEENDDATE > :startDate
  AND EFFECTIVESTARTDATE < :endDate
  AND state IN ('END_OF_LIFE', 'IN_EFFECT')
  AND product_id NOT IN (
    SELECT B_Product.PID
    FROM B_Product
    WHERE B_Product.IS_TRIAL = '1'
  )