package wang.huaichao.data.service;

import com.kt.entity.mysql.crm.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Service;
import wang.huaichao.Global;
import wang.huaichao.data.ds.ProductMonthlyFee;
import wang.huaichao.data.entity.PriceList;
import wang.huaichao.data.entity.crm.OrderCustomer;
import wang.huaichao.data.entity.crm.PortChargeSchema;
import wang.huaichao.data.entity.crm.PstnPackageOrder;
import wang.huaichao.data.entity.crm.PstnStandardChargeOrder;
import wang.huaichao.utils.DateBuilder;
import wang.huaichao.utils.DateUtils;
import wang.huaichao.utils.FileUtils;

import java.io.IOException;
import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.sql.Types;
import java.text.ParseException;
import java.util.*;

/**
 * Created by Administrator on 8/17/2016.
 */
@Service
public class CrmDataService {
    @Autowired
    @Qualifier("crmJdbcTemplate")
    private NamedParameterJdbcTemplate jdbcTemplate;

    public Map<String, PstnStandardChargeOrder> findAllPstnStandardChargeOrder(
        Date startDate, Date endDate) throws IOException {

        String sql = FileUtils.readFromClassPath("/wang/huaichao/crm/find-orders-with-charge-attributes.sql");

        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("startDate", Global.DB_DATETIME_FMT.format(startDate), Types.VARCHAR);
        params.addValue("endDate", Global.DB_DATETIME_FMT.format(endDate), Types.VARCHAR);

        PstnStandardChargeOrderRowMapper mapper = new PstnStandardChargeOrderRowMapper();
        jdbcTemplate.query(sql, params, mapper);
        final Map<String, PstnStandardChargeOrder> orderMap = mapper.getOrderMap();

        // todo: check both siteName & rateId present

        // get price lists
//        Map<String, PriceList> priceListMap = findPriceRatesByIds(mapper.getRatesIds());
        Map<String, PriceList> priceListMap = buildPriceList(mapper.getRatesIds());

        // fill in price lists
        for (PstnStandardChargeOrder pstnStandardChargeOrder : orderMap.values()) {
            pstnStandardChargeOrder.setPriceList(
                priceListMap.get(pstnStandardChargeOrder.getPstnRatesId())
            );
        }

        return orderMap;
    }

    public List<String> findStandardChargeCustomerByEffectiveDateIntersect(Date startDate, Date endDate) throws IOException {
        String sql = FileUtils.readFromClassPath("/wang/huaichao/crm/find-starndard-charge-customer-by-effective-date-intersect.sql");

        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("startDate", Global.DB_DATETIME_FMT.format(startDate), Types.VARCHAR);
        params.addValue("endDate", Global.DB_DATETIME_FMT.format(endDate), Types.VARCHAR);

        return jdbcTemplate.query(sql, params, new RowMapper<String>() {
            @Override
            public String mapRow(ResultSet resultSet, int i) throws SQLException {
                return resultSet.getString("customerId");
            }
        });
    }

    public List<String> findCustomerWithEffectiveOrder(Date startDate, Date endDate) throws IOException {
        String sql = FileUtils.readFromClassPath("/wang/huaichao/crm/find-direct-or-agented-customer-with-effective-order.sql");

        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("startDate", Global.DB_DATETIME_FMT.format(startDate), Types.VARCHAR);
        params.addValue("endDate", Global.DB_DATETIME_FMT.format(endDate), Types.VARCHAR);

        return jdbcTemplate.query(sql, params, new RowMapper<String>() {
            @Override
            public String mapRow(ResultSet resultSet, int i) throws SQLException {
                return resultSet.getString("customerId");
            }
        });
    }

