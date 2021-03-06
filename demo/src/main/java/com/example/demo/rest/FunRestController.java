package com.example.demo.rest;

import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class FunRestController {
	//expose "/" end point that return hello world
	
	//inject properties for : coach.name and team.name
	
	@Value("${coach.name}")
	private String coachName;
	
	@Value("${team.name}")
	private String teamName;
	
	@Value("${testing.value}")
	private String testValue;
	
	//expose new endpoint for custom properties
	@GetMapping("/teaminfo")
	public String getTeamInfo()
	{
		return "Coach: " + coachName + ", Team Name : " + teamName + " ,Test Val : " + testValue;
	}
	
	@GetMapping("/bin")
	public String sayHello()
	{
		return "Hello World! Time on the server is " + LocalDateTime.now();
	}
	
	//expose a new endpoint for "workout"
	@GetMapping("/workout")
	public String getDailyWorkout()
	{
		return "Run a hard 5ks!";
	}
}
