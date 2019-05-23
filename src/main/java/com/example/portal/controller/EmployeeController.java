package com.example.portal.controller;
import java.sql.Date;
import java.util.ArrayList;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import  com.example.portal.model.Leave;
import com.example.portal.model.User;
import  com.example.portal.repo.LeaveRepository;
import  com.example.portal.repo.UserRepository;
import com.example.portal.util.ComputeLeave;


@Controller
public class EmployeeController {
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

	@RequestMapping(path="/staff")
	public String Index()
	{
		return "homeEmployee";
	}
	@RequestMapping(path="/manager")
	public String In()
	{
		return "homeManager";
	}
	@RequestMapping(path = "/leaves/addform", method = RequestMethod.GET)
    public String createLeave(Model model) {
        model.addAttribute("leave", new Leave());
        return "leaveform";
    }
	  @RequestMapping(path = "/leaves/viewform", method = RequestMethod.POST)
	    public String saveLeave(@Valid Leave l, BindingResult bindingResult, Model model) {
		  	Date start_date = l.getFromDate();
		    Date end_date = l.getToDate();
		    ComputeLeave cl = new ComputeLeave(start_date, end_date);
	    	if (bindingResult.hasErrors()) {
	            return "redirect:addform";
	        }
	  
	        lRepo.save(l);
	    	ArrayList<Leave> plist = (ArrayList<Leave>) lRepo.findAll();
		 	model.addAttribute("leavelist", plist);
	
	        return "leave";
	    }
	  @RequestMapping(path = "/leaves/viewform", method = RequestMethod.GET)
	    public String getAllLeave(Model model) {
	    	 ArrayList<Leave> plist = (ArrayList<Leave>) lRepo.findAll();
	 		model.addAttribute("leavelist", plist);
	     
	        return "viewleaveform";
	    } 
	    @RequestMapping(path = "/leaves/editform/{id}", method = RequestMethod.GET)
	    public String editLeave( @PathVariable(value = "id") String id,@Valid Leave l,Model model) {   	
	    	l = lRepo.findById(id).orElse(null);
	    	System.out.println(l);
	    	  lRepo.save(l);
	        model.addAttribute("leaves", l);
	        return "leaveform";
	    }
	    @RequestMapping(path = "/leaves/editform/{id}", method = RequestMethod.POST)
	    public String updateLeave( @PathVariable(value = "id") String id,@Valid Leave l,Model model) {   	
	    	
	    	  lRepo.save(l);
	    	  ArrayList<Leave> plist = (ArrayList<Leave>) lRepo.findAll();
			 	model.addAttribute("leavelist", plist);
		       
			     return "redirect:/leaves/viewform";
	    }
	    @RequestMapping(path = "/leaves/deleteform/{id}", method = RequestMethod.GET)
	    public String deleteLeave(@PathVariable(name = "id") String id,Model m,Leave l) {
	    	lRepo.delete(lRepo.findById(id).orElse(null));
	    	m.addAttribute("leaves", l);
	        return "redirect:/leaves/viewform";
	    }
	    //note hardcoded, edit to /claimcompensation/{employeeid} 
	    @RequestMapping(path = "/claimcompensation", method = RequestMethod.GET)
	    public String EditLeave( @Valid User user,Model model, Leave leave) { 
	    	long employeeid = 2;
	    	user = mRepo.findById(employeeid).orElse(null);
	    	System.out.println(user );
	    	mRepo.save(user );
	        model.addAttribute("user", user);
	        Leave l = new Leave();
	        model.addAttribute("Leave",l);
	        return "compensationform";
	    }
	  

}
