package com.aksrua.common.config;

import com.aksrua.common.filter.AuthTokenFilter;
import com.querydsl.core.annotations.Config;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;

@Config
public class WebConfig {

	@Bean
	public FilterRegistrationBean<AuthTokenFilter> authTokenFilter(AuthTokenFilter filter) {
		FilterRegistrationBean<AuthTokenFilter> filterRegistrationBean = new FilterRegistrationBean<>();
		filterRegistrationBean.setFilter(filter);
		filterRegistrationBean.addUrlPatterns("/api/v1/*"); // 보호할 경로
		return filterRegistrationBean;
	}
}
