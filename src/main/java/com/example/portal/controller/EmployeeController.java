package com.example.portal.controller;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.example.portal.model.Holiday;
import  com.example.portal.model.Leave;
import com.example.portal.model.User;
import com.example.portal.repo.HolidayRepository;
import  com.example.portal.repo.LeaveRepository;
import  com.example.portal.repo.UserRepository;
import com.example.portal.service.LeaveServiceIF;
import com.example.portal.util.ComputeLeave;


@Controller
public class EmployeeController implements LeaveServiceIF{
	private UserRepository mRepo;
	private LeaveRepository lRepo;
	private HolidayRepository hRepo;
	
	@Autowired
	public void sethRepo(HolidayRepository hRepo) {
		this.hRepo = hRepo;
	}

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
	    	if (bindingResult.hasErrors()) {
	            return "redirect:addform";
	        }
	    	
	        l=SaveLeave(l);
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
	    public String editLeave( @PathVariable(value = "id") int id,@Valid Leave l,Model model) {   	
	    	l = lRepo.findById(id).orElse(null);
	    	System.out.println(l);
	    	  lRepo.save(l);
	        model.addAttribute("leaves", l);
	        return "leaveform";
	    }
	    @RequestMapping(path = "/leaves/editform/{id}", method = RequestMethod.POST)
	    public String updateLeave( @PathVariable(value = "id") String id,@Valid Leave l,Model model) {   	
	    	
	    	  l=SaveLeave(l);
	    	  ArrayList<Leave> plist = (ArrayList<Leave>) lRepo.findAll();
			 	model.addAttribute("leavelist", plist);
		       
			     return "redirect:/leaves/viewform";
	    }
	    @RequestMapping(path = "/leaves/deleteform/{id}", method = RequestMethod.GET)
	    public String deleteLeave(@PathVariable(name = "id") int id,Model m,Leave l) {
	    	lRepo.delete(lRepo.findById(id).orElse(null));
	    	m.addAttribute("leaves", l);
	        return "redirect:/leaves/viewform";
	    }
	    //note hardcoded, edit to /claimcompensation/{employeeid} 
	    @RequestMapping(path = "/claimcompensation", method = RequestMethod.GET)
	    public String EditLeave(User user,Model model, Leave leave) { 
	    	long employeeid = 2;
	    	user = mRepo.findById(employeeid).orElse(null);
	    	System.out.println(user );
	    	mRepo.save(user );
	        model.addAttribute("user", user);
	        Leave l = new Leave();
	        model.addAttribute("Leave",l);
	        return "compensationform";
	    }
		  @RequestMapping(path = "/approvecompensation", method = RequestMethod.GET)
		    public String getPending(Model model) {
		    	 ArrayList<Leave> plist = (ArrayList<Leave>) lRepo.findAllPendingCompensationLeave();
		 		model.addAttribute("leavelist", plist);
		     
		        return "approvecompensation";
		    }
		  @RequestMapping(path = "/leaves/edit/managerview/{id}", method = RequestMethod.GET)
		  public String updateCompensation( @PathVariable(value = "id") int id,Leave l,Model model) {   	
		    	l = lRepo.findById(id).orElse(null);
		    	System.out.println(l);
		    	  lRepo.save(l);
		        model.addAttribute("leaves", l);
		        return "updateCompensation";
		    }

		@Override
		public Leave SaveLeave(Leave l) {
			System.out.println("IN, we reached here1");
			List<Holiday> hols = hRepo.findAll();
			System.out.println("IN, we reached here2");
	    	ArrayList<Date> holidays = (ArrayList<Date>) hols.stream().map(a -> a.getDate()).collect(Collectors.toList());
	    	System.out.println("IN, we reached here3");
	    	System.out.println(l.getToDate());
	    	ComputeLeave ldt = new ComputeLeave(l.getFromDate(), l.getToDate(), holidays);
	    	System.out.println("IN, we reached here4");
	    	double diff = ldt.getDifference();
	    	System.out.println("IN, we reached here5");
	    	l.setDuration(diff);
	    	System.out.println("IN, we reached here6");
	    	lRepo.save(l);
	        return l;
		} 
		
}
