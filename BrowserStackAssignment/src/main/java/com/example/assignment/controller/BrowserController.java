package com.example.assignment.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.assignment.service.CommandExecutorService;

@RestController
@CrossOrigin(origins = "*")
public class BrowserController {

	@Autowired
	private CommandExecutorService commandExecutorService;
	
	@RequestMapping("/start")
	public String OpenBrowser(@RequestParam String browser, @RequestParam String url) {		
		String output = commandExecutorService.StartBrowser(browser, url);		
		return output;
	}
	
	@RequestMapping("/stop")
	public String StopBrowser(@RequestParam String browser) {
		String output = commandExecutorService.StopBrowser(browser);
		return output;
	}
	
	@RequestMapping("/geturl")
	public String GetUrl(@RequestParam String browser) {
		String output = commandExecutorService.fetchLastUrl(browser);
		return output;
	}
	
	@RequestMapping("/cleanup")
	public String Cleanup(@RequestParam String browser) {
		String output = commandExecutorService.CleanHistory(browser); 
		return output;
	}
}