    public Map<String, PstnStandardChargeOrder> findPstnStandardChargeOrderMapByCustomerAndEffectiveDateIntersect(
        String customerId, Date startDate, Date endDate) throws IOException {
        String sql = FileUtils.readFromClassPath("/wang/huaichao/crm/find-standard-charge-order-by-customer-and-effective-date-intersect.sql");

        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("startDate", Global.DB_DATETIME_FMT.format(startDate), Types.VARCHAR);
        params.addValue("endDate", Global.DB_DATETIME_FMT.format(endDate), Types.VARCHAR);
        params.addValue("customerId", customerId);


        PstnStandardChargeOrderRowMapper mapper = new PstnStandardChargeOrderRowMapper();
        jdbcTemplate.query(sql, params, mapper);

        Map<String, PstnStandardChargeOrder> orderMap = mapper.getOrderMap();

        // get price lists
//        Map<String, PriceList> priceListMap = findPriceRatesByIds(mapper.getRatesIds());
        Map<String, PriceList> priceListMap = buildPriceList(mapper.getRatesIds());

        // fill in price lists
        for (PstnStandardChargeOrder pstnStandardChargeOrder : orderMap.values()) {
            pstnStandardChargeOrder.setPriceList(
                priceListMap.get(pstnStandardChargeOrder.getPstnRatesId())
            );
        }

        return orderMap;
    }


//    public Map<String, PriceList> findPriceRatesByIds(List<String> ids) throws IOException {
//        String sql = "SELECT * FROM B_RATE WHERE PID IN (:rateIds)";
//
//        MapSqlParameterSource params = new MapSqlParameterSource();
//        params.addValue("rateIds", ids);
//
//        final Map<String, PriceList> priceListMap = new HashMap<>();
//
//        jdbcTemplate.query(sql, params, new RowMapper<PriceRate>() {
//            @Override
//            public PriceRate mapRow(ResultSet resultSet, int i) throws SQLException {
//                String pstnRatesId = resultSet.getString("pid");
//
//                PriceList priceList = priceListMap.get(pstnRatesId);
//
//                if (priceList == null) {
//                    priceList = new PriceList();
//                    priceList.setPstnRatesId(pstnRatesId);
//                    priceListMap.put(pstnRatesId, priceList);
//                }
//
//                priceList.add(
//                        resultSet.getString("code"),
//                        resultSet.getBigDecimal("rate"),
//                        resultSet.getBigDecimal("service_rate")
//                );
//
//                return null;
//            }
//        });
//
//        return priceListMap;
//    }


    public Map<String, PstnPackageOrder> findPstnPackagesByOrderIds(List<String> orderIds) throws IOException {
        String sql = FileUtils.readFromClassPath("/wang/huaichao/crm/find-pstn-package-by-order-ids.sql");
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("orderIds", orderIds);

        PstnPackageOrderRowMapper rowMapper = new PstnPackageOrderRowMapper();
        jdbcTemplate.query(sql, params, rowMapper);
        return rowMapper.getPstnPackageMap();
    }

    private static class PstnPackageOrderRowMapper implements RowMapper<PstnPackageOrder> {
        private Map<String, PstnPackageOrder> pstnPackageMap = new HashMap<>();

        public Map<String, PstnPackageOrder> getPstnPackageMap() {
            return pstnPackageMap;
        }

        @Override
        public PstnPackageOrder mapRow(ResultSet resultSet, int i) throws SQLException {
            String orderId = resultSet.getString("orderId");
            PstnPackageOrder pstnPackageOrder = pstnPackageMap.get(orderId);

            if (pstnPackageOrder == null) {
                pstnPackageOrder = new PstnPackageOrder();
                pstnPackageOrder.setOrderId(orderId);
                pstnPackageOrder.setCustomerId(resultSet.getString("customerId"));
                pstnPackageOrder.setChargeSchemaId(resultSet.getString("chargeSchemaId"));

                try {
                    pstnPackageOrder.setStartDate(Global.DB_DATETIME_FMT.parse(resultSet.getString("startDate")));
                    pstnPackageOrder.setEndDate(Global.DB_DATETIME_FMT.parse(resultSet.getString("endDate")));
                } catch (ParseException e) {
                    e.printStackTrace();
                }

                pstnPackageOrder.setPackageType(PstnPackageOrder.PackageType.valueOf(resultSet.getString("packageType")));

                pstnPackageMap.put(orderId, pstnPackageOrder);
            }

            String attrName = resultSet.getString("attrName");
            String attrValue = resultSet.getString("attrValue");

            if ("COMMON_SITES".equalsIgnoreCase(attrName)) {
                pstnPackageOrder.getSites().addAll(Arrays.asList(attrValue.split(";")));
            } else if ("PSTN_PACKAGE_MINUTES".equalsIgnoreCase(attrName)) {
                pstnPackageOrder.setMinitues(Integer.valueOf(attrValue));
            } else if ("MONTH_AMOUNT".equalsIgnoreCase(attrName)) {
                pstnPackageOrder.setMonths(Integer.valueOf(attrValue));
            } else {
                throw new RuntimeException("unexpected attr value: " + attrValue);
            }

            return pstnPackageOrder;
        }
    }


