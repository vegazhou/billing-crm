package com.kt.config;

import com.alibaba.druid.filter.Filter;
import com.alibaba.druid.filter.logging.Log4jFilter;
import com.alibaba.druid.pool.DruidDataSource;
import com.kt.common.dbhelp.DbHelper;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.jdbc.datasource.TransactionAwareDataSourceProxy;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceContext;
import javax.sql.DataSource;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

/**
 * MysqlConfig
 */
@Configuration
@EnableTransactionManagement
@EnableJpaRepositories(
        entityManagerFactoryRef = "billingEntityManagerFactory",
        transactionManagerRef = "billingTransactionManager",
        basePackages = {"com.kt.repo.mysql", "wang.huaichao.data.repo"})
public class MysqlConfig {
    @Value("${mysql.url}")
    private String url;

    @Value("${mysql.username}")
    private String username;

    @Value("${mysql.password}")
    private String password;

    @Value("${mysql.initialSize}")
    private String initialSize;

    @Value("${mysql.minIdle}")
    private String minIdle;

    @Value("${mysql.maxActive}")
    private String maxActive;

    @Value("${mysql.maxWait}")
    private String maxWait;

    @Value("${mysql.timeBetweenEvictionRunsMillis}")
    private String timeBetweenEvictionRunsMillis;

    @Value("${mysql.minEvictableIdleTimeMillis}")
    private String minEvictableIdleTimeMillis;

    @Value("${mysql.poolPreparedStatements}")
    private String poolPreparedStatements;

    @Value("${mysql.maxPoolPreparedStatementPerConnectionSize}")
    private String maxPoolPreparedStatementPerConnectionSize;

    @Bean(name="dataSource")
    @Primary
    public DataSource dataSource() throws Exception {
        DruidDataSource dataSource = new DruidDataSource();
        dataSource.setUrl(url);
        dataSource.setUsername(username);
        dataSource.setPassword(password);
        dataSource.setInitialSize(Integer.parseInt(initialSize));
        dataSource.setMinIdle(Integer.parseInt(minIdle));
        dataSource.setMaxActive(Integer.parseInt(maxActive));
        dataSource.setMaxWait(Long.parseLong(maxWait));
        dataSource.setTimeBetweenEvictionRunsMillis(Long.parseLong(timeBetweenEvictionRunsMillis));
        dataSource.setMinEvictableIdleTimeMillis(Long.parseLong(minEvictableIdleTimeMillis));
        dataSource.setPoolPreparedStatements(Boolean.valueOf(poolPreparedStatements));
        dataSource.setMaxPoolPreparedStatementPerConnectionSize(Integer.parseInt(maxPoolPreparedStatementPerConnectionSize));
        dataSource.setValidationQuery("SELECT 'x' from dual");
        dataSource.setTestWhileIdle(true);
        dataSource.setTestOnBorrow(false);
        dataSource.setTestOnReturn(false);
        dataSource.setFilters("stat,log4j");
        List<Filter> filters = new ArrayList<Filter>();
        filters.add(log4jFilter());
        dataSource.setProxyFilters(filters);
        return dataSource;
    }

    @Bean(name="log4jFilter")
    public Log4jFilter log4jFilter() {
        Log4jFilter log4jFilter = new Log4jFilter();
        log4jFilter.setResultSetLogEnabled(false);
        log4jFilter.setStatementExecutableSqlLogEnable(true);
        return log4jFilter;
    }

    @Bean(name="billingEntityManagerFactory")
    @Primary
    public EntityManagerFactory billingEntityManagerFactory(@Qualifier("dataSource")DataSource dataSource) throws Exception {
        HibernateJpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter();
//        vendorAdapter.setGenerateDdl(true);

        LocalContainerEntityManagerFactoryBean factory = new LocalContainerEntityManagerFactoryBean();
        factory.setJpaVendorAdapter(vendorAdapter);
        factory.setPackagesToScan(
                "com.kt.repo.mysql",
                "com.kt.entity.mysql",
                "wang.huaichao.data.entity"
        );
        factory.setDataSource(dataSource);
        Properties properties = new Properties();
        //properties.put("hibernate.dialect", "org.hibernate.dialect.MySQL5Dialect");
        properties.put("hibernate.dialect", "org.hibernate.dialect.Oracle10gDialect");
        properties.put("hibernate.ejb.naming_strategy", "org.hibernate.cfg.ImprovedNamingStrategy");
        properties.put("hibernate.cache.provider_class", "org.hibernate.cache.NoCacheProvider");
        properties.put("hibernate.show_sql", false);
        properties.put("hibernate.format_sql", false);
//        properties.put("hibernate.hbm2ddl.auto", "none");
        factory.setJpaProperties(properties);
        factory.afterPropertiesSet();

        return factory.getObject();
    }

    @Bean(name="billingTransactionManager")
    @Primary
    public PlatformTransactionManager billingTransactionManager(@Qualifier("billingEntityManagerFactory")EntityManagerFactory entityManagerFactory) throws Exception {
        JpaTransactionManager transactionManager = new JpaTransactionManager();
        transactionManager.setEntityManagerFactory(entityManagerFactory);
        return transactionManager;
    }

    @Bean(name="transactionAwareDataSourceProxy")
    public TransactionAwareDataSourceProxy transactionAwareDataSourceProxy(@Qualifier("dataSource")DataSource dataSource) throws Exception {
        return new TransactionAwareDataSourceProxy(dataSource);
    }

    @Bean(name="dbHelper")
    @Primary
    public DbHelper dbHelper(@Qualifier("transactionAwareDataSourceProxy")TransactionAwareDataSourceProxy transactionAwareDataSourceProxy) throws Exception {
        DbHelper dbHelper = new DbHelper(transactionAwareDataSourceProxy);
        dbHelper.setDialect("oracle");
        //dbHelper.setDialect("mysql");
        return dbHelper;
    }
}
