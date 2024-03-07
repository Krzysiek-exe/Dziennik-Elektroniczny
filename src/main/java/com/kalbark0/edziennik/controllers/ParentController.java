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

import com.kalbark0.edziennik.model.Grade;
import com.kalbark0.edziennik.model.NewGradeQuery;
import com.kalbark0.edziennik.model.PasswordChange;
import com.kalbark0.edziennik.model.Person;

@Controller
@RequestMapping("/parent")
public class ParentController {
	
	@GetMapping
	public String get(Model model, @RequestParam("userId") String userId) throws SQLException{
		Connection connection = DriverManager.getConnection("jdbc:mysql://localhost/edziennik", "root", "admin");
        Statement statement = connection.createStatement();
        ResultSet user = statement.executeQuery("select * from parent where id_student='" + userId + "'");
        String idParent = null;
        while(user.next()) {
        	idParent = user.getString("id_parent");
        }
        ResultSet parentData = statement.executeQuery("select * from user where id_user='" + idParent + "'");
		while(parentData.next()) {
	        String userFirstName = parentData.getString(3);
			String userLastName = parentData.getString(2);
			                            
			Person person = new Person(userFirstName, userLastName, idParent);
			
			model.addAttribute("parent", person);		
			}
        
        ResultSet resultSet = statement.executeQuery("select * from user where id_user='" + userId + "'");
		    	
		while(resultSet.next()) {
        String userFirstName = resultSet.getString(3);
		String userLastName = resultSet.getString(2);
		                            
		Person person = new Person(userFirstName, userLastName, userId);
		
		model.addAttribute("child", person);		
		}
		Map<String, List<Grade>> tmpMap = new HashMap<String, List<Grade>>();
				
		        //stworzenie mapy uczeń->(przedmiot->ocena)  
				LinkedHashMap <Person, Map<String, List<Grade>>> finalMap = new LinkedHashMap<Person, Map<String, List<Grade>>>();
				ResultSet finalSet = statement.executeQuery("select subjects.class, subjects.subject, parent.id_parent, class.id_student, user.lastname_user, user.firstname_user, grades.grade, grades.description "
						+ "from subjects "
						+ "inner join grades on subjects.id_subject = grades.id_subject "
						+ "inner join class on grades.id_student = class.id_student "
						+ "inner join user on class.id_student = user.id_user "
						+ "inner join parent on class.id_student = parent.id_student "
						+ "WHERE parent.id_parent='" + idParent + "'"
						+ "ORDER BY subjects.class ASC, user.lastname_user ASC");
				
				while(finalSet.next()) {
					String studentId = finalSet.getString("id_student");
					String studentLastname = finalSet.getString("lastname_user");
					String studentFirstname = finalSet.getString("firstname_user");
					Person person = new Person(studentFirstname, studentLastname, studentId);
					
		            String subject = finalSet.getString("subject");
					
					int grade = Integer.parseInt(finalSet.getString("grade"));
					String description = finalSet.getString("description");
			        Grade gradeCon = new Grade(grade, description);
					
					Map<String, List<Grade>> studentMap = finalMap.getOrDefault(person, new LinkedHashMap<String, List<Grade>>());
					List<Grade> gradess = studentMap.getOrDefault(subject, new ArrayList<Grade>());
					
					gradess.add(gradeCon);
					studentMap.put(subject, gradess);
					finalMap.put(person, studentMap);
				}
				model.addAttribute("students",finalMap);                  
				return "parent";
				
	}
	@RequestMapping(value = "/change", method = RequestMethod.POST)
	public ModelAndView submit(@ModelAttribute("parent") PasswordChange query) throws SQLException  {
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