package com.example.portal.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import com.example.portal.repo.LeavedataRepository;


@Controller
public class LeavedataController {
	
	private LeavedataRepository ldRepo;
	@Autowired
	public void setLdRepo(LeavedataRepository ldRepo) {
		this.ldRepo = ldRepo;
	}
	
	
		
	
}
