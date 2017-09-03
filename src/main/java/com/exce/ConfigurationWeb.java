package com.exce;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpMethod;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import javax.annotation.PostConstruct;
import java.util.Arrays;
import java.util.List;

@Configuration
public class ConfigurationWeb extends WebMvcConfigurerAdapter {

    private static Logger log = LoggerFactory.getLogger(ConfigurationWeb.class);

    @Autowired
    private Environment env;

    @Value("${spring.application.name}")
    private String applicationName;

    @Value("${goldluck.setting.crossDomain}")
    private String crossDomain;

    @PostConstruct
    public void init() {
        log.debug("***** Mvc Active Profiles : " + StringUtils.join(env.getActiveProfiles()) + " *****");
    }

    @Override
    public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
        MappingJackson2HttpMessageConverter converter = new MappingJackson2HttpMessageConverter();
        ObjectMapper mapper = new ObjectMapper();
        mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        converter.setObjectMapper(mapper);
        converters.add(converter);
    }

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        if (StringUtils.isNotEmpty(crossDomain)) {
            String[] allowDomains = StringUtils.split(crossDomain);
            log.info(String.format("Allow CORS domains %s", Arrays.toString(allowDomains)));

            registry.addMapping("/**")
                    .allowedOrigins(allowDomains)
                    .allowedMethods("POST", "GET", "PUT", "DELETE", "OPTIONS");
        }
    }

}
