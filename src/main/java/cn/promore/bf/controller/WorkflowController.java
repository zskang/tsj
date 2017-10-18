package cn.promore.bf.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 *@author huzd@si-tech.com.cn or ahhzd@vip.qq
 *@version createrTime:May 6, 2017 9:06:24 PM
 **/

@Controller
public class WorkflowController {

	@RequestMapping("/sayHello.mvc")
	public String sayHello(ModelMap model) {
		model.addAttribute("helloworld", "Spring 4 MVC");
		System.out.println("================= spring mvc =====================");
		return "springmvc";
	}
	
	

}
