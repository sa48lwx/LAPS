package com.example.portal.validator;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import com.example.portal.model.Leave;



@Component
public class LeaveValidator implements Validator {
	@Override
	public boolean supports(Class<?> arg0) {
		return Leave.class.isAssignableFrom(arg0);

	}

	@Override
	public void validate(Object arg0, Errors arg1) {
		Leave l = (Leave) arg0;	
		if ((l.getFromDate()!=null && l.getToDate()!=null)&&(l.getFromDate().compareTo(l.getToDate()) > 0)) {
			arg1.reject("toDate", "End date should be greater than start date.");
			arg1.rejectValue("toDate", "error.dates", "to date must be > from date");
	
		}
		ValidationUtils.rejectIfEmptyOrWhitespace(arg1, "leave_type", "error.leave_type", "Leave Type is required.");
		ValidationUtils.rejectIfEmptyOrWhitespace(arg1, "fromDate", "error.fromDate", "From Date is required.");
		ValidationUtils.rejectIfEmptyOrWhitespace(arg1, "toDate", "error.toDate", "To Date is required.");
	}

}
