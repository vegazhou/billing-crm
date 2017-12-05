package com.kt.sys;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.Map;

/**
 * Created by Vega Zhou on 2015/11/4.
 */
@Component
public class SpringContextHolder implements ApplicationContextAware {

    private static ApplicationContext applicationContext;

    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        SpringContextHolder.applicationContext = applicationContext;
    }

    public static ApplicationContext getApplicationContext() {
        return applicationContext;
    }



    public static <T> T getBean(Class<T> clazz) {
        checkApplicationContext();
        Map<String, T> candidates = applicationContext.getBeansOfType(clazz);
        if (!candidates.isEmpty()) {
            return candidates.values().iterator().next();
        }
        return null;
    }

    public static <T> Collection<T> getBeans(Class<T> clazz) {
        checkApplicationContext();
        Map<String, T> candidates = applicationContext.getBeansOfType(clazz);
        return candidates.values();
    }

    private static void checkApplicationContext() {
        if (applicationContext == null) {
            throw new IllegalStateException("applicationContext not injected");
        }
    }
}
