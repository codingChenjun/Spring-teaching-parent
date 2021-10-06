package config.simple;

import com.dao.EmployeeDao;
import com.dao.impl.EmployeDaoImpl;
import com.service.EmployeeService;
import com.service.impl.EmployeeServiceImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * proxyBeanMethods设置为false之后不要进行bean间调用,
 * 因为没有getBean的效果,只是普通方法的调用
 * @author cj
 * @date 2019/11/5
 */
@Configuration(proxyBeanMethods = false)
public class SimpleConfigIOC3 {


    @Bean
    public EmployeDaoImpl employeDao() {
        System.out.println("注册EmployeeDaoImpl的bean方法");
        return new EmployeDaoImpl();
    }

    /**
     * 通过调用同一个配置类中的其它@Bean修饰的方法来完成注入
     */
    @Bean
    public EmployeeService employeeService() {
        EmployeeServiceImpl employeeService = new EmployeeServiceImpl();
        System.out.println("====================开始调用bean方法");
        EmployeeDao dao = employeDao();
        System.out.println("====================结束调用bean方法");
        System.out.println("dao = " + dao);
        employeeService.setDao(dao);
        return employeeService;
    }
}
