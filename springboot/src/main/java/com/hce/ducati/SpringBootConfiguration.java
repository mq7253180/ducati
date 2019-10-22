package com.hce.ducati;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.SpringBootConfiguration;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

import com.hce.ducati.freemarker.PaginationTemplateDirectiveModelBean;

@PropertySource("classpath:application-springboot.properties")
@Configuration
//@SpringBootConfiguration
public class SpringBootConfiguration {
    @Autowired
    private freemarker.template.Configuration configuration;

    @PostConstruct
    public void freeMarkerConfigurer() {
    	configuration.setSharedVariable("p", new PaginationTemplateDirectiveModelBean());
    }
}
