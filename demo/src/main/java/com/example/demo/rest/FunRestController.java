package com.example.demo.rest;

import java.time.LocalDateTime;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class FunRestController {
	//expose "/" end point that return hello world
	
	@GetMapping("/bin")
	public String sayHello()
	{
		return "Hello World! Time on the server is " + LocalDateTime.now();
	}
}
