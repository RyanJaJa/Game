package com.exce.util;

import com.google.common.collect.Lists;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import java.util.List;

public class RestTemplateSingleton {

    private static RestTemplate instance;

    // 多執行緒時，當物件需要被建立時才使用synchronized保證RestTemplateSingleton一定是單一的 ，增加程式校能
    public static RestTemplate getInstance() {
        if (instance == null) {
            synchronized (RestTemplateSingleton.class) {
                if (instance == null) {
                    instance = new RestTemplate();
                    List<HttpMessageConverter<?>> messageConverters = Lists.newArrayList();
                    //Add the Jackson Message converter
                    MappingJackson2HttpMessageConverter converter = new MappingJackson2HttpMessageConverter();
                    // Note: here we are making this converter to process any kind of response,
                    // not only application/*json, which is the default behaviour
                    converter.setSupportedMediaTypes(Lists.newArrayList(MediaType.ALL));
                    messageConverters.add(converter);
                    instance.setMessageConverters(messageConverters);
                }
            }
        }
        return instance;
    }
}
