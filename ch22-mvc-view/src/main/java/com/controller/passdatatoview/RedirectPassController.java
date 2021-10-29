package com.controller.passdatatoview;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.FlashMap;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.support.RequestContextUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Map;

/**
 * @author cj
 * @date 2019/11/14
 */
@Controller
@RequestMapping("/redirect")
public class RedirectPassController {
    // 第一组方法:利用session(HttpSession)和application(ServletContext)
    @GetMapping("/demo1")
    public String demo1(HttpSession session){
        session.setAttribute("f", "/redirect/demo1");
        return "redirect:demo12";
    }

    @GetMapping("/demo12")
    public String demo12(){

        return "view";
    }

// 利用拼接url的方式传递数据,url致命缺点:1.长度是有限制,2.拼接字符繁琐
    @GetMapping("/demo21")
    public String demo21(){

        return "redirect:demo22?f=redirect-demo21";
    }

    @GetMapping("/demo22")
    public String demo22(String f, Model model){
        model.addAttribute("f",f);

        return "view";
    }

    // RedirectAttributes其实就是利用FlashMap实现的
    @GetMapping("/demo31")
    public String demo31(RedirectAttributes redirectAttributes){
        //因为我们是重定向到32,所以addAttribute添加的数据自动帮我们拼接到url上
        redirectAttributes.addAttribute("f1", "demo31 f1");
        // addFlashAttribute是把数据自动放到session中,跳转到的方法demo32的model参数自动有这个值.

        // 利用addFlashMap方法添加的数据会保存到 OUTPUT_FLASH_MAP 中,
        // 跳转到的方法除了Model方式也可以通过Input FlashMap的方式来获取到数据

        //addFlashAttribute添加的数据会放到session中,但会在跳转发生之后从session中取出此数据(在本方法执行完毕后)
        // 并添加到请求作用域中,所以你在demo32对应的视图中取数据时requestScope有值,sessionScope没值
        redirectAttributes.addFlashAttribute("f", "demo3-flash");
        return "redirect:demo32";
    }

    @GetMapping("/demo32")
    public String demo32(String f1, Model model){
        return "view";
    }

    //因为addFlashAttribute是把数据放到session中,读取到了之后自动从session中删除数据
    //这个方法用来演示已经读不到数据,操作步骤:/demo31->跳转到了32,接着访问/demo33
    @GetMapping("/demo33")
    public String demo33(){
        return "view";
    }

    // RequestContextUtils
   // Annotated controllers typically do not need to work with FlashMap directly.
   // Instead, a @RequestMapping method can accept an argument of type RedirectAttributes
   // and use it to add flash attributes for a redirect scenario.
   // Flash attributes added through RedirectAttributes are automatically propagated to the “output” FlashMap.
   // Similarly, after the redirect, attributes from the “input” FlashMap are automatically
   // added to the Model of the controller that serves the target URL.

    // 不能把FlashMap作为参数,会出现argument type mismatch异常,
    // 说把BindingAwareModelMap不能绑定FlashMap上
    @GetMapping("/demo41")
    public String demo41(HttpServletRequest request){
        //直接先请求此方法,input flash map是null的,output flashmap不是null的,但size=0
//        Map<String, ?> inputFlashMap = RequestContextUtils.getInputFlashMap(request);
//        System.out.println("---41----" + inputFlashMap.get("f"));
//        System.out.println("---41----" + inputFlashMap.size());
        FlashMap flashMap = RequestContextUtils.getOutputFlashMap(request);
        flashMap.put("f", "flashmap");
        //flashMap.addTargetRequestParam("f", "flashmap");
        return "redirect:demo42";
    }

    @GetMapping("/demo42")
    public String demo42(HttpServletRequest request){
        Map<String, ?> flashMap = RequestContextUtils.getInputFlashMap(request);
        //下面的代码会输出值,但也会把f值从session中删除,导致视图想获取数据时报nullReferenceException
        //System.out.println("-----42----" + flashMap.get("f"));
        return "view";
    }
}
