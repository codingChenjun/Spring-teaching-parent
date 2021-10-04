package config.simple;

import com.dao.impl.EmployeDaoImpl;
import com.service.EmployeeService;
import com.service.impl.EmployeeServiceImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author cj
 * @date 2019/11/5
 */

@Configuration
public class SimpleConfigIOC3 {


    @Bean
    public EmployeDaoImpl employeDao() {
        return new EmployeDaoImpl();
    }

    /**
     * 通过调用同一个配置类中的其它@Bean修饰的方法来完成注入
     *
     */
    @Bean
    public EmployeeService employeeService() {
        EmployeeServiceImpl employeeService = new EmployeeServiceImpl();
        employeeService.setDao(employeDao());
        return employeeService;
    }
}
