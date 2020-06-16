package com.hce.ducati;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Configuration;

import com.hce.ducati.freemarker.PaginationTemplateDirectiveModelBean;
import com.netflix.hystrix.contrib.metrics.eventstream.HystrixMetricsStreamServlet;

//@PropertySource("classpath:application-springboot.properties")
@Configuration
//@SpringBootConfiguration
public class SpringBootConfiguration {
    @Autowired
    private freemarker.template.Configuration configuration;

    @PostConstruct
    public void init() {
    	configuration.setSharedVariable("p", new PaginationTemplateDirectiveModelBean());
    }

//    @Bean
    public ServletRegistrationBean<HystrixMetricsStreamServlet> getServlet() {
    	HystrixMetricsStreamServlet streamServlet =new HystrixMetricsStreamServlet();
        ServletRegistrationBean<HystrixMetricsStreamServlet> registrationBean = new ServletRegistrationBean<HystrixMetricsStreamServlet>(streamServlet);
        /*ServletRegistrationBean<HystrixMetricsStreamServlet> registrationBean = new ServletRegistrationBean<HystrixMetricsStreamServlet>();
        registrationBean.setServlet(streamServlet);*/
        registrationBean.setEnabled(true);
        registrationBean.setLoadOnStartup(1);
        registrationBean.addUrlMappings("/hystrix.stream");
//        registrationBean.addUrlMappings("/actuator/hystrix.stream");
//        registrationBean.setName("HystrixMetricsStreamServlet");
        return registrationBean;
    }
}
