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
import org.springframework.web.servlet.ModelAndView;
 
import com.kalbark0.edziennik.model.ErrorMessages;
import com.kalbark0.edziennik.model.NewGradeQuery;
import com.kalbark0.edziennik.model.Query;
 
@Controller
@RequestMapping(value = {"/login"})
public class LoginController {
    
    @GetMapping()
    public String login(Model model) {
        Query query = new Query();
        model.addAttribute("query", query);
        return "login";
    }
    
    @PostMapping 
    public ModelAndView loginPost(@ModelAttribute("query") Query query) throws SQLException {
        String login = query.getLogin();
        String password = query.getPassword();
        
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost/edziennik", "root", "admin");
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("select * from user where nick_user='" + login
                    + "' and password_user='" + password + "'");
            if (resultSet.next()) {
                String userId = resultSet.getString(1);
                String job = resultSet.getString(4);
                if(job.equals("uczeń")) {
	                Map<String, Object> model = new HashMap<>();
	                model.put("userId", userId );
	                ModelAndView mv = new ModelAndView("redirect:/student", model);
	                return mv;
                }
                else if(job.equals("rodzic")) {
	                Map<String, Object> model = new HashMap<>();
	                String id_child = null;
	                ResultSet parent = statement.executeQuery("select * from parent where id_parent='" + userId + "'");
	                while(parent.next()) {
	                	id_child = parent.getString("id_student");	
	                }
	                model.put("userId", id_child);
	                ModelAndView mv = new ModelAndView("redirect:/parent", model);
	                return mv;
                }
                else if(job.equals("nauczyciel")) {
	                Map<String, Object> model = new HashMap<>();
	                model.put("userId", userId );
	                ModelAndView mv = new ModelAndView("redirect:/teacher", model);
	                return mv;
                }
                else if(job.equals("admin")) {   
                    Map<String, Object> model = new HashMap<>();
                    model.put("userId", userId);
                    ModelAndView mv = new ModelAndView("redirect:/admin", model);
                    return mv;
                }
            } else {
                Map<String, Object> model = new HashMap<>();
                model.put("message", ErrorMessages.INVALID_LOGIN);
                ModelAndView mv = new ModelAndView("redirect:/error", model);
                return mv;
            }
        
        return null;
    }
}
