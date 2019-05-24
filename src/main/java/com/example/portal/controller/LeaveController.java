package com.example.portal.controller;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
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
import com.example.portal.model.Leave;
import com.example.portal.model.User;
import com.example.portal.repo.HolidayRepository;
import com.example.portal.repo.LeaveRepository;
import com.example.portal.repo.UserRepository;
import com.example.portal.service.LeaveServiceIF;
import com.example.portal.util.ComputeLeave;

@Controller
public class LeaveController implements LeaveServiceIF{
	private UserRepository uRepo;
	private LeaveRepository lRepo;
	private HolidayRepository hRepo;
	@Autowired
	public void setlRepo(LeaveRepository lRepo) {
		this.lRepo = lRepo;
	}

	@Autowired
	public void setmRepo(UserRepository uRepo) {
		this.uRepo = uRepo;
	}
	
	@Autowired
	public void setmRepo(HolidayRepository hRepo) {
		this.hRepo = hRepo;
	}
	
	//Apply Leave HTML
	@RequestMapping(path = "/leaves/add", method = RequestMethod.GET)
    public String createLeave(Model model) {
        model.addAttribute("leave", new Leave());
        return "applyleave";
    }
	
	//View Leave HTML
	@RequestMapping(path = "/leaves", method = RequestMethod.GET)
    public String getAllLeave(Model model) {
    	
    	ArrayList<Leave> plist = (ArrayList<Leave>) lRepo.findAll();
 		model.addAttribute("leavelist",plist);
     
        return "viewleave";
    } 
	@RequestMapping(path = "/leaves", method = RequestMethod.POST)
    public String saveLeave(@Valid Leave l, BindingResult bindingResult, Model model) {
    	if (bindingResult.hasErrors()) {
            return "applyleave";
        }
    	l=SaveLeave(l);
    	ArrayList<Leave> plist = (ArrayList<Leave>) lRepo.findAll();
    	model.addAttribute("leave_period", l.getDuration());
	 	model.addAttribute("leavelist", plist);
        return "viewleave";
    }
	/*
	 * @RequestMapping(path = "/leaves/viewform", method = RequestMethod.POST)
	 * public String saveLeave(@Valid Leave l, BindingResult bindingResult, Model
	 * model) { if (bindingResult.hasErrors()) { return "redirect:addform"; }
	 * 
	 * l=SaveLeave(l); ArrayList<Leave> plist = (ArrayList<Leave>) lRepo.findAll();
	 * model.addAttribute("leavelist", plist);
	 * 
	 * return "leave"; }
	 */
	
	// Update leave HTML
	@RequestMapping(path = "/leaves/edit/{id}", method = RequestMethod.GET)
    public String updateLeave( @PathVariable(value = "id") int id,Leave l,Model model) {   	
    	l = lRepo.findById(id).orElse(null);
    	System.out.println(l);
    	  lRepo.save(l);
        model.addAttribute("leaves", l);
        return "editleave";
    }

	/*
	 * @RequestMapping(path = "/leaves/editform/{id}", method = RequestMethod.GET)
	 * public String editLeave( @PathVariable(value = "id") int id,@Valid Leave
	 * l,Model model) { l = lRepo.findById(id).orElse(null); System.out.println(l);
	 * lRepo.save(l); model.addAttribute("leaves", l); return "leaveform"; }
	 */
	/*
	 * @RequestMapping(path = "/leaves/editform/{id}", method = RequestMethod.POST)
	 * public String updateLeave( @PathVariable(value = "id") String id,@Valid Leave
	 * l,Model model) {
	 * 
	 * l=SaveLeave(l); ArrayList<Leave> plist = (ArrayList<Leave>) lRepo.findAll();
	 * model.addAttribute("leavelist", plist);
	 * 
	 * return "redirect:/leaves/viewform"; }
	 */
	@RequestMapping(path = "/leaves/edit/{id}", method = RequestMethod.POST)
	public String updateLeave(@PathVariable(value = "id") String id, @Valid Leave l,
			BindingResult bindingResult, Model model) {

		if (bindingResult.hasErrors()) {
			return "applyleave";
		}
		l=SaveLeave(l);
		ArrayList<Leave> plist = (ArrayList<Leave>) lRepo.findAll();
		model.addAttribute("leavelist", plist);

		return "viewleave";
	}
	//Delete Leave
	@RequestMapping(path = "/leaves/delete/{id}", method = RequestMethod.GET)
    public String deleteLeave(@PathVariable(name = "id") int id) {
    	lRepo.delete(lRepo.findById(id).orElse(null));
        return "viewleave";
    }
	//Manager Leave
	@RequestMapping(path = "/approveleave", method = RequestMethod.GET)
    public String getPendingLeaves(Model model) {
    	 ArrayList<Leave> plist = (ArrayList<Leave>) lRepo.findAllPendingLeave();
 		model.addAttribute("leavelist", plist);
        return "approveleave";
    }
	
