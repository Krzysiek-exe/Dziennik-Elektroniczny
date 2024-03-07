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
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.kalbark0.edziennik.model.Clazz;
import com.kalbark0.edziennik.model.ErrorMessages;
import com.kalbark0.edziennik.model.Grade;
import com.kalbark0.edziennik.model.NewGradeQuery;
import com.kalbark0.edziennik.model.NewUserQuery;
import com.kalbark0.edziennik.model.PasswordChange;
import com.kalbark0.edziennik.model.Person;

@Controller
@RequestMapping("/teacher")
public class TeacherController {
	
	@GetMapping
	public String get(Model model, @RequestParam("userId") String userId) throws SQLException{
		Connection connection = DriverManager.getConnection("jdbc:mysql://localhost/edziennik", "root", "admin");
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery("select * from user where id_user='" + userId + "'");   	
		//dodanie imienia i nazwiska
        while(resultSet.next()) {
        String userFirstName = resultSet.getString(3);
		String userLastName = resultSet.getString(2);
		                            
		Person person = new Person(userFirstName, userLastName, userId);
		
		model.addAttribute("person", person);		
		}
		
		List<String> classList = new ArrayList<String>();
		int idSubject = 0;
        ResultSet subjectTaught = statement.executeQuery("select * from subjects where id_teacher='" + userId + "'");
		while(subjectTaught.next()) {
			String subject = subjectTaught.getString("subject"); //nauczany przedmiot
			idSubject = Integer.parseInt(subjectTaught.getString("id_subject"));
			model.addAttribute("subject", subject);
		}
		
	//stworzenie mapy klasa->(uczeń->ocena)  
	LinkedHashMap <Clazz, Map<Person, List<Grade>>> finalMap = new LinkedHashMap<Clazz, Map<Person, List<Grade>>>();
    Statement statement1 = connection.createStatement();
	ResultSet classesList = statement1.executeQuery("select * from subjects where id_teacher ='" + userId + "'");
	
	while(classesList.next()) {
		String className = classesList.getString("class");
		
		int idTeacher = Integer.parseInt(userId);
		Clazz clazz = new Clazz(className, idSubject, idTeacher);
		
        Statement statement2 = connection.createStatement();
		ResultSet studentList = statement2.executeQuery("select * from class where class_name ='" + className + "'");
		while(studentList.next()) {
			String idStudent = studentList.getString("id_student");

	        Statement statement3 = connection.createStatement();
			ResultSet students = statement3.executeQuery("select * from user where id_user='" + idStudent + "'");
			while(students.next()) {
				String studentLastname = students.getString("lastname_user");
				String studentFirstname = students.getString("firstname_user");
				Person person = new Person(studentFirstname, studentLastname, idStudent);
				
		        Map<Person, List<Grade>> studentMap = finalMap.getOrDefault(clazz, new LinkedHashMap<Person, List<Grade>>());
				List<Grade> grades = studentMap.getOrDefault(person, new ArrayList<Grade>());
				ResultSet gradess = statement.executeQuery("select * from grades where id_student='" + idStudent + "' and id_teacher ='" + userId + "'");
				while(gradess.next()) {
					int grade = Integer.parseInt(gradess.getString("grade"));
					String description = gradess.getString("description");
			        Grade gradeCon = new Grade(grade, description);
			        
					grades.add(gradeCon);
				}
				studentMap.put(person, grades);
				finalMap.put(clazz, studentMap);	
			}
		}
	}
	model.addAttribute("students",finalMap);                  
	return "teacher";
}
	
	@RequestMapping(value = "/add", method = RequestMethod.POST)
	public ModelAndView submit(@ModelAttribute("teacher") NewGradeQuery query) throws SQLException  {
		int idSubject = query.getIdSubject();
		int idStudent = query.getIdStudent();
		int idTeacher = query.getIdTeacher();
		int grade = query.getGrade();
		String description = query.getDescription();
		
		Connection connection = DriverManager.getConnection("jdbc:mysql://localhost/edziennik", "root", "admin");
        Statement statement = connection.createStatement();
		int result = statement.executeUpdate("insert into grades (id_student, id_teacher, id_subject, grade, description) "
				+ "values('"  + idStudent + "','" + idTeacher + "','" + idSubject + "','" + grade + "','" + description + "')");
		
		Map<String, Object> model = new HashMap<>();
        model.put("userId", idTeacher );
        ModelAndView mv = new ModelAndView("redirect:/teacher", model);
        return mv;
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