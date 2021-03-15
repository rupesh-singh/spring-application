package com.assignment.tail.service;

import java.io.BufferedReader;
import java.io.InputStreamReader;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.stereotype.Service;

@Service
public class TailService {
	
	@Autowired
	private SimpMessageSendingOperations simpMessageSendingOperations;
	
	public void sendMessage(){
    	System.out.print("check if started");
               
        String cmd = "java D:\\java-ws\\springboot-workspace\\BrowserStackTailAssignment\\src\\main\\resources\\utility\\TailFast.java D:\\java-ws\\springboot-workspace\\BrowserStackTailAssignment\\src\\main\\resources\\utility\\Logs.txt";
        try {
        	Process process = Runtime.getRuntime().exec(cmd);
            try {
                BufferedReader br = new BufferedReader(new InputStreamReader(process.getInputStream(), "UTF-8"));
                String lineOne = null;
                while ((lineOne = br.readLine()) != null) {
                        	simpMessageSendingOperations.convertAndSend("/topic/log", lineOne.toString());
                        	Thread.sleep(1000);
                }
                br.close();
            } 
            catch (Exception e) {
            	e.printStackTrace();
            }
                
       
        } catch (Exception e) {
            e.printStackTrace();
            System.out.print("Tail Command Execution Failed! "+e);        }
    }
	
}
