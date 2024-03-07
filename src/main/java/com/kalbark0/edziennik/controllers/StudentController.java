package com.kalbark0.edziennik.controllers;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.kalbark0.edziennik.model.Person;
import com.kalbark0.edziennik.model.Grade;
import com.kalbark0.edziennik.model.PasswordChange;

@Controller
@RequestMapping("/student")
public class StudentController {
	
	@GetMapping
	public String get(Model model, @RequestParam("userId") String userId) throws SQLException{
		Connection connection = DriverManager.getConnection("jdbc:mysql://localhost/edziennik", "root", "admin");
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery("select * from user where id_user='" + userId + "'");
		    	
		while(resultSet.next()) {
        String userFirstName = resultSet.getString(3);
		String userLastName = resultSet.getString(2);
		                            
		Person person = new Person(userFirstName, userLastName, userId);
		
		model.addAttribute("person", person);		
		}
		Map<String, List<Grade>> tmpMap = new HashMap<String, List<Grade>>();
		                    
		        ResultSet grades = statement.executeQuery("select * from grades where id_student='" + userId + "'");
		        while(grades.next()) {
		        	String idSubject = grades.getString("id_subject");
		            int grade = Integer.parseInt(grades.getString("grade"));
		            String description = grades.getString("description");
		                        
		            Grade gradeCon = new Grade(grade, description);
		            
		            List<Grade> tmpList = tmpMap.getOrDefault(idSubject, new ArrayList<Grade>());  
		            tmpList.add(gradeCon);
		            tmpMap.put(idSubject, tmpList);
		            }
		                    
		        ResultSet subjects = statement.executeQuery("SELECT class.class_name, subjects.id_subject, subjects.subject" 
		        + " FROM class"
		        + " INNER JOIN subjects ON class.class_name=subjects.class"
		        + " WHERE class.id_student='" + userId + "'"
		        + " ORDER BY subjects.subject ASC");
		                    
		        LinkedHashMap<String, List<Grade>> resultMap = new LinkedHashMap<String, List<Grade>>();  
		        while(subjects.next()) {
		        	String idSubject = subjects.getString("id_subject");
		            String subject = subjects.getString("subject");
		                        
		            List<Grade> tmpList = tmpMap.getOrDefault(idSubject, new ArrayList<>());  
		            resultMap.put(subject, tmpList);
		        }
		        ResultSet clazz = statement.executeQuery("select * from class where id_student='" + userId + "'");
		        while(clazz.next()) {
		        	String className = clazz.getString("class_name");
		        	model.addAttribute("className", className);
		        }
		                    
		        model.addAttribute("przedmioty", resultMap);
		        return "student";
		}
	@RequestMapping(value = "/change", method = RequestMethod.POST)
	public ModelAndView submit(@ModelAttribute("student") PasswordChange query) throws SQLException  {
		String oldPassword = query.getOldPassword();
		String newPassword = query.getNewPassword();
		String repeatNewPassword = query.getRepeatNewPassword();
		int idParent = query.getIdParent();
		
		Connection connection = DriverManager.getConnection("jdbc:mysql://localhost/edziennik", "root", "admin");
        Statement statement = connection.createStatement();

        ResultSet basePassword =  statement.executeQuery("select * from user where id_user='" + idParent + "'");
		while(basePassword.next()){
			String oldPasswordFromBase = basePassword.getString("password_user");
			if(oldPasswordFromBase.equals(oldPassword)) {
				if(newPassword.equals(repeatNewPassword)) {
					int result = statement.executeUpdate("update user set password_user='"  + newPassword + "'where id_user='" + idParent + "'");
					if(result > 0) {
						Map<String, Object> model = new HashMap<>();
						model.put("message", "Hasło zostało zmienione pomyślnie");
						ModelAndView mv = new ModelAndView("redirect:/parent/change/message", model);
					    return mv;
					}
					else {
						Map<String, Object> model = new HashMap<>();
						model.put("message", "Wystąpił błąd, prosze spróbować ponownie");
						ModelAndView mv = new ModelAndView("redirect:/parent/change/message", model);
					    return mv;
					}
				}
				else {
					Map<String, Object> model = new HashMap<>();
					model.put("message", "Nowe hasła się różniły");
					ModelAndView mv = new ModelAndView("redirect:/parent/change/message", model);
				    return mv;
				}
			}
			else {
				Map<String, Object> model = new HashMap<>();
				model.put("message", "Wprowadzono złe poprzednie hasło");
				ModelAndView mv = new ModelAndView("redirect:/parent/change/message", model);
			    return mv;
			}
		}
		Map<String, Object> model = new HashMap<>();
		model.put("message", "Wystąpił błąd");
		ModelAndView mv = new ModelAndView("redirect:/parent/change/message", model);
	    return mv;
	}
}