package config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.interceptor.FirstInterceptor;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.format.FormatterRegistry;
import org.springframework.format.datetime.DateFormatter;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.servlet.config.annotation.*;

import java.text.SimpleDateFormat;
import java.util.List;

/**
 * @author cj
 * @date 2019/11/25
 */

@Configuration
@EnableWebMvc
@ComponentScan({"com.controller", "com.interceptor","com.exception"})
public class MvcConfig implements WebMvcConfigurer {


   /* @Bean
    public InternalResourceViewResolver viewResolver() {
        InternalResourceViewResolver viewResolver = new InternalResourceViewResolver();
        viewResolver.setSuffix(".jsp");
        viewResolver.setPrefix("/WEB-INF/views/");
        return viewResolver;

    }*/


    @Override
    public void configureViewResolvers(ViewResolverRegistry registry) {
        registry.jsp("/WEB-INF/views/", ".jsp");
    }

    @Override
    public void addFormatters(FormatterRegistry registry) {
        registry.addFormatter(new DateFormatter("yyyy-MM-dd"));
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        InterceptorRegistration interceptorRegistry = registry.addInterceptor(new FirstInterceptor());
        interceptorRegistry.addPathPatterns("/**");
    }

    /**
     *   // 这个配置通常也是用来处理静态资源用的,这种方法可以与上面的方法混用,但推荐用上面的方法
     *   它会注册一个RequestMapping,并且优先级是最低的(Integer.maxValue)
     * @param configurer
     */
    @Override
    public void configureDefaultServletHandling(DefaultServletHandlerConfigurer configurer) {
        configurer.enable();//表示默认servlet名字spring知道,不需要指定,一般是default
//        configurer.enable("默认servlet的名字");//spring不知道servlet容器的默认servlet名字时用这个重载
    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        ResourceHandlerRegistration registration
                = registry.addResourceHandler("/static/**");
        registration.addResourceLocations("classpath:/static/");
    }

    /**
     * 这里添加的转换器是不会添加默认转换器的,
     * 如果想在保留默认转换器的情况下添加消息转换器,可以重写extendMessageConverters方法
     * @param converters
     */
    /*@Override
    public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.setDateFormat(sdf);
        MappingJackson2HttpMessageConverter converter = new MappingJackson2HttpMessageConverter(objectMapper);
        converters.add(converter);

    }
*/
    @Override
    public void extendMessageConverters(List<HttpMessageConverter<?>> converters) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.setDateFormat(sdf);
        MappingJackson2HttpMessageConverter converter = new MappingJackson2HttpMessageConverter(objectMapper);
        //通过设置索引,让自己的转换器放在最前面,否则默认的jackson转换器会在前面,用不上我们设置的转换器.
        converters.add(0, converter);
    }
}
