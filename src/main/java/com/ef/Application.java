package com.ef;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Profile;

import com.ef.log.DurationType;
import com.ef.log.LogService;
import com.ef.util.DateUtil;

@SpringBootApplication
@Profile("!test")
public class Application implements CommandLineRunner {
	
	@Autowired
	private LogService logService;

	public static void main(String[] args) throws Exception {
		 SpringApplication.run(Application.class, args);
	}

	public void run(String... args) throws Exception {
		if (args.length != 3) {
			System.err.println("Usage example: java -jar parser-0.0.1-SNAPSHOT.jar 2017-01-01.13:00:00 hourly 100");
			System.exit(-1);
		}
		
		Date startDate = DateUtil.LOG_DATEFORMAT.parse(args[0].replaceAll("\\.", " "));
		DurationType duration = DurationType.valueOf(args[1]);
		Integer threshold = Integer.valueOf(args[2]);
		
		logService.execute(startDate, duration, threshold);
	}
}
