package com.hce.ducati;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.context.ApplicationPidFileWriter;
import org.springframework.cloud.openfeign.EnableFeignClients;
//import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
//import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import com.quincy.auth.AuthConstants;
import com.quincy.sdk.Constants;

@EnableFeignClients
//@EnableDiscoveryClient
//@EnableEurekaClient
@MapperScan(basePackages = {AuthConstants.PACKAGE_NAME_MAPPER, "com.hce.ducati.mapper"})
@EntityScan(basePackages = {Constants.PACKAGE_NAME_ENTITY, AuthConstants.PACKAGE_NAME_ENTITY, "com.hce.ducati.entity"})
@EnableJpaRepositories(basePackages = {Constants.PACKAGE_NAME_REPOSITORY, AuthConstants.PACKAGE_NAME_REPOSITORY, "com.hce.ducati.dao"})
@EnableTransactionManagement
@EnableWebMvc
@EnableJpaAuditing
@EnableAutoConfiguration
@EnableScheduling
@SpringBootApplication/*(exclude = {
        DataSourceAutoConfiguration.class
})*/
@ComponentScan(basePackages= {"com.*"})
public class DucatiBootstrap {
    public static void main(String[] args) {
    		SpringApplication sa = new SpringApplication(DucatiBootstrap.class);
        sa.addListeners(new ApplicationPidFileWriter());
        sa.run(args);
//        SpringApplication.run(Bootstrap.class, args);
    }
}
