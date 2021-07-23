package com.gdgu.trackit;

import java.io.IOException;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication
public class TrackitApplication {

	public static void main(String[] args) throws IOException {
		SpringApplicationBuilder builder = new SpringApplicationBuilder(TrackitApplication.class);
		builder.headless(false);
		ConfigurableApplicationContext configurableApplicationContext = builder.run(args);
		// Loader loader = configurableApplicationContext.getBean(Loader.class);
		// loader.loadTimeTable();
		configurableApplicationContext.getBean(Tracker.class);
	}

}
