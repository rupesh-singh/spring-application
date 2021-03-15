package com.assignment.tail.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import com.assignment.tail.service.TailService;

@Controller
public class TailController {

	@Autowired
	private TailService service;
	
	@GetMapping("/getlogs")
	public void getLogs() {
		service.sendMessage();
	}
	
}
