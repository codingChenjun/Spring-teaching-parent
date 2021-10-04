package com.aop;

import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.stereotype.Component;
//这个类是给AopTest类的testAnnoAopWithoutXml方法用的,
// 这个案例不应该放在这章,主要是为了测试AnnotationConfigApplicationContext扫描包时是否对这些Enable注解生效
@Component
@EnableAspectJAutoProxy
public class EnableAopAspectj {
}
