package com.tipray.core.job;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

//@Component("exampleJob")
public class ExampleJob{
	Logger log = LoggerFactory.getLogger(this.getClass());
	
	public void execute(){
		System.out.println("ExampleJob execute");
	}
}
