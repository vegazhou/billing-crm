package com.kt.config;


//import com.skytech.csm.service.ContractService;
//import com.skytech.csm.service.OrderService;
//import com.skytech.csm.service.ReportService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.remoting.caucho.HessianProxyFactoryBean;

/**
 * Created by Administrator on 2017/6/15.
 */
@Configuration
public class HessianConfig {
    @Value("${hessian.base.path}")
    private String hessianBasePath;

    private void setFactoryUrl(HessianProxyFactoryBean factory, String serviceUrl) {
        factory.setServiceUrl(hessianBasePath + serviceUrl);
    }

//    @Bean
//    public HessianProxyFactoryBean contractService() {
//        HessianProxyFactoryBean factory = new HessianProxyFactoryBean();
//        setFactoryUrl(factory, "/contractService");
//        factory.setServiceInterface(ContractService.class);
//        factory.setOverloadEnabled(true);
//        factory.setAllowNonSerializable(true);
//        return factory;
//    }
//
//    @Bean
//    public HessianProxyFactoryBean orderService() {
//        HessianProxyFactoryBean factory = new HessianProxyFactoryBean();
//        setFactoryUrl(factory, "/orderService");
//        factory.setServiceInterface(OrderService.class);
//        factory.setOverloadEnabled(true);
//        factory.setAllowNonSerializable(true);
//        return factory;
//    }
//
//    @Bean
//    public HessianProxyFactoryBean reportService() {
//        HessianProxyFactoryBean factory = new HessianProxyFactoryBean();
//        setFactoryUrl(factory, "/reportService");
//        factory.setServiceInterface(ReportService.class);
//        factory.setOverloadEnabled(true);
//        factory.setAllowNonSerializable(true);
//        return factory;
//    }

}
