package com.example.workflow;

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
import org.springframework.web.bind.annotation.RestController;

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

    public void victoryTask(String taskInstanceId) {
        taskService.complete(taskInstanceId);
    }
    
    @GetMapping("/add")
	public ResponseEntity<?> add() {
    	processInstance = runtimeService.startProcessInstanceByKey("new_emp");
		processInstanceId = processInstance.getProcessInstanceId();
		System.out.println("Process id is Null");
		System.out.println("please add employee details");
    	//logger.debug("Debug Logger Enabled");
    	//logger.info("Logger Enabled please add employee details");
    	 return new ResponseEntity < > ("Testing Sucessful", HttpStatus.OK);
    	}
    
	@PostMapping("/addEmployee")
	public void addEmployee(@RequestBody Employee employee) {
		System.out.println("inside add Employee");
		System.out.println(employee.getFirstName());
		System.out.println(employee.getLastName());
		System.out.println(employee.getOhrId());
		System.out.println("====Employee Added====");
		employeeRepo.save(employee);
		/*
		 * System.out.println(employeeRepo.findAll()); List<Task> taskList =
		 * taskService.createTaskQuery().processInstanceId(processInstanceId).
		 * orderByTaskCreateTime().desc().list(); taskInstanceId =
		 * taskList.get(0).getId(); System.out.println("taskInstanceId "
		 * +taskInstanceId); victoryTask(taskInstanceId);
		 */
	}
	
	@PostMapping("/approval")
	public void approval(@RequestBody String status) {
		List<Task> taskList = taskService.createTaskQuery().processInstanceId(processInstanceId).orderByTaskCreateTime().desc().list();
        taskInstanceId = taskList.get(0).getId();
        System.out.println("taskInstanceId " +taskInstanceId);		
        
        
        System.out.println("status is " + status);
		if (status.equals("accept") || status.contains("accept")) {
			System.out.println("****Approved****");
			HashMap<String, String> arg1 = new HashMap<>();
			arg1.put("approved", "yes");
			runtimeService.setVariablesLocal(processInstanceId, arg1);
			
		} else {
			System.out.println("****Reject****");
			HashMap<String, String> arg1 = new HashMap<>();
			arg1.put("approved", "no");
			runtimeService.setVariablesLocal(processInstanceId, arg1);
		}
		victoryTask(taskInstanceId);
		
	}
}
