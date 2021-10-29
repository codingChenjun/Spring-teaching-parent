package config;

import controller.MyFirstInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.InterceptorRegistration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.view.InternalResourceViewResolver;

/**
 * @author cj
 * @date 2019/11/22
 */

@Configuration
@ComponentScan("controller")
// 等价于xml的mvc:annotation-driven,提供了转换校验功能,不加这个注解Formatter不能加,DateTimeFormat注解也会报错
@EnableWebMvc
public class MvcConfig implements WebMvcConfigurer {


    @Bean
    public InternalResourceViewResolver resourceViewResolver() {
        InternalResourceViewResolver resourceViewResolver = new InternalResourceViewResolver();
        resourceViewResolver.setPrefix("/WEB-INF/views/");
        resourceViewResolver.setSuffix(".jsp");
        return resourceViewResolver;
    }



    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        MyFirstInterceptor firstInterceptor = new MyFirstInterceptor();
        InterceptorRegistration registration = registry.addInterceptor(firstInterceptor);
        registration.addPathPatterns("/**");
    }

/*    @Override
    public void addFormatters(FormatterRegistry registry) {
        DateFormatter formatter = new DateFormatter();
        formatter.setPattern("yyyy-MM-dd");
        registry.addFormatter(formatter);
    }*/
}
