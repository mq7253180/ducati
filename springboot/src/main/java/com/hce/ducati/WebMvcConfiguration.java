package com.hce.ducati;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.PropertySource;
import org.springframework.web.method.support.HandlerMethodReturnValueHandler;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerAdapter;
import org.springframework.web.servlet.mvc.method.annotation.RequestResponseBodyMethodProcessor;

import com.hce.auth.freemarker.ButtonTemplateDirectiveModelBean;
import com.hce.auth.freemarker.DivTemplateDirectiveModelBean;
import com.hce.auth.freemarker.HyperlinkTemplateDirectiveModelBean;
import com.hce.auth.freemarker.InputTemplateDirectiveModelBean;
import com.hce.auth.service.AuthorizationService;
import com.hce.core.view.GlobalHandlerMethodReturnValueHandler;
import com.hce.core.view.GlobalLocaleResolver;
import com.hce.core.view.I18NInterceptor;
import com.hce.core.view.StaticInterceptor;
import com.hce.core.view.freemarker.AttributeTemplateDirectiveModelBean;
import com.hce.core.view.freemarker.I18NTemplateDirectiveModelBean;
import com.hce.core.view.freemarker.LocaleTemplateDirectiveModelBean;
import com.hce.core.view.freemarker.PropertiesTemplateDirectiveModelBean;
import com.hce.ducati.freemarker.PaginationTemplateDirectiveModelBean;
import com.hce.global.Constants;

import freemarker.template.Configuration;

@PropertySource("classpath:application-springboot.properties")
@SpringBootConfiguration
public class WebMvcConfiguration extends WebMvcConfigurationSupport implements InitializingBean {
	@Autowired
    private RequestMappingHandlerAdapter adapter;
	@Autowired
	private ApplicationContext applicationContext;
	@Resource(name = "${impl.auth.service}")
	private AuthorizationService authorizationService;
	@Resource(name = "${impl.auth.interceptor}")
	private HandlerInterceptorAdapter handlerInterceptorAdapter;
	@Value("${env}")
	private String env;
	@Value("${server.port}")
	private String cluster;

	@Override
	protected void addInterceptors(InterceptorRegistry registry) {
		if(Constants.ENV_DEV.equals(env))
			registry.addInterceptor(new StaticInterceptor()).addPathPatterns("/static/**");
		registry.addInterceptor(new I18NInterceptor()).addPathPatterns("/**");
		registry.addInterceptor(handlerInterceptorAdapter).addPathPatterns("/**");
	}

    @Override
    public void afterPropertiesSet() throws Exception {
        List<HandlerMethodReturnValueHandler> returnValueHandlers = adapter.getReturnValueHandlers();
        List<HandlerMethodReturnValueHandler> handlers = new ArrayList<HandlerMethodReturnValueHandler>(returnValueHandlers);
        decorateHandlers(handlers);
        adapter.setReturnValueHandlers(handlers);
    }

    private void decorateHandlers(List<HandlerMethodReturnValueHandler> handlers) {
        for(HandlerMethodReturnValueHandler handler:handlers) {
            if(handler instanceof RequestResponseBodyMethodProcessor) {
            	HandlerMethodReturnValueHandler decorator = new GlobalHandlerMethodReturnValueHandler(handler, applicationContext, cluster);
                int index = handlers.indexOf(handler);
                handlers.set(index, decorator);
                break;
            }
        }
    }

    @Bean
    public LocaleResolver localeResolver() {
        return new GlobalLocaleResolver();
    }

    @Resource(name = "propertiesFactoryBean")
    private Properties properties;
    @Autowired
    private Configuration configuration;

    @PostConstruct
    public void freeMarkerConfigurer() {
    		configuration.setSharedVariable("attr", new AttributeTemplateDirectiveModelBean());
    		configuration.setSharedVariable("i18n", new I18NTemplateDirectiveModelBean(properties));
    		configuration.setSharedVariable("property", new PropertiesTemplateDirectiveModelBean(properties));
    		configuration.setSharedVariable("locale", new LocaleTemplateDirectiveModelBean());
    		configuration.setSharedVariable("input", new InputTemplateDirectiveModelBean());
    		configuration.setSharedVariable("a", new HyperlinkTemplateDirectiveModelBean());
    		configuration.setSharedVariable("button", new ButtonTemplateDirectiveModelBean());
    		configuration.setSharedVariable("div", new DivTemplateDirectiveModelBean());
    		configuration.setSharedVariable("p", new PaginationTemplateDirectiveModelBean());
    }

    /*@Bean
    public ViewResolver viewResolver() {
        FreeMarkerViewResolver resolver = new FreeMarkerViewResolver();
        resolver.setCache(true);
        resolver.setSuffix(".ftl");
        resolver.setContentType("text/html; charset=UTF-8");
        resolver.setAllowSessionOverride(true);
        return resolver;
    }*/

    /*@Bean
    public FreeMarkerConfigurer freeMarkerConfigurer() {
		String path = null;
		if("dev".equals(env)) {
			path = this.getClass().getResource("/").getPath();
			path = path.substring(0, path.indexOf("springboot/target/classes"))+"springboot/src/main/view/page/";
		} else {
			path = ftlLocation;
		}
		path = "file:"+path;
		log.info("VIEW_LOCATION====================="+path);
		Map<String, Object> variables = new HashMap<String, Object>(3);
		variables.put("attr", new AttributeTemplateDirectiveModelBean());
		variables.put("i18n", new I18NTemplateDirectiveModelBean(properties));
		variables.put("property", new PropertiesTemplateDirectiveModelBean(properties));
		configuration.setSharedVariable("attr", new AttributeTemplateDirectiveModelBean());
		configuration.setSharedVariable("i18n", new I18NTemplateDirectiveModelBean(properties));
		configuration.setSharedVariable("property", new PropertiesTemplateDirectiveModelBean(properties));
        FreeMarkerConfigurer configurer = new FreeMarkerConfigurer();
        configurer.setTemplateLoaderPaths(path);
        configurer.setDefaultEncoding("UTF-8");
        configurer.setFreemarkerVariables(variables);
        return configurer;
    }*/
}
