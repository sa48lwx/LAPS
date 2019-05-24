package com.example.portal.javabean;

import org.springframework.beans.factory.annotation.Autowired;

import com.example.portal.model.User;

public class UserSession {
	
	private User user;


	@Autowired
	public void setUser(User user) {
		this.user = user;
	}

	
}