	//approve leave
	@RequestMapping(path = "/leaves/edit/managerview/{id}", method = RequestMethod.GET)
	  public String updateleaves( @PathVariable(value = "id") int id,Leave l,Model model) {   	
	    	
		Optional<User> u = uRepo.findById(l.getEmployeeId());
		if(u.isPresent()) {
			System.out.println(u.get());
			User user = u.get();
			double now = user.getLeaveentitled();
			now -= l.getDuration();
			user.setLeaveentitled(now);
			uRepo.save(user);
			System.out.println(user);
		}
		l = lRepo.findById(id).orElse(null);
	    	  lRepo.save(l);
	        model.addAttribute("leaves", l);
	        return "updateLeave";
	    }
	/**
	 * @RequestMapping(path = "/leaves/editform/{id}", method = RequestMethod.POST)
	 *                      public String updateLeave( @PathVariable(value = "id")
	 *                      String id,@Valid Leave l,Model model) {
	 * 
	 *                      l=SaveLeave(l); ArrayList<Leave> plist =
	 *                      (ArrayList<Leave>) lRepo.findAll();
	 *                      model.addAttribute("leavelist", plist);
	 * 
	 *                      return "redirect:/leaves/viewform"; }
	 */
	
	//Compensation
	
	//note hardcoded, edit to /claimcompensation/{employeeid} 
    @RequestMapping(path = "/claimcompensation", method = RequestMethod.GET)
    public String EditLeave(User user,Model model, Leave leave) { 
    	long employeeid = 48;
    	user = uRepo.findById(employeeid).orElse(null);
    	System.out.println(user );
    	uRepo.save(user );
        model.addAttribute("user", user);
        Leave l = new Leave();
        model.addAttribute("Leave",l);
        return "claimcompensation";
    }
	  @RequestMapping(path = "/approvecompensation", method = RequestMethod.GET)
	    public String getPendingCompensation(Model model) {
	    	 ArrayList<Leave> plist = (ArrayList<Leave>) lRepo.findAllPendingCompensationLeave();
	 		model.addAttribute("leavelist", plist);
	     
	        return "approvecompensation";
	    }
	  @RequestMapping(path = "/compleaves/edit/managerview/{id}", method = RequestMethod.GET)
	  public String updateCompensation( @PathVariable(value = "id") int id,Leave l,Model model) {   	
	    	l = lRepo.findById(id).orElse(null);
	    	System.out.println(l);
	    	  lRepo.save(l);
	        model.addAttribute("leaves", l);
	        return "updateleave";
	    }
	
	
	@Override
	public Leave SaveLeave(Leave l) {
		
		List<Holiday> hols = hRepo.findAll();
    	ArrayList<Date> holidays = (ArrayList<Date>) hols.stream().map(a -> a.getDate()).collect(Collectors.toList());
    	ComputeLeave ldt = new ComputeLeave(l.getFromDate(), l.getToDate(), holidays);
    	double diff = ldt.getDifference();
    	l.setDuration(diff);
    	lRepo.save(l);
        return l;
	}
}
