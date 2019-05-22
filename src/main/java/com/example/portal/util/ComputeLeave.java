package com.example.portal.util;

import java.sql.Date;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;



public class ComputeLeave {
	
		private LocalDateTime start_date;
		private LocalDateTime end_date;
		private float difference;
		private float leave_period;
		private int excludedDays;
		private ArrayList<Date> holidays;
		
		public ComputeLeave(LocalDateTime start_date, LocalDateTime end_date, ArrayList<Date> holidays) {
			super();
			this.start_date = start_date;
			this.end_date = end_date;
			this.holidays = holidays;
			this.calculateDiff();
			this.calculateExcludedDays();
			this.difference -= this.excludedDays;
			if(isHalfDay(start_date)) {
				this.difference -= 0.5f;
			}
			if(! isHalfDay(end_date)) {
				this.difference -= 0.5f;
			}
		}
		
		public ComputeLeave() {
			super();
			// TODO Auto-generated constructor stub
		}
		@Override
		public String toString() {
			return "LeaveDateTime [start_date=" + start_date + ", end_date=" + end_date + ", difference=" + difference
					+ ", leave_period=" + leave_period + "]";
		}
		public LocalDateTime getStart_date() {
			return start_date;
		}
		public void setStart_date(LocalDateTime start_date) {
			this.start_date = start_date;
		}
		public LocalDateTime getEnd_date() {
			return end_date;
		}
		public void setEnd_date(LocalDateTime end_date) {
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
		
		private void calculateDiff() {
			Duration duration = Duration.between(start_date, end_date);
			long d = duration.toDays();
			if(start_date.getHour() > end_date.getHour())
				d += 1;
			this.difference = (float) d;
		}
		
		private void calculateExcludedDays() {
			int c = 0;
			for(int i = 0; i <= this.difference; i++) {
				if(holidays.stream().map(a -> a.toString().split("T")[0]).anyMatch(x -> x.equals(start_date.toString().split("T")[0]))) {
					System.out.println("Is holiday!");
					c++;
				} else if (start_date.getDayOfWeek().getValue() > 5) {
					c++;
				}
				start_date = start_date.plusDays(1);
			}
			this.excludedDays = c;
		}
		
		private boolean isHalfDay(LocalDateTime d) {
			return d.getHour() >= 12;
		}
	}
