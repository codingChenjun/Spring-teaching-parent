package config.simple;

import com.parentchild.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 这不是上课要讲的案例,主要是测试注册同样的Bean时,@Bean修饰的方法的重载问题
 * @author cj
 * @date 2019/11/5
 */

@Configuration
public class SimpleParentChildConfig {


    @Bean
    public UserService userService() {
        System.out.println("userService 无参注册");
        UserService service = new UserServiceImpl1();
        return service;
    }


//表象:总是注册参数多的...这个网址有部分解释(但不全)https://www.jianshu.com/p/f0335aff54c1
    @Bean
    public UserService userService(UserDao dao,UserDao dao2) {
        System.out.println("userService 有2参注册");
        UserServiceImpl2 service = new UserServiceImpl2();
        //service.setDao(dao);
        return service;
    }


    @Bean
    public UserService userService(UserDao dao) {
        System.out.println("userService 有参注册");
        UserServiceImpl2 service = new UserServiceImpl2();
        //service.setDao(dao);
        return service;
    }
    @Bean
    public UserDaoImpl1 userDao(){
        return new UserDaoImpl1();
    }
}