    public Map<String, PstnPackageOrder> findPstnPackagesByCustomerAndBillingPeroid(String customerId, Date start, Date end) throws IOException {
        String sql = FileUtils.readFromClassPath("/wang/huaichao/crm/find-pstn-package.sql");
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("customerId", customerId);
        params.addValue("startDate", Global.DB_DATETIME_FMT.format(start));
        params.addValue("endDate", Global.DB_DATETIME_FMT.format(end));

        PstnPackageOrderRowMapper rowMapper = new PstnPackageOrderRowMapper();
        jdbcTemplate.query(sql, params, rowMapper);

        return rowMapper.getPstnPackageMap();
    }


    private static class PstnStandardChargeOrderRowMapper implements RowMapper<PstnStandardChargeOrder> {
        private Map<String, PstnStandardChargeOrder> orderMap = new HashMap<>();
        private List<String> ratesIds = new ArrayList<>();

        @Override
        public PstnStandardChargeOrder mapRow(ResultSet resultSet, int i) throws SQLException {
            String orderId = resultSet.getString("orderId");

            PstnStandardChargeOrder order = orderMap.get(orderId);

            if (order == null) {
                order = new PstnStandardChargeOrder();
                order.setOrderId(resultSet.getString("orderId"));
                order.setCustomerId(resultSet.getString("customerId"));
                order.setChargeSchemaId(resultSet.getString("chargeSchemaId"));
                try {
                    order.setStartDate(Global.DB_DATETIME_FMT.parse(resultSet.getString("startDate")));
                    order.setEndDate(Global.DB_DATETIME_FMT.parse(resultSet.getString("endDate")));
                } catch (ParseException e) {
                }
                orderMap.put(orderId, order);
            }

            String attrValue = resultSet.getString("attrValue");

            if ("COMMON_SITES".equalsIgnoreCase(resultSet.getString("attrName"))) {
                order.setSiteName(attrValue);
            } else {
                order.setPstnRatesId(attrValue);
                ratesIds.add(attrValue);
            }

            return order;
        }

        public Map<String, PstnStandardChargeOrder> getOrderMap() {
            return orderMap;
        }

        public List<String> getRatesIds() {
            return ratesIds;
        }
    }

    public Map<String, String> getSiteNameByOrderIds(List<String> orderIds) {
        String sql = "" +
            "select s.sitename,sor.order_id from " +
            "  B_SITE_ORDER_RELATIONS sor," +
            "  B_WEBEX_SITES s " +
            "where " +
            "  sor.site_id = s.pid" +
            "  and order_id in (:orderIds)";

        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("orderIds", orderIds);

        final Map<String, String> map = new HashMap<>();

        jdbcTemplate.query(sql, params, new RowMapper<String>() {
            @Override
            public String mapRow(ResultSet resultSet, int i) throws SQLException {
                map.put(resultSet.getString("order_id"), resultSet.getString("sitename"));
                return null;
            }
        });

        return map;
    }

    public float[] getCustomerBalance(String customerId) {
        String sql = "" +
            "select account_type, balance " +
            "from bb_account " +
            "where " +
            "customer_id = :customerId " +
            "and account_type in ('DEPOSIT', 'PREPAID')";

        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("customerId", customerId);

        // [DEPOSIT, PREPAID]
        final float[] balances = new float[2];

        jdbcTemplate.query(sql, params, new RowMapper<Object>() {
            @Override
            public Object mapRow(ResultSet rs, int rowNum) throws SQLException {
                String account_type = rs.getString("account_type");
                float balance = rs.getFloat("balance");

                switch (account_type) {
                    case "DEPOSIT":
                        balances[0] = balance;
                        break;
                    case "PREPAID":
                        balances[1] = balance;
                        break;
                }
                return null;
            }
        });

        return balances;
    }

