package com.groww.app.ws.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

@Component
public class AppProperties {
	@Autowired
	private Environment env;
	
	public AppProperties() {
		super();
		System.out.println("object created");
	}

	public String getTokenSecret()
	{
		return env.getProperty("tokenSecret");
	}
}
