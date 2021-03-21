package com.oop.Spider;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;

//main controller
@SpringBootApplication
public class SpiderApplication {

	//main method
	public static void main(String[] args) {
		SpringApplication.run(SpiderApplication.class, args);
	}
	
	/**
	 * This method is called whenever an error and exception is detected
	 * @return the error view template
	 */
	@GetMapping("/error")
	public String getError() {
		return "error";
	}

}