    public Map<String, ProductMonthlyFee> findProductByCustomerId(String customerId, Date start, final Date end) throws IOException {
        String sql = FileUtils.readFromClassPath("/wang/huaichao/crm/find-product-by-customer.sql");
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("customer_id", customerId);
        params.addValue("start_time", Global.DB_DATETIME_FMT.format(start));
        params.addValue("end_time", Global.DB_DATETIME_FMT.format(end));

        final Map<String, ProductMonthlyFee> productMonthlyFeeMap = new HashMap<>();

        jdbcTemplate.query(sql, params, new RowMapper<Product>() {
            @Override
            public Product mapRow(ResultSet resultSet, int i) throws SQLException {
                String orderId = resultSet.getString("order_id");
                String attrName = resultSet.getString("attr_name");
                String attrValue = resultSet.getString("attr_value");

                ProductMonthlyFee fee = productMonthlyFeeMap.get(orderId);

                if (fee == null) {
                    String productName = resultSet.getString("prod_name");
                    Timestamp startDate = resultSet.getTimestamp("start_date");
                    Timestamp endDate = resultSet.getTimestamp("end_date");

                    fee = new ProductMonthlyFee();

                    fee.setOrderId(orderId);
                    fee.setProductName(productName);
                    fee.setStartDate(startDate);
                    fee.setEndDate(endDate);

                    productMonthlyFeeMap.put(orderId, fee);
                }

                if ("HOSTS_AMOUNT".equals(attrName)) {
                    fee.setHosts(Integer.valueOf(attrValue));
                } else if ("PORTS_AMOUNT".equals(attrName)) {
                    fee.setPorts(Integer.valueOf(attrValue));
                } else if ("COMMON_UNIT_PRICE".equals(attrName)) {
                    fee.setPrice(BigDecimal.valueOf(Double.valueOf(attrValue)));
                } else {
                    fee.setSiteName(attrValue);
                }

                return null;
            }
        });


        List<ProductMonthlyFee> lst = new ArrayList<>(productMonthlyFeeMap.values());

        Collections.sort(lst, new Comparator<ProductMonthlyFee>() {
            @Override
            public int compare(ProductMonthlyFee o1, ProductMonthlyFee o2) {
                return o1.getSiteName().compareTo(o2.getSiteName());
            }
        });

        Map<String, ProductMonthlyFee> orderedMap = new LinkedHashMap<>();

        for (ProductMonthlyFee productMonthlyFee : lst) {
            orderedMap.put(productMonthlyFee.getOrderId(), productMonthlyFee);
        }

        return orderedMap;
    }

    public Map<String, PriceList> buildPriceList(List<String> pstnRateIds) throws IOException {
        if (pstnRateIds == null || pstnRateIds.size() == 0) {
            throw new RuntimeException("empty price list ids");
        }

        String sql = FileUtils.readFromClassPath("/wang/huaichao/crm/find-fee-rate.sql");
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("pstnRatesIds", pstnRateIds);

        final Map<String, PriceList> priceListMap = new HashMap<>();

        jdbcTemplate.query(sql, params, new RowMapper<String>() {
            @Override
            public String mapRow(ResultSet resultSet, int i) throws SQLException {
                String pid = resultSet.getString("price_list_id");

                PriceList priceList = priceListMap.get(pid);

                if (priceList == null) {
                    priceList = new PriceList();
                    priceList.setPstnRatesId(pid);
                    priceListMap.put(pid, priceList);
                }

                String access_number = resultSet.getString("access_number");

                if (access_number != null) {
                    access_number = "+" + access_number;
                }

                BigDecimal rate = resultSet.getBigDecimal("rate");

                if (access_number != null) {
                    priceList.addCallInFeeRate(access_number, rate);
                } else {
                    int code = resultSet.getInt("code");
                    if (code == 6) {
                        priceList.setCallInLocalFee(rate);
                    } else if (code == 7) {
                        priceList.setCallOutLocalFee(rate);
                    } else if (code > 100 && code < 109) {
                        priceList.addCallOutFeeRate(code - 99, rate);
                    }
                }
                return null;
            }
        });

        return priceListMap;
    }

