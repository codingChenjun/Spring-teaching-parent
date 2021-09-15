package com.controller.jump;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.view.RedirectView;

/** 这里的案例指的是在一个或多个控制器间的方法间跳转
 *
 * @author cj
 * @date 2019/11/14
 */
@Controller
@RequestMapping("/jump")
public class JumpController {
    ///////////////////////////////
    /////////////转发(forward):指的是多个请求方法之间的转发
    ///////////////////

    @RequestMapping("/demo1")
    public String demo1(){
        return "forward:demo2";
    }
    @RequestMapping("/demo2")
    public String demo2(){
        return "view";
    }



    ///////////////////////////////
    /////////////重定向(redirect):指的是多个请求方法之间的跳转
    ///////////////////


    @RequestMapping("/demo4")
    public String demo4(){
        //以前缀redirect:修饰,后面跟着的是一个重定向的url地址,
        // 视图解析器直接一句指定的地址加载视图(不加在xml中设定的前后缀值)
        return "redirect:dis"; //: /view/dis
    }

    @RequestMapping("/demo5")
    public RedirectView demo5(){
        //返回RedirectView时,指定的是跳转的地址,
        //视图解析器直接一句指定的地址加载视图(不加在xml中设定的前后缀值)
        RedirectView rv = new RedirectView("dis");
        return  rv;
    }


    @RequestMapping("/dis")
    public String dis(){
        return "view";
    }




}
