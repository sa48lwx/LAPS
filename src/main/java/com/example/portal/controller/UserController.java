package com.example.portal.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.portal.model.User;
import com.example.portal.repo.UserRepository;

// This controller contains all the admin CRUD functions for Employee Data
//test 2nd commit
@Controller
public class UserController {
	
	
	private UserRepository userRepository;
	
	@Autowired
	public void setUserRepository(UserRepository userRepository) {
		this.userRepository = userRepository;
	}

	//ADMIN- Controller for Home Page
	@RequestMapping("/home")
    public String userhome() {
		 
		return "homePage";
    }
	//ADMIN- Controller for View Page
	
	@RequestMapping("/home/viewEmployee")
    public String viewemployee(Model model) {
		 model.addAttribute("users", userRepository.findAll());
		return "viewEmployee";
    }
	
	//ADMIN- Controller for Adding Employee
	@GetMapping("/home/addEmployee")
    public String homesendForm(User user,Model model) {
		model.addAttribute("managers", userRepository.findManagerList());
		return "addEmployee";
    }

	    
    @PostMapping("/home/addEmployee")
    public String processForm(@Valid User user, BindingResult result, Model model) {
        if (result.hasErrors()) {
            return "error";
        }
         
        userRepository.save(user);
        model.addAttribute("users", userRepository.findAll());
        
        	return "showMessage";
    }
    //ADMIN- Controller for Edit Employee
    @GetMapping(path="/home/edit/{employeeid}")
    public String EditUser(@PathVariable("employeeid") long employeeid ,@Valid User user,Model model,BindingResult result) {
       
    	 user = userRepository.findById(employeeid).orElseThrow(() -> new IllegalArgumentException("Invalid user Id:" + employeeid));
    	 model.addAttribute("user", user);
       
    	 return "updateEmployee";
    }

    @PostMapping(path="/home/edit/{employeeid}")
    public String UpdateEditUser(@PathVariable("employeeid") long employeeid ,@Valid User user,Model model,BindingResult result)
    {
        
        	 userRepository.save(user);
             model.addAttribute("users", userRepository.findAll());
             return "redirect:/home/viewEmployee" ;
        
        
     }
    //ADMIN- Delete Controller for employee
    
    @GetMapping("/home/delete/{employeeid}")
    public String deleteUser(@PathVariable("employeeid") long employeeid, Model model,User user) {
        user = userRepository.findById(employeeid)
          .orElseThrow(() -> new IllegalArgumentException("Invalid user Id:" + employeeid));
        userRepository.delete(user);
        model.addAttribute("user", userRepository.findAll());
        return "redirect:/home/viewEmployee";
    }
    
}

