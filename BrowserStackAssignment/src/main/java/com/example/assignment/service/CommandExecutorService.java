package com.example.assignment.service;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

import org.springframework.stereotype.Service;

import com.example.assignment.model.CmdOutput;

@Service
public class CommandExecutorService {
	
	private final String TEMP_DIR = "D:\\java-ws\\springboot-workspace\\BrowserStackAssignment\\src\\main\\resources\\temp";

    private final String chrome = "chrome.exe";
    private final String ChromehistoryPath = "C:\\Users\\Rupesh\\AppData\\Local\\Google\\Chrome\\User Data\\Default\\History";
    private final String ChromeSqlforlastUrl = "select url from urls order by last_visit_time desc limit 1";

    private final String firefox = "firefox.exe";
    private final String FirefoxhistoryPath = "C:\\Users\\Rupesh\\AppData\\Roaming\\Mozilla\\Firefox\\Profiles\\ijefjbz8.default-release\\places.sqlite";
    private final String FirefoxSqlforlastUrl = "select url from moz_places order by last_visit_date desc limit 1";

    private CmdOutput CommandExecutor(String... cmd){
        CmdOutput cmdOutput = new CmdOutput();
        try {
            Process process = Runtime.getRuntime().exec(cmd);

            BufferedReader stdInput = new BufferedReader(new InputStreamReader(process.getInputStream()));

            BufferedReader stdError = new BufferedReader(new InputStreamReader(process.getErrorStream()));
            StringBuilder output = new StringBuilder("");
            StringBuilder error = new StringBuilder("");
            String traverse;
            while ((traverse = stdInput.readLine()) != null) {
                output.append(traverse);
            }
            while ((traverse = stdError.readLine()) != null) {
                error.append(traverse);
            }
            cmdOutput.setOutput(output.toString());            
            cmdOutput.setError(error.toString());
        }
        catch (Exception e){
            System.out.println(e.toString());
        }

        return cmdOutput;
    }
	
    /* TODO: We can check type of OS and change the command accordingly.*/
    public String CheckOS() {
    	String os = System.getProperty("os.name");
    	if(os.toLowerCase().contains("window")) {
    		return "Windows";
    	}
    	if(os.toLowerCase().contains("mac")) {
    		return "mac";
    	}
    	if(os.toLowerCase().contains("nix")|| os.toLowerCase().contains("nux")) {
    		return "unix";
    	}
    	return "windows";
    	
    }
    
	public String StartBrowser(String browser, String url){
        CmdOutput cmdOutput;
        
        if(browser.toLowerCase().equals("chrome"))
            cmdOutput = CommandExecutor("cmd","/c","start",chrome,url);
        else
            cmdOutput = CommandExecutor("cmd","/c","start",firefox,url);
        
        return (cmdOutput.getError()==null || cmdOutput.getError() == "")?"Success":"Failure";
    }
	
	public String StopBrowser(String browser){
        CmdOutput cmdOutput;
        if(browser.toLowerCase().equals("chrome"))
            cmdOutput = CommandExecutor("taskkill","/F","/IM",chrome,"/T");
        else
            cmdOutput = CommandExecutor("taskkill","/F","/IM",firefox,"/T");
        
        return (cmdOutput.getError()==null || cmdOutput.getError() == "")?"Success":"Failure";
    }
	
	public void copyFile(String target, String destination){
        try {
            if(Files.exists(Paths.get(destination)))
                Files.delete(Paths.get(destination));
            Files.copy(Paths.get(target), Paths.get(destination), StandardCopyOption.COPY_ATTRIBUTES);
        }
        catch (Exception e){
            System.out.println(e);
        }
    }
	
	public String fetchLastUrl(String browser){
		CmdOutput cmdOutput;
        String SQLLITECOMMAND = "D:\\SQLite\\sqlite3";
        
        if(browser.toLowerCase().equals("chrome")){
            copyFile(ChromehistoryPath,TEMP_DIR);
            String[] cmd = {SQLLITECOMMAND, TEMP_DIR, ChromeSqlforlastUrl,".quit"};
            cmdOutput = CommandExecutor(cmd);
            
        }
        else{
            copyFile(FirefoxhistoryPath,TEMP_DIR);
            String[] cmd = {SQLLITECOMMAND, TEMP_DIR, FirefoxSqlforlastUrl,".quit"};
            cmdOutput = CommandExecutor(cmd);            
        }
//        System.out.println("Error: "+cmdOutput.getError()+" Output: "+cmdOutput.getOutput());
        return (cmdOutput.getError()==null || cmdOutput.getError() == "")?cmdOutput.getOutput():"Failure";

    }
	
	public String CleanHistory(String browser) {
		CmdOutput cmdOutput;
		if(browser.toLowerCase().equals("chrome1"))
            cmdOutput = CommandExecutor("cmd","/c","del",ChromehistoryPath);
        else
            cmdOutput = CommandExecutor("cmd","/c","del",FirefoxhistoryPath);
		
		return (cmdOutput.getError()==null || cmdOutput.getError() == "")?"Success":"Failure";
	}

	

}
