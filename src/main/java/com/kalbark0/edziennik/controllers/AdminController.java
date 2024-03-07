package com.kalbark0.edziennik.controllers;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.kalbark0.edziennik.model.ErrorMessages;
import com.kalbark0.edziennik.model.NewGradeQuery;
import com.kalbark0.edziennik.model.NewUserQuery;
import com.kalbark0.edziennik.model.Person;
import com.kalbark0.edziennik.model.Query;

@Controller
@RequestMapping("/admin")
public class AdminController {
	
	@GetMapping
	public String get(Model model, @RequestParam("userId") String userId) {
		model.addAttribute("userId", userId);
		return "admin";
	}
	
	@GetMapping("/add")
	public String add(Model model) {
		NewUserQuery query = new NewUserQuery();
		model.addAttribute(query);
		return "addUser";
	}
	
	@PostMapping("/add")
	public ModelAndView addPost(@ModelAttribute("query") NewUserQuery query) throws SQLException {
		String firstName = query.getFirstName();
		String lastName = query.getLastName();
		String zawod_user = query.getZawod_user();
		String nick_user = query.getNick_user();
		String password = query.getPassword();
		
		Connection connection = DriverManager.getConnection("jdbc:mysql://localhost/edziennik", "root", "admin");
		Statement statement = connection.createStatement();
		int result = statement.executeUpdate("insert into user (lastname_user, firstname_user, job_user, nick_user, password_user) values('"  + lastName + "','" 
		+ firstName + "','" + zawod_user + "','" + nick_user + "','" + password + "')");
		
		if(result > 0) {
			Map<String, Object> model = new HashMap<>();
			model.put("message", "Użytkownik został stworzony");
			ModelAndView mv = new ModelAndView("redirect:/admin/add/addedUser", model);
		    return mv;
		}
		else {
			Map<String, Object> model = new HashMap<>();
			model.put("message", ErrorMessages.INVALID_DATA);
			ModelAndView mv = new ModelAndView("redirect:/error", model);
		    return mv;
		}
	}
	
	@GetMapping("/delete")
	public String delete(Model model) {
		NewUserQuery query = new NewUserQuery();
		model.addAttribute(query);
		return "deleteUser";
	}
	
	@PostMapping("/delete")
	public ModelAndView deletePost(@ModelAttribute("query") NewUserQuery query) throws SQLException {
		String nick_user = query.getNick_user();
		
		Connection connection = DriverManager.getConnection("jdbc:mysql://localhost/edziennik", "root", "admin");
		Statement statement = connection.createStatement();
		int result = statement.executeUpdate("delete from user where nick_user ='" + nick_user + "'");
		if(result > 0) {
			Map<String, Object> model = new HashMap<>();
			model.put("message", "Użytkownik został usunięty");
			ModelAndView mv = new ModelAndView("redirect:/admin/delete/deletedUser", model);
		    return mv;
		}
		else {
			Map<String, Object> model = new HashMap<>();
			model.put("message", ErrorMessages.INVALID_DATA);
			ModelAndView mv = new ModelAndView("redirect:/error", model);
		    return mv;
		}
	}
	
	@GetMapping("/update")
	public String update(Model model) {
		NewUserQuery query = new NewUserQuery();
		model.addAttribute(query);
		return "updateUser";
	}
	
	@PostMapping("/update")
	public ModelAndView updatePost(@ModelAttribute("query") NewUserQuery query) throws SQLException {
		String firstName = query.getFirstName();
		String lastName = query.getLastName();
		String zawod_user = query.getZawod_user();
		String nick_user = query.getNick_user();
		String password = query.getPassword();
		
		Connection connection = DriverManager.getConnection("jdbc:mysql://localhost/edziennik", "root", "admin");
		Statement statement = connection.createStatement();
		int result = statement.executeUpdate("update user set lastname_user='"  + lastName + "',firstname_user='" + firstName + "',job_user='" + zawod_user + "',password_user='" + password + "'where nick_user='" + nick_user + "'");
		if(result > 0) {
			Map<String, Object> model = new HashMap<>();
			model.put("message", "Użytkownik został zaktualizowany");
			ModelAndView mv = new ModelAndView("redirect:/admin/update/updatedUser", model);
		    return mv;
		}
		else {
			Map<String, Object> model = new HashMap<>();
			model.put("message", ErrorMessages.INVALID_DATA);
			ModelAndView mv = new ModelAndView("redirect:/error", model);
		    return mv;
		}
	}
}
