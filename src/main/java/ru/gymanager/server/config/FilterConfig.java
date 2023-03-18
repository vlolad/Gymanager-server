package ru.gymanager.server.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ru.gymanager.server.filter.TransactionFilter;

@Configuration
public class FilterConfig {
    @Autowired
    private TransactionFilter transactionFilter;

    // Регистрация фильтра для логирования входящих запросов и ответов
    @Bean
    public FilterRegistrationBean<TransactionFilter> filterRegistration() {
        FilterRegistrationBean<TransactionFilter> filterFilterRegistrationBean = new FilterRegistrationBean<>();
        filterFilterRegistrationBean.setFilter(transactionFilter);
        filterFilterRegistrationBean.setName("Transaction logger filter");
        filterFilterRegistrationBean.setOrder(1);
        return filterFilterRegistrationBean;
    }
}
