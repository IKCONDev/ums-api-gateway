package com.ikn.ums.apigateway.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UMSMicroservicesFallBackMethodController {

	   @GetMapping("/employeeServiceFallBack")
	    public String employeeServiceFallBackMethod() {
	        return "Employee Service is taking longer than Expected." +
	                " Please try again later";
	    }

	    @GetMapping("/departmentServiceFallBack")
	    public String departmentServiceFallBackMethod() {
	        return "Department Service is taking longer than Expected." +
	                " Please try again later";
	    }

}
