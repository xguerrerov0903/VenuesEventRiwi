package com.xguerrerov.venues.infrastructure.config.logging;


import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class LoggingConfig {

    @Bean
    public FilterRegistrationBean<MdcFilter> mdcFilterRegistration() {
        FilterRegistrationBean<MdcFilter> registrationBean = new FilterRegistrationBean<>();
        registrationBean.setFilter(new MdcFilter());
        registrationBean.setOrder(1);
        return registrationBean;
    }
}