    public List<String> findFormalActiveCustomers(int billingPeriod) throws IOException, ParseException {
        String sql = FileUtils.readFromClassPath("/wang/huaichao/crm/find-formal-active-customer.sql");

        DateBuilder db = new DateBuilder(Global.yyyyMM_FMT.parse(billingPeriod + ""));

        Date startDate = db.beginOfMonth().build();
        Date endDate = db.nextMonth().build();

        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("startDate", Global.DB_DATETIME_FMT.format(startDate), Types.VARCHAR);
        params.addValue("endDate", Global.DB_DATETIME_FMT.format(endDate), Types.VARCHAR);


        return jdbcTemplate.query(sql, params, new RowMapper<String>() {
            @Override
            public String mapRow(ResultSet rs, int rowNum) throws SQLException {
                return rs.getString("customer_id");
            }
        });
    }

    public List<String> findNonDirectCustomerIds() {
        String sql = "SELECT DISTINCT PID FROM B_CUSTOMER WHERE AGENT_TYPE <> 'DIRECT'";
        return jdbcTemplate.query(sql, new RowMapper<String>() {
            @Override
            public String mapRow(ResultSet rs, int rowNum) throws SQLException {
                return rs.getString("pid");
            }
        });
    }

    public List<String> findF2fUsers() {
        String sql = "SELECT DISTINCT PID FROM B_CUSTOMER WHERE clevel=5";
        return jdbcTemplate.query(sql, new RowMapper<String>() {
            @Override
            public String mapRow(ResultSet rs, int rowNum) throws SQLException {
                return rs.getString("pid");
            }
        });
    }

    public List<String> findCustomerUnderGivenAgent(String agentId, int billingPeriod) throws ParseException, IOException {
        String sql = FileUtils.readFromClassPath("/wang/huaichao/crm/find-customers-under-given-agent.sql");

        DateUtils.DateRange dateRange = DateUtils.dateRange(billingPeriod);
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("startDate", Global.DB_DATETIME_FMT.format(dateRange.getStartDate()), Types.VARCHAR);
        params.addValue("endDate", Global.DB_DATETIME_FMT.format(dateRange.getEndDate()), Types.VARCHAR);
        params.addValue("agentId", agentId);

        return jdbcTemplate.query(sql, params, new RowMapper<String>() {
            @Override
            public String mapRow(ResultSet rs, int rowNum) throws SQLException {
                return rs.getString("customerId");
            }
        });
    }

    public List<OrderCustomer> findOrdersUnderGivenAgent(String agentId, int billingPeriod) throws ParseException, IOException {
        String sql = FileUtils.readFromClassPath("/wang/huaichao/crm/find-orders-under-given-agent.sql");

        DateUtils.DateRange dateRange = DateUtils.dateRange(billingPeriod);
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("startDate", Global.DB_DATETIME_FMT.format(dateRange.getStartDate()), Types.VARCHAR);
        params.addValue("endDate", Global.DB_DATETIME_FMT.format(dateRange.getEndDate()), Types.VARCHAR);
        params.addValue("agentId", agentId);

        return jdbcTemplate.query(sql, params, orderCustomerRowMapper);
    }

    private static final RowMapper orderCustomerRowMapper = new RowMapper<OrderCustomer>() {
        @Override
        public OrderCustomer mapRow(ResultSet rs, int rowNum) throws SQLException {
            OrderCustomer oc = new OrderCustomer();
            oc.setOrderId(rs.getString("PID"));
            oc.setCustomerId(rs.getString("CUSTOMER_ID"));
            oc.setCustomerName(rs.getString("DISPLAY_NAME"));
            return oc;
        }
    };

    public List<OrderCustomer> findDirectOrdersUnderGivenCustomer(String customerId, int billingPeriod) throws ParseException, IOException {
        String sql = FileUtils.readFromClassPath("/wang/huaichao/crm/find-direct-orders-under-given-customer.sql");

        DateUtils.DateRange dateRange = DateUtils.dateRange(billingPeriod);
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("startDate", Global.DB_DATETIME_FMT.format(dateRange.getStartDate()), Types.VARCHAR);
        params.addValue("endDate", Global.DB_DATETIME_FMT.format(dateRange.getEndDate()), Types.VARCHAR);
        params.addValue("customerId", customerId);

        return jdbcTemplate.query(sql, params, orderCustomerRowMapper);
    }

