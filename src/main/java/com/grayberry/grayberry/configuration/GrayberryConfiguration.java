package com.grayberry.grayberry.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.grayberry.grayberry.filters.PosterOnlyFilter;

@Configuration
public class GrayberryConfiguration
{
    @Autowired
    private PosterOnlyFilter postOnlyFilter;
    
    @Bean
    public FilterRegistrationBean<PosterOnlyFilter> registerPosterOnlyFilter()
    {
        FilterRegistrationBean<PosterOnlyFilter> bean = new FilterRegistrationBean<>();
        bean.setFilter(postOnlyFilter);
        bean.addUrlPatterns("/Post/createNewPost");
        bean.addUrlPatterns("/Post/deletePost");
        return bean;
    }
}
