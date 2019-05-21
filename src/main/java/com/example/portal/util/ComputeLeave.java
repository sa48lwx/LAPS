package com.example.portal.util;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.example.portal.repo.HolidayRepository;

public class ComputeLeave {
	
	private HolidayRepository hRepo;
	
	@Autowired
	public ComputeLeave(HolidayRepository hRepo) {
		super();
		this.hRepo = hRepo;
	}

	public long Compute(LocalDateTime start_date, LocalDateTime end_date) {
		long days = getNumberOfDays(start_date, end_date);
		if(days > 14) {
			return days;
		} else {
			int c = 0;
			for(int i = 0; i < days; i++) {
				LocalDateTime checkDate = start_date.plusDays(i);
				if(this.isHoliday(checkDate)) {
					c++;
				}
				days -= c;
			}
		}
		return days;
	}
	
	public long getNumberOfDays(LocalDateTime start_date, LocalDateTime end_date) {
		long days = Math.abs(start_date.compareTo(end_date));
		return days;
	}
	
	
	public Boolean isHoliday(LocalDateTime date) {
		List<LocalDateTime> Holidays = hRepo.findAllHolidays();
		if(Holidays.contains(date)) {
			return true;
		}
		return false;
	}
	
	
	public Boolean isWorkDay(LocalDateTime date) {
		int day = date.getDayOfWeek().getValue();
		if((day >= 1) || (day <= 5)) {
			return true;
		}
		return false;
	}
}