    public List<String> findCustomersUnderReseller2(int billingPeriod) throws IOException {
        String sql = FileUtils.readFromClassPath("/wang/huaichao/crm/find-customers-under-reseller2.sql");

        DateUtils.DateRange dateRange = DateUtils.dateRange(billingPeriod);
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("startDate", Global.DB_DATETIME_FMT.format(dateRange.getStartDate()), Types.VARCHAR);
        params.addValue("endDate", Global.DB_DATETIME_FMT.format(dateRange.getEndDate()), Types.VARCHAR);

        return jdbcTemplate.query(sql, params, new RowMapper<String>() {
            @Override
            public String mapRow(ResultSet rs, int rowNum) throws SQLException {
                return rs.getString("customerId");
            }
        });
    }

    public List<String> findReseller2s() {
        String sql = "select pid from b_customer where AGENT_TYPE = 'RESELLER2'";
        return jdbcTemplate.query(sql, new RowMapper<String>() {
            @Override
            public String mapRow(ResultSet rs, int rowNum) throws SQLException {
                return rs.getString("pid");
            }
        });
    }

    public List<String> findMainProductOrderIds(String customerId, int billingPeriod) throws IOException {
        String sql = FileUtils.readFromClassPath("/wang/huaichao/crm/find-main-product-orderids.sql");

        DateUtils.DateRange dateRange = DateUtils.dateRange(billingPeriod);
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("startDate", Global.DB_DATETIME_FMT.format(dateRange.getStartDate()), Types.VARCHAR);
        params.addValue("endDate", Global.DB_DATETIME_FMT.format(dateRange.getEndDate()), Types.VARCHAR);
        params.addValue("customerId", customerId, Types.VARCHAR);
        return jdbcTemplate.query(sql, params, new RowMapper<String>() {
            @Override
            public String mapRow(ResultSet rs, int rowNum) throws SQLException {
                return rs.getString("pid");
            }
        });
    }

    public Map<String, List<PortChargeSchema>> findPortChargeSchema(String customerId, int billingPeriod) throws IOException {
        String sql = FileUtils.readFromClassPath("/wang/huaichao/crm/find-port-charge-schema.sql");

        DateUtils.DateRange dateRange = DateUtils.dateRange(billingPeriod);
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("start_time", Global.DB_DATETIME_FMT.format(dateRange.getStartDate()), Types.VARCHAR);
        params.addValue("end_time", Global.DB_DATETIME_FMT.format(dateRange.getEndDate()), Types.VARCHAR);
        params.addValue("customer_id", customerId, Types.VARCHAR);

        final Map<String, PortChargeSchema> map = new HashMap<>();

        jdbcTemplate.query(sql, params, new RowMapper<String>() {
            @Override
            public String mapRow(ResultSet rs, int rowNum) throws SQLException {

                String orderId = rs.getString("order_id");
                PortChargeSchema schema;

                if (map.containsKey(orderId)) {
                    schema = map.get(orderId);
                } else {
                    schema = new PortChargeSchema();
                    try {
                        schema.setStartDate(Global.DB_DATETIME_FMT.parse(
                            rs.getString("start_date")));
                        schema.setEndDate(Global.DB_DATETIME_FMT.parse(
                            rs.getString("end_date")));
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                    map.put(orderId, schema);
                }

                String attr_name = rs.getString("attr_name");
                String attr_value = rs.getString("attr_value");

                if ("COMMON_SITE".equals(attr_name)) {
                    schema.setSiteName(attr_value);
                } else if ("PORTS_AMOUNT".equals(attr_name)) {
                    schema.setPorts(Integer.valueOf(attr_value));
                } else if ("COMMON_UNIT_PRICE".equals(attr_name)) {
                    schema.setPrice(BigDecimal.valueOf(
                        Double.valueOf(attr_value)));
                } else if ("COMMON_OVERFLOW_UNIT_PRICE".equals(attr_name)) {
                    schema.setOverflowPrice(BigDecimal.valueOf(
                        Double.valueOf(attr_value)));
                }

                return null;
            }
        });

        Map<String, List<PortChargeSchema>> xxx = new HashMap<>();

        for (PortChargeSchema x : map.values()) {
            String siteName = x.getSiteName();
            if (!xxx.containsKey(siteName)) {
                xxx.put(siteName, new ArrayList<PortChargeSchema>());
            }
            xxx.get(siteName).add(x);
        }

        return xxx;
    }
}
