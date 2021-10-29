package com.controller;
import org.springframework.aop.support.AopUtils;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Constructor;
import java.lang.reflect.Parameter;

// 参考文档:https://stackoverflow.com/questions/22674044/inject-httpservletrequest-into-controller
@Controller
public class RequestTestController implements InitializingBean {
  // 注入的是一个代理,其实没有问题,也就是说可以这样做,也没有线程安全的问题
  @Autowired
  private HttpServletRequest request;

  private HttpServletRequest r1;
  private HttpServletRequest r2;

  /**
   * 运行测试时,你会发现请求index1或index2,每次参数中的HttpServletRequest的值的hashcode是一样的
   * 在非spring mvc环境下,在纯粹的servlet环境下测试也会发现请求对象都是同一个hashcode
   * 这应该是因为容器为了节省资源,对象只创建一个,但其值是依据每次请求的不同有不同,比如请求地址
   * @param request1
   * @return
   */
  @RequestMapping("/index1")
  public String index1(HttpServletRequest request1){

    System.out.println("==================");
    this.r1 = request1;
    System.out.println(r1 == r2);

    System.out.println(request1.hashCode());
    System.out.println(request1.getClass().getName());
    System.out.println(AopUtils.isAopProxy(request1));
    System.out.println("*************************");
    System.out.println(request.hashCode());
    System.out.println(request.getClass().getName());
    //=======
    Class<? extends HttpServletRequest> aClass = request1.getClass();
    /*Field[] declaredFields = aClass.getDeclaredFields();
    for (Field declaredField : declaredFields) {
      System.out.println(declaredField.getName()+ "---" + declaredField.getType() + declaredField.hashCode());
    }*/
    Constructor<?>[] constructors = aClass.getConstructors();
    for (Constructor<?> constructor : constructors) {
      Parameter[] parameters = constructor.getParameters();
      for (Parameter parameter : parameters) {
        System.out.println(parameter.getName() + "--" + parameter.getType() + "---" + parameter.hashCode());
      }
    }
    return "index";
  }

  @RequestMapping("/index2")
  public String index2(HttpServletRequest request2){

    System.out.println("==================");
    this.r2 = request2;
    System.out.println(r1 == r2);
    System.out.println(request2.hashCode());
    System.out.println(request2.getClass().getName());
    System.out.println(AopUtils.isAopProxy(request2));
    System.out.println("*************************");
    System.out.println(request.hashCode());
    System.out.println(request.getClass().getName());
    return "index";
  }

  @Override
  public void afterPropertiesSet() throws Exception {
    System.out.println("==================");
    System.out.println(request.hashCode());
    System.out.println(request.getClass().getName());

    System.out.println("==================");
        /*Class<? extends HttpServletRequest> aClass = request.getClass();
        Field[] declaredFields = aClass.getDeclaredFields();
        for (Field field : declaredFields) {
            System.out.println(field.getName());
        }

        Constructor<?>[] constructors = aClass.getConstructors();
        for (Constructor<?> constructor : constructors) {
            Parameter[] parameters = constructor.getParameters();
            for (Parameter parameter : parameters) {
                System.out.println(parameter.getName() + "--" + parameter.getType());
            }
        }*/
  }
}