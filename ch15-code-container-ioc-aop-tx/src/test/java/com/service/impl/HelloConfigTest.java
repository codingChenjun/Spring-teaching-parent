package com.service.impl;

import com.dao.EmployeeDao;
import config.HelloConfig;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

/**
 * @author cj
 * @date 2019/11/5
 */
public class HelloConfigTest {

  @Test
  public void testBeanRegister() {
    ApplicationContext context = new AnnotationConfigApplicationContext(HelloConfig.class);
    System.out.println("==========getBean========");
    EmployeeDao dao = context.getBean("employeDaoImpl", EmployeeDao.class);
    EmployeeDao dao2 = context.getBean("employeDaoImpl", EmployeeDao.class);
    System.out.println(dao);
    System.out.println(dao2);

  }

  @Test
  public void testConfigurationClass() {
    ApplicationContext context = new AnnotationConfigApplicationContext(HelloConfig.class);
    System.out.println("==========getBean========");
    //当把Configuration注解的proxyBeanMethods设置为true时,配置类会被代理
    HelloConfig config= context.getBean( HelloConfig.class);
    System.out.println(config);

  }


}
