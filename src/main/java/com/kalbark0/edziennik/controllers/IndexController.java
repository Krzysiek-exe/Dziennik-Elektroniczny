package com.kalbark0.edziennik.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping(
		value = {"/","/index"})
public class IndexController {
	private final String message = "Hello World";
	
	@GetMapping
	public String indexView(Model model) {
		System.out.println("weszło");
		model.addAttribute("hello", message);
		return "index";
	}
}
