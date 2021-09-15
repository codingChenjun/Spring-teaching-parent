package config;

import com.filter.MyFilter;
import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;

import javax.servlet.*;
import java.util.EnumSet;

/**
 * @author cj
 * @date 2019/11/25
 */
public class SystemConfig extends AbstractAnnotationConfigDispatcherServletInitializer {
    /**
     * 当你的项目只有一个spring 容器,那么只需要下面方法返回有值即可,getServletConfigClasses返回null即可
     *
     * 如果有2个容器,那么这2个配置类的方法都需要返回配置类
     * @return
     */
    @Override
    protected Class<?>[] getRootConfigClasses() {
        return new Class[]{AppConfig.class};
    }

    @Override
    protected Class<?>[] getServletConfigClasses() {
        return new Class[]{MvcConfig.class};
    }

    @Override
    protected String[] getServletMappings() {
        return new String[]{"/"};
    }

    /**
     * 这个方法是用来继续对DispatcherServlet进行自定义配置的.
     *
     * 设置让Spring mvc支持文件上传
     * @param registration
     */
    @Override
    protected void customizeRegistration(ServletRegistration.Dynamic registration) {
        registration.setMultipartConfig(new MultipartConfigElement("",
                2000, 20000, 0));


    }

    //================注册过滤器的方法一:利用getServletFilters与registerServletFilter,两者结合一起完成========

    /**
     * 这个方法注册的filter就是给DispatcherServlet用的,它的方式是让注册器关联到servlet
     * @return
     */
    /*@Override
    protected Filter[] getServletFilters() {
        return new Filter[]{new MyFilter()};
    }*/

    /**
     * 这个方法具体完成过滤器的注册工作,如果需要自定义注册设置,比如设置其urlPattern,而不是保留关联到DispatcherServlet的话
     * 就需要重写这个方法,
     * @param servletContext
     * @param filter
     * @return
     */
    /*@Override
    protected FilterRegistration.Dynamic registerServletFilter(ServletContext servletContext, Filter filter) {
        //这几行代码是自定义过滤器的注册配置
        String filterName = "myfilter";
        FilterRegistration.Dynamic filterRegistration = servletContext.addFilter(filterName, filter);
        filterRegistration.addMappingForUrlPatterns(EnumSet.of(DispatcherType.REQUEST), false, "/*");

       return filterRegistration;
    }*/

    //===========注册过滤器的方法二=============
    @Override
    public void onStartup(ServletContext servletContext) throws ServletException {
        String filterName = "myfilter";
        FilterRegistration.Dynamic filterRegistration = servletContext.addFilter(filterName, new MyFilter());
        filterRegistration.addMappingForUrlPatterns(EnumSet.of(DispatcherType.REQUEST), false, "/*");
        //父类的方法一定要调用,否则getRootConfigClasses与getServletConfigClasses等方法都会失效
        super.onStartup(servletContext);
    }
}
