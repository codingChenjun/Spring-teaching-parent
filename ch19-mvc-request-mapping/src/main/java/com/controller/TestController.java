package com.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
// 可以删掉
@Controller
@RequestMapping("/restful")
public class TestController {

  @GetMapping("/g")
  public String abc(){
    System.out.println("-------/** ----abc ----");
    return "index";
  }
}
