/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mindfire.controller;

import com.mindfire.DTO.UserDTO;
import com.mindfire.model.User;
import com.mindfire.service.UserService;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.validation.Valid;
import org.mindrot.jbcrypt.BCrypt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpRequest;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

/**
 *
 * @author anants
 */
@RestController
public class UserController {
    @Autowired
    UserService userService;
    @RequestMapping("/")
    public ModelAndView showform() {
        return new ModelAndView("index");
    }
    
    @RequestMapping(value = "/login")
    public UserDTO getStatus(String email, String password) {
        User us = userService.getUser(email);
        UserDTO result = new UserDTO();
        result.setCode("200");
        result.setMessage("Valid User");
        result.setStatus("successful");
        result.setUser(us);
        return result;
    }
    
    @RequestMapping(value = "/signup", method = RequestMethod.POST)
    public UserDTO save(@ModelAttribute("user") User user) {
        //encrypting password using bcrypt
        String pass = BCrypt.hashpw(user.getPassword(), BCrypt.gensalt());
        System.out.println("pass   "+pass);
        user.setPassword(pass);
        userService.saveUser(user);
        UserDTO result = new UserDTO();
        result.setCode("200");
        result.setMessage("Valid User");
        result.setStatus("successful");
        result.setUser(user);
        return result; 
    }
//    @RequestMapping(value = "/edit", method = RequestMethod.GET)
//    public RestWrapperDTO getEmployeeInJSON() {
//        RestWrapperDTO wrapperDTO = new RestWrapperDTO();
//        wrapperDTO.setSuccess("Successful");
//        return wrapperDTO;
//    }
//    
//   @RequestMapping(value = "/search")
//    public SearchCriteria getSearch() {
//        SearchCriteria result = new SearchCriteria();
//        result.setEmail("anant@gmail.com");
//        result.setUsername("anant");
//        return result;
//    }
//    

}
