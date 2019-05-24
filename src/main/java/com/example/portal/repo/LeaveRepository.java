package com.example.portal.repo;
import java.util.Collection;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import  com.example.portal.model.Leave;


@Repository
public interface LeaveRepository extends JpaRepository<Leave,Integer> {
	@Query(
			  value = "SELECT * FROM leave_app where status='Pending' AND leave_type='Compensation'", 
			  nativeQuery = true)
			Collection<Leave> findAllPendingCompensationLeave();
	
<<<<<<< HEAD
=======
	@Query(
			  value = "SELECT * FROM leave_app where status='Pending' AND leave_type<>'Compensation'", 
			  nativeQuery = true)
			Collection<Leave> findAllPendingLeave();
>>>>>>> branch 'master' of https://github.com/sa48lwx/LAPS.git

	List<Leave> findAllByEmployeeId(Long Id);
}

