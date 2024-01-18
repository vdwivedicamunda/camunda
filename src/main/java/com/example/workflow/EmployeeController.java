package com.example.workflow;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.util.HashMap;
import java.util.List;

import org.camunda.bpm.engine.RuntimeService;
import org.camunda.bpm.engine.TaskService;
import org.camunda.bpm.engine.runtime.ProcessInstance;
import org.camunda.bpm.engine.task.Task;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping
public class EmployeeController {
	
	String processDefinitionName = "testCaseSample";
    String processInstanceId;
    String taskInstanceId;
    ProcessInstance processInstance;
    private static final Logger logger = LoggerFactory.getLogger(EmployeeController.class);

    
    @Autowired
    private TaskService taskService;
    
    @Autowired
    private EmployeeRepo employeeRepo;
    
	
	@Autowired
	RuntimeService runtimeService;
	@Autowired
	EmailNotification emailNotification;

    public void victoryTask(String taskInstanceId) {
        taskService.complete(taskInstanceId);
    }
    
    @GetMapping("/add")
	public void add() {
    	processInstance = runtimeService.startProcessInstanceByKey("new_emp");
		processInstanceId = processInstance.getProcessInstanceId();
		//System.out.println("Process id is Null");
		System.out.println("please add employee details");
    	//logger.debug("Debug Logger Enabled");
    	//logger.info("Logger Enabled please add employee details");
    	// return new ResponseEntity < > ("Testing Sucessful", HttpStatus.OK);
    	}
    
	@PostMapping("/addEmployee")
	public void addEmployee(@RequestBody Employee employee) {
		System.out.println("inside add Employee");
		System.out.println(employee.getFirstName());
		System.out.println(employee.getLastName());
		System.out.println(employee.getOhrId());
		System.out.println("====Employee Added====");
		employeeRepo.save(employee);
		
		  System.out.println(employeeRepo.findAll()); List<Task> taskList =
		  taskService.createTaskQuery().processInstanceId(processInstanceId).
		  orderByTaskCreateTime().desc().list(); taskInstanceId =
		  taskList.get(0).getId(); System.out.println("taskInstanceId "
		  +taskInstanceId); victoryTask(taskInstanceId);
		 
	}
	
	@PostMapping("/approval")
	public void approval(@RequestParam String decision , @RequestParam MultipartFile pdfFile ) {
		System.out.println("Status Value "  +decision);
		
		System.out.println("PDF Value "  +pdfFile);
		
		readAndWriteFile(pdfFile);
		
		List<Task> taskList = taskService.createTaskQuery().processInstanceId(processInstanceId).orderByTaskCreateTime().desc().list();
        taskInstanceId = taskList.get(0).getId();
        System.out.println("taskInstanceId " +taskInstanceId);		
        
        
        System.out.println("status is " + decision);
		if (decision.equals("accept") || decision.contains("accept")) {
			System.out.println("****Approved****");
			HashMap<String, String> arg1 = new HashMap<>();
			arg1.put("approved", "yes");
			runtimeService.setVariablesLocal(processInstanceId, arg1);
			emailNotification.execute("approved");
		} else {
			System.out.println("****Reject****");
			HashMap<String, String> arg1 = new HashMap<>();
			arg1.put("approved", "no");
			runtimeService.setVariablesLocal(processInstanceId, arg1);
			emailNotification.execute("rejected");
		}
		victoryTask(taskInstanceId);
		
	}
	
	public void readAndWriteFile( MultipartFile pdfFile)
	{
		//path =C:\Users\dell\Downloads
		//File src = new File("C:\\Users\\dell\\Downloads\\"+pdfFile.getOriginalFilename().toString());
		//File src = new File(pdfFile));
		String fileName = pdfFile.getOriginalFilename();
        File dest = new File("D:\\Network Location Camunda File\\"+fileName); 
             
        // using copy(InputStream,Path Target); method 
        try {
			//Files.copy(src.toPath(), dest.toPath());
        	pdfFile.transferTo(dest);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
