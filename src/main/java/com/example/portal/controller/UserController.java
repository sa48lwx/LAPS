package com.example.portal.controller;

import java.util.ArrayList;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import  com.example.portal.model.Leave;
import  com.example.portal.model.User;
import  com.example.portal.repo.LeaveRepository;
import  com.example.portal.repo.UserRepository;

import com.example.portal.model.Holiday;

import com.example.portal.repo.HolidayRepository;

@Controller
public class UserController {
	
	
	private UserRepository userRepository;
	private HolidayRepository hRepo;
	
	@Autowired
	public void setUserRepository(UserRepository userRepository) {
		this.userRepository = userRepository;
	}
    public HolidayRepository gethRepo() {
			return hRepo;
		}
	@Autowired
		public void sethRepo(HolidayRepository hRepo) {
			this.hRepo = hRepo;
		}
	//ADMIN- Controller for Home Page
	@RequestMapping("/home")
    public String userhome() {
		 
		return "homePage";
    }
	//ADMIN- Controller for View Page
	

	   @RequestMapping(path = "/home/viewEmployee", method = RequestMethod.GET)
	    public String viewemployee(Model model) {
		   ArrayList<User> plist = (ArrayList<User>)userRepository.findAll();
		 	model.addAttribute("users", plist);
		 	return "viewEmployee";
	    } 
	
	//ADMIN- Controller for Adding Employee
	@GetMapping("/home/addEmployee")
    public String homesendForm(User user,Model model) {
		model.addAttribute("managers", userRepository.findAll());
		return "addEmployee";
    }

	    
	/*
	 * @PostMapping("/home/addEmployee")public String processForm(@Valid User user,
	 * BindingResult result, Model model) { if (result.hasErrors()) {
	 * model.addAttribute("managers", userRepository.findAll()); return
	 * "addEmployee"; }
	 * 
	 * userRepository.save(user); model.addAttribute("users",
	 * userRepository.findAll());
	 * 
	 * return "showMessage"; }
	 */
	 @PostMapping("/home/addEmployee")
	    public String processForm(@Valid User user, BindingResult result, Model model) {
	        if (result.hasErrors()) {
	        	model.addAttribute("managers", userRepository.findAll());
	            return "addEmployee";
	        }
	         
	        userRepository.save(user);
	        model.addAttribute("users", userRepository.findAll());
	        
	        	return "showMessage";
	    }
    //ADMIN- Controller for Edit Employee

    @RequestMapping(path = "/home/edit/{employeeid}", method = RequestMethod.GET)
    public String EditUser( @PathVariable(value = "employeeid") long employeeid,@Valid User user,Model model) {   	
    	user = userRepository.findById(employeeid).orElse(null);
    	System.out.println(user );
    	userRepository.save(user );
        model.addAttribute("user", user);
        return "updateEmployee";
    }
    @RequestMapping(path = "/home/edit/{employeeid}", method = RequestMethod.POST)
    public String updateUser( @PathVariable(value = "employeeid") long employeeid,@Valid User user,Model model) {   	
    	
    	userRepository.save(user);
    	  ArrayList<User> plist = (ArrayList<User>) userRepository.findAll();
		 	model.addAttribute("users", plist);
		 	 return "redirect:/home/viewEmployee" ;
	        
    }
    //ADMIN- Controller for Edit holiday

    @RequestMapping(path = "/home/editholiday/{id}", method = RequestMethod.GET)
    public String editHoliday( 
       @PathVariable(name = "id") long id, Model model,@Valid Holiday h) {
		h= hRepo.findById(id).orElse(null);
	         hRepo.save(h);
	     
			 	model.addAttribute("holiday",h);
	    	  return "addHoliday";
    }
	
    @RequestMapping(path = "/home/editholiday/{id}", method = RequestMethod.POST)
    public String updateHoliday( 
       @PathVariable(name = "id") long id, Model model,@Valid Holiday h) {
	    
	         hRepo.save(h);
	         ArrayList<Holiday> plist = (ArrayList<Holiday>) hRepo.findAll();
			 	model.addAttribute("holiday", plist);
	    	  return "redirect:/home/viewholiday";
    }
    
    
  
 //ADMIN-create holiday list
    @RequestMapping(path = "/home/addholiday", method = RequestMethod.GET)
    public String createLeave(Model model) {
        model.addAttribute("holiday", new Holiday());
        return "addHoliday";
    }


	  @RequestMapping(path = "/home/addholiday", method = RequestMethod.POST)
	    public String saveLeave(@Valid  Holiday h, BindingResult bindingResult, Model model) {
	    	if (bindingResult.hasErrors()) {
	    		 return "addHoliday";
	        }
	  
	  	  hRepo.save(h);
    	  ArrayList<Holiday> plist = (ArrayList<Holiday>) hRepo.findAll();
		 	model.addAttribute("holiday", plist);
	       
	
		 	  return "viewHoliday";
	    }
	    @RequestMapping(path = "/home/viewholiday", method = RequestMethod.GET)
	    public String viewHoliday( Model model) {
	 
	  	  
    	  ArrayList<Holiday> plist = (ArrayList<Holiday>) hRepo.findAll();
		 	model.addAttribute("holiday", plist);
	       
	
		 	  return "viewHoliday";
	
	    }
	  

	  
	    //admin- delete for holiday

	    
	    @RequestMapping(path = "/home/deleteholiday/{id}", method = RequestMethod.GET)
	    public String deleteHoliday(@PathVariable(name = "id") long  id,Holiday h,Model m) {
	    	 hRepo.delete(  hRepo.findById(id).orElse(null));
	    	 m.addAttribute("holiday", hRepo.findAll());
	    	 return "viewHoliday";
	    }

    //ADMIN- Delete Controller for employee
    
    @RequestMapping(path = "/home/delete/{employeeid}", method = RequestMethod.GET)
    public String deleteLeave(@PathVariable(name = "employeeid") long employeeid, Model model,User user) {
    	 userRepository.delete( userRepository.findById(employeeid).orElse(null));
         model.addAttribute("user", userRepository.findAll());
    	  return "redirect:/home/viewEmployee";
    }
   
}



