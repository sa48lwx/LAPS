package com.example.portal.repo;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.portal.javabeans.Holiday;

public interface HolidayRepository extends JpaRepository<Holiday, Integer>{
	public List<LocalDateTime> findAllHolidays();
}
