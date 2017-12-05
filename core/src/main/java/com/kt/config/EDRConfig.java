package com.kt.config;

import com.alibaba.druid.filter.Filter;
import com.alibaba.druid.filter.logging.Log4jFilter;
import com.alibaba.druid.pool.DruidDataSource;
import com.kt.common.dbhelp.DbHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.PropertySource;
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
 * EDRConfig
 */
@Configuration
@EnableTransactionManagement
@EnableJpaRepositories(
        entityManagerFactoryRef = "edrEntityManagerFactory",
        transactionManagerRef = "edrTransactionManager",
        basePackages = {"com.kt.repo.edr"})
@PropertySource("classpath:edr.properties")
public class EDRConfig {
    @Value("${edr.database.url}")
    private String url;

    @Value("${edr.database.username}")
    private String username;

    @Value("${edr.database.password}")
    private String password;

    @Value("${edr.database.initialSize}")
    private String initialSize;

    @Value("${edr.database.minIdle}")
    private String minIdle;

    @Value("${edr.database.maxActive}")
    private String maxActive;

    @Value("${edr.database.maxWait}")
    private String maxWait;

    @Value("${edr.database.timeBetweenEvictionRunsMillis}")
    private String timeBetweenEvictionRunsMillis;

    @Value("${edr.database.minEvictableIdleTimeMillis}")
    private String minEvictableIdleTimeMillis;

    @Value("${edr.database.poolPreparedStatements}")
    private String poolPreparedStatements;

    @Value("${edr.database.maxPoolPreparedStatementPerConnectionSize}")
    private String maxPoolPreparedStatementPerConnectionSize;

    @Bean(name="edrDataSource")
    public DataSource edrDataSource() throws Exception {
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
        filters.add(edrLog4jFilter());
        dataSource.setProxyFilters(filters);
        return dataSource;
    }

    @Bean
    public Log4jFilter edrLog4jFilter() {
        Log4jFilter log4jFilter = new Log4jFilter();
        log4jFilter.setResultSetLogEnabled(false);
        log4jFilter.setStatementExecutableSqlLogEnable(true);
        return log4jFilter;
    }

    @Bean(name="edrEntityManagerFactory")
    public EntityManagerFactory edrEntityManagerFactory(@Qualifier("edrDataSource")DataSource edrDataSource) throws Exception {
        HibernateJpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter();
//        vendorAdapter.setGenerateDdl(true);

        LocalContainerEntityManagerFactoryBean factory = new LocalContainerEntityManagerFactoryBean();
        factory.setJpaVendorAdapter(vendorAdapter);
        factory.setPackagesToScan("com.kt.repo.edr", "com.kt.entity.edr");
        factory.setDataSource(edrDataSource);
        Properties properties = new Properties();
        //properties.put("hibernate.dialect", "org.hibernate.dialect.MySQL5Dialect");
        properties.put("hibernate.dialect", "org.hibernate.dialect.Oracle10gDialect");
        properties.put("hibernate.ejb.naming_strategy", "org.hibernate.cfg.ImprovedNamingStrategy");
        properties.put("hibernate.cache.provider_class", "org.hibernate.cache.NoCacheProvider");
        properties.put("hibernate.show_sql", true);
        properties.put("hibernate.format_sql", false);
//        properties.put("hibernate.hbm2ddl.auto", "none");
        factory.setJpaProperties(properties);
        factory.afterPropertiesSet();

        return factory.getObject();
    }

    /*
    @Bean
    public PlatformTransactionManager edrTransactionManager() throws Exception {
        JpaTransactionManager transactionManager = new JpaTransactionManager();
        transactionManager.setEntityManagerFactory(edrEntityManagerFactory());
        return transactionManager;
    }

    @Bean
    public TransactionAwareDataSourceProxy edrTransactionAwareDataSourceProxy() throws Exception {
        return new TransactionAwareDataSourceProxy(edrDataSource());
    }

    @Bean
    public DbHelper edrDbHelper() throws Exception {
        DbHelper dbHelper = new DbHelper(edrTransactionAwareDataSourceProxy);
        dbHelper.setDialect("oracle");
        //dbHelper.setDialect("mysql");
        return dbHelper;
    }
    */
    @Bean(name="edrTransactionManager")
    public PlatformTransactionManager edrTransactionManager(@Qualifier("edrEntityManagerFactory")EntityManagerFactory entityManagerFactory) throws Exception {
        JpaTransactionManager transactionManager = new JpaTransactionManager();
        transactionManager.setEntityManagerFactory(entityManagerFactory);
        return transactionManager;
    }

    @Bean(name="edrTransactionAwareDataSourceProxy")
    public TransactionAwareDataSourceProxy edrTransactionAwareDataSourceProxy(@Qualifier("edrDataSource")DataSource dataSource) throws Exception {
        return new TransactionAwareDataSourceProxy(dataSource);
    }

    @Bean(name="edrDbHelper")
    public DbHelper edrDbHelper(@Qualifier("edrTransactionAwareDataSourceProxy")TransactionAwareDataSourceProxy transactionAwareDataSourceProxy) throws Exception {
        DbHelper dbHelper = new DbHelper(transactionAwareDataSourceProxy);
        dbHelper.setDialect("oracle");
        //dbHelper.setDialect("mysql");
        return dbHelper;
    }
}
