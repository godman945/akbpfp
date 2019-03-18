package com.pchome.alex;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/")
public class AlexController {
	 protected Logger log = LogManager.getRootLogger();
	 
	@RequestMapping(value = { "/alex" }, method = RequestMethod.GET)
	public String homePage(ModelMap model) {
		  
		log.info(">>>>>>>>>>>>>>>>>>>>>>>>:TEST");
		
		System.out.println("DDDD");
		
		return "summary.view";
	}

	@RequestMapping(value = { "/products" }, method = RequestMethod.GET)
	public String productsPage(ModelMap model) {
		return "products";
	}

	@RequestMapping(value = { "/contactus" }, method = RequestMethod.GET)
	public String contactUsPage(ModelMap model) {
		return "contactus";
	}
}
