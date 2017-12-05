package wang.huaichao.conf;

import com.mchange.v2.c3p0.ComboPooledDataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.factory.config.PropertyPlaceholderConfigurer;
import org.springframework.context.annotation.*;
import org.springframework.core.io.ClassPathResource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;

import javax.sql.DataSource;

/**
 * Created by Administrator on 8/16/2016.
 */
@Configuration
@EnableAsync
@EnableScheduling
@ComponentScan(basePackages = {"wang.huaichao", "com.kt.config"})
@PropertySource("classpath:/wang/huaichao/billing.properties")
public class AppConfig {

    @Bean(name = "edrJdbcTemplate")
    @Autowired
    public NamedParameterJdbcTemplate edrJdbcTemplate(
            @Qualifier("edrDataSource") DataSource datasource) {
        return new NamedParameterJdbcTemplate(datasource);
    }

    @Bean(name = "crmJdbcTemplate")
    @Autowired
    public NamedParameterJdbcTemplate crmJdbcTemplate(DataSource datasource) {
        return new NamedParameterJdbcTemplate(datasource);
    }

}
