package com.kalbark0.edziennik.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/admin/add/addedUser")
public class AddedUserController {
	
	@GetMapping
	public String get(Model model, @RequestParam("message") String message) {
		model.addAttribute("message", message);
		return "addedUser";
	}
}
