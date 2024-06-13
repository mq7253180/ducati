package com.hce.ducati;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.ObjectProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
//import org.springframework.session.data.redis.config.annotation.SpringSessionRedisConnectionFactory;

import com.hce.ducati.freemarker.PaginationTemplateDirectiveModelBean;

//@PropertySource("classpath:application-springboot.properties")
@Configuration
//@SpringBootConfiguration
public class SpringBootConfiguration {
    @Autowired
    private freemarker.template.Configuration configuration;
    private Test test;

//    @Autowired
//    public void setTest(@SpringSessionRedisConnectionFactory Test t1, Test t2) {
//    	this.test = t1!=null?t1:t2;
//    	System.out.println("TTTTTTTTTTT==========="+t1+"================"+t2+"-------"+test.getValue());
//    }

//    @Autowired
//    public void setTestFactory(@SpringSessionRedisConnectionFactory ObjectProvider<Test> t1, ObjectProvider<Test> t2) {
//    	this.test = t1.getIfAvailable(t2::getObject);
//    	System.out.println("TTTTTTTTTTT==========="+test.getValue());
//    }

    @PostConstruct
    public void init() {
    	configuration.setSharedVariable("p", new PaginationTemplateDirectiveModelBean());
    }

    /*@Bean
    public ServletRegistrationBean<HystrixMetricsStreamServlet> getServlet() {
    	HystrixMetricsStreamServlet streamServlet =new HystrixMetricsStreamServlet();
        ServletRegistrationBean<HystrixMetricsStreamServlet> registrationBean = new ServletRegistrationBean<HystrixMetricsStreamServlet>(streamServlet);
        ServletRegistrationBean<HystrixMetricsStreamServlet> registrationBean = new ServletRegistrationBean<HystrixMetricsStreamServlet>();
        registrationBean.setServlet(streamServlet);
        registrationBean.setEnabled(true);
        registrationBean.setLoadOnStartup(1);
        registrationBean.addUrlMappings("/hystrix.stream");
//        registrationBean.addUrlMappings("/actuator/hystrix.stream");
//        registrationBean.setName("HystrixMetricsStreamServlet");
        return registrationBean;
    }*/
}
