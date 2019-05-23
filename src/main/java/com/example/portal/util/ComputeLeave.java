package com.example.portal.util;

import java.sql.Date;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.example.portal.model.Holiday;
import com.example.portal.repo.HolidayRepository;



public class ComputeLeave {
	
		private Date start_date;
		private Date end_date;
		private float difference;
		private float leave_period;
		private int excludedDays;
		private ArrayList<Date> holidays;
		private HolidayRepository hRepo;
		
		
		@Autowired
		public void sethRepo(HolidayRepository hRepo) {
			this.hRepo = hRepo;
		}

		public ComputeLeave(Date start_date, Date end_date) {
			super();
			this.start_date = start_date;
			this.end_date = end_date;
			List<Holiday> holidays_list = hRepo.findAll();
			ArrayList<Date> holidays = new ArrayList<Date>();
			for(Holiday h : holidays_list) {
				holidays.add(h.getDate());
			}
			this.calculateDiff();
			this.calculateExcludedDays();
			this.difference -= this.excludedDays;
		}
		
		public ComputeLeave() {
			super();
			// TODO Auto-generated constructor stub
		}
		
		
		public Date getStart_date() {
			return start_date;
		}

		public void setStart_date(Date start_date) {
			this.start_date = start_date;
		}

		public Date getEnd_date() {
			return end_date;
		}

		public void setEnd_date(Date end_date) {
			this.end_date = end_date;
		}

		public float getDifference() {
			return difference;
		}

		public void setDifference(float difference) {
			this.difference = difference;
		}

		public float getLeave_period() {
			return leave_period;
		}

		public void setLeave_period(float leave_period) {
			this.leave_period = leave_period;
		}

		public int getExcludedDays() {
			return excludedDays;
		}

		public void setExcludedDays(int excludedDays) {
			this.excludedDays = excludedDays;
		}

		public ArrayList<Date> getHolidays() {
			return holidays;
		}

		public void setHolidays(ArrayList<Date> holidays) {
			this.holidays = holidays;
		}

		private void calculateDiff() {
			long diff = start_date.getTime() - end_date.getTime();
			int diffDays = (int) (diff / (24 * 60 * 60 * 1000));
			this.difference = (float) diffDays;
		}
		
		@SuppressWarnings("deprecation")
		private void calculateExcludedDays() {
			int c = 0;
			for(int i = 0; i <= this.difference; i++) {
				if(holidays.stream().map(a -> a.toString().split("T")[0]).anyMatch(x -> x.equals(start_date.toString().split("T")[0]))) {
					System.out.println("Is holiday!");
					c++;
				} else if (start_date.getDay() > 5) {
					c++;
				}
				Calendar cal = Calendar.getInstance();
				cal.setTime(start_date);
				cal.add(Calendar.DATE, 1);
				start_date = (Date) cal.getTime();
			}
			this.excludedDays = c;
		}
		
	}
