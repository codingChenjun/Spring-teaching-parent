package controller;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Date;

/**
 * @author cj
 * @date 2019/11/22
 */

@Controller
public class HelloController {

    @RequestMapping("/hello")
    public String hello(){
        return "hello";
    }

    //在配置类里就是所有的方法都生效,注解只对其修饰的方法生效
    @RequestMapping("/d")
    public String hello(@DateTimeFormat(pattern = "yyyy-MM-dd") Date date){
        System.out.println("-------d 接收日期-----"+ date);
        return "hello";
    }
}
