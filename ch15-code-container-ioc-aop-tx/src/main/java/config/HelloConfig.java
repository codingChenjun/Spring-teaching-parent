package config;

import com.dao.impl.EmployeDaoImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 *
 * @Configuration注解放置在类上面
 * 一般这样做:
 * 1.所有的配置类,单独用一个包存放
 * 2.此注解一般放在单独的类上面,比如下面的HelloConfig
 * 3.此注解放在某个配置类内部的静态类上面,这种配置方法跟顶级类上
 * 添加Configuration注解一样.主要起到组织作用,把相关的东西
 * 放在一起配置
 *
 *
 * 2 .Configuration上面也有一个元注解Component
 *意味着这个配置类自己本身也是一个被spring管理的普通bean
 *
 * bean方法:用Bean注解修饰的方法
 *
 * bean方法可以出现在配置类里面,也可以出现在普通类里面
 * 第一种称之为full模式,第二种情况称之为lite模式.(参考https://juejin.cn/post/6881528714715201549)
 *
 * 3. 讲解Configuration的proxyBeanMethods属性的含义
 *
 *
 * @author cj
 * @date 2019/11/5
 */
@Configuration(proxyBeanMethods = false)
public class HelloConfig {

    public HelloConfig() {
        System.out.println("---helloconfig 构造函数---");
    }

    /**
     * 加了Bean注解的方式,其作用是用来往spring中注册bean的
     *  没有指定Scope,默认就是单例
     *
     *  bean方法,如果放在@Configuration修饰的类中,那么此配置类默认会被动态代理
     *  这样就支持bean间(inter-bean)方法调用了.
     *
     *  代理之后的方法的运行逻辑是这样:
     *  1.检查当前spring容器中有没有这个bean已经注册,
     *  没有就调用这个bean方法,有就直接返回,什么都不干.
     *
     *  当把Configuration注解的proxyBeanMethods设置为true时,配置类会被代理
     *  设置为true时Bean方法不能是private以及final修饰的
     *
     *  如果要配置BFPP,那么就用静态的bean方法即可,BPP正常注册
     * @return
     */
    @Bean
    public  EmployeDaoImpl employeDaoImpl(){
        System.out.println("注册EmployeeDaoImpl---");
        return new EmployeDaoImpl();
    }



    @Configuration
    static  class MyInnerConfig {

    }
}
