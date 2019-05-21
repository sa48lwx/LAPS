package com.example.portal.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.example.portal.model.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

	//ADMIN- Query to return the available Managers to map to the employee
	@Query("select user from User user where user.employeediv ='Manager' ")
    @Transactional(readOnly = true)
    List<User> findManagerList();
}


