SELECT
  r.pid                   AS price_list_id,
  m.REAL_NUMBER           AS access_number,
  to_number(r.code)       AS code,
  r.rate + r.service_rate AS rate
FROM b_rate r
  LEFT JOIN (
              SELECT
                t4e.REAL_NUMBER,
                decode(tcn.code, NULL, '1', tcn.code) code
              FROM TELEPHONE_400_EXCHANGE_T t4e
                LEFT JOIN TELEPHONE_CALLIN_NUMBER_T tcn
                  ON t4e.ACCESS_NUMBER = tcn.ACCESS_NUMBER
            ) m
    ON r.code = m.code
WHERE r.pid IN (:pstnRatesIds)


