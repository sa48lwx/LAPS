package com.example.portal.controller;


import java.util.ArrayList;
import com.example.portal.validator.LeaveValidator;
import javax.validation.Valid;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import  com.example.portal.model.Leave;
import  com.example.portal.repo.LeaveRepository;
import  com.example.portal.repo.UserRepository; 

@Controller
public class AdminController {
	private UserRepository mRepo;
	private LeaveRepository lRepo;
	@Autowired
	public void setlRepo(LeaveRepository lRepo) {
		this.lRepo = lRepo;
	}

	@Autowired
	public void setmRepo(UserRepository mRepo) {
		this.mRepo = mRepo;
	}

	@RequestMapping(path="/")
	public String Index()
	{
		return "index";
	}
	@RequestMapping(path = "/leaves/add", method = RequestMethod.GET)
    public String createLeave(Model model) {
        model.addAttribute("leave", new Leave());
        return "edit";
    }
	  @RequestMapping(path = "/leaves/createLeave", method = RequestMethod.POST)
	    public String savenewLeave(@Valid Leave l, BindingResult bindingResult, Model model) {
	    	if (bindingResult.hasErrors()) {
	            return "edit";
	        }
	  
	        lRepo.save(l);
	    	ArrayList<Leave> plist = (ArrayList<Leave>) lRepo.findAll();
		 	model.addAttribute("leavelist", plist);
	       
	
	        return "createleave";
	    }
	    @RequestMapping(path = "/leaves/createLeave", method = RequestMethod.GET)
	    public String getLeave(Model model) {

	 		model.addAttribute("leavelist", new Leave());
	     
	        return "createleave";
	    } 
	
	  @RequestMapping(path = "/leaves", method = RequestMethod.POST)
	    public String saveLeave(@Valid Leave l, BindingResult bindingResult, Model model) {
	    	if (bindingResult.hasErrors()) {
	            return "edit";
	        }
	  
	        lRepo.save(l);
	    	ArrayList<Leave> plist = (ArrayList<Leave>) lRepo.findAll();
		 	model.addAttribute("leavelist", plist);
	       
	
	        return "leave";
	    }
	    @RequestMapping(path = "/leaves", method = RequestMethod.GET)
	    public String getAllLeave(Model model) {
	    	 ArrayList<Leave> plist = (ArrayList<Leave>) lRepo.findAll();
	 		model.addAttribute("leavelist", plist);
	     
	        return "leave";
	    } 
	    @RequestMapping(path = "/leaves/edit/{id}", method = RequestMethod.GET)
	    public String editLeave( @PathVariable(value = "id") String id,@Valid Leave l,Model model) {   	
	    	l = lRepo.findById(id).orElse(null);
	    	System.out.println(l);
	    	  lRepo.save(l);
	        model.addAttribute("leaves", l);
	        return "update";
	    }
	    @RequestMapping(path = "/leaves/edit/{id}", method = RequestMethod.POST)
	    public String updateLeave( @PathVariable(value = "id") String id,@Valid Leave l,Model model) {   	
	    	
	    	  lRepo.save(l);
	    	  ArrayList<Leave> plist = (ArrayList<Leave>) lRepo.findAll();
			 	model.addAttribute("leavelist", plist);
		       
		
		        return "leave";
	    }
	    @RequestMapping(path = "/leaves/delete/{id}", method = RequestMethod.GET)
	    public String deleteLeave(@PathVariable(name = "id") String id) {
	    	lRepo.delete(lRepo.findById(id).orElse(null));
	        return "redirect:/leaves";
	    }





	
	  
}
