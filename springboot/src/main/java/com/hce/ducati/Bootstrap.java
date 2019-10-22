package com.hce.ducati;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import com.quincy.auth.AuthConstants;

//@EnableDiscoveryClient
//@EnableEurekaClient
@MapperScan(basePackages = {AuthConstants.PACKAGE_NAME_MAPPER, "com.hce.auth.mapper", "com.hce.ducati.mapper"})
@EntityScan(basePackages = {AuthConstants.PACKAGE_NAME_ENTITY, "com.hce.auth.entity", "com.hce.ducati.entity"})
@EnableJpaRepositories(basePackages = {AuthConstants.PACKAGE_NAME_REPOSITORY, "com.hce.auth.dao", "com.hce.ducati.dao"})
@EnableTransactionManagement
@EnableFeignClients
@EnableWebMvc
@EnableJpaAuditing
@EnableAutoConfiguration
@EnableScheduling
@SpringBootApplication/*(exclude = {
        DataSourceAutoConfiguration.class
})*/
@ComponentScan(basePackages= {"com.*"})
public class Bootstrap {
    public static void main(String[] args) {
        SpringApplication.run(Bootstrap.class, args);
    }
}
