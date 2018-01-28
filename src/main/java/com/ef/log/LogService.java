package com.ef.log;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.ParseException;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.ef.util.DateUtil;

@Service
public class LogService {

	@Autowired
	private LogRepository logRepository;
	
	@Autowired
	private BlockedLogRepository blockedLogRepository;
	
	private String fileName;
	
	public LogService(@Value("${parser.filename}") String fileName) {
		this.fileName = fileName;
	}

	public void execute(Date startDate, DurationType duration, int threshold) throws Exception {
		System.out.println("Parsing log file...");
		List<Log> logs = parseLogFile();
		
		System.out.println("Saving to database, please wait...");
		saveLogFiles(logs);
		
		List<String> blockedIps = logRepository.find(startDate, DateUtil.addTime(startDate, duration), threshold);
		System.out.println("IPs returned: "+blockedIps);
		
		System.out.println("Saving blocked ips...");
		saveBlockedIps(blockedIps, threshold);
	}
	
	private void saveLogFiles(List<Log> logs) {
		logRepository.save(logs);
	}
	
	private void saveBlockedIps(List<String> ips, int threshold) {
		List<BlockedIp> blockedLogs = ips
				.stream()
				.map(ip -> new BlockedIp(ip, "IP blocked because of "+threshold+" threshold limit"))
				.collect(Collectors.toList());
		blockedLogRepository.save(blockedLogs);
	}
	
	private List<Log> parseLogFile() throws Exception {
		Path path = Paths.get(fileName);
		
	    Stream<String> lines = Files.lines(path);

	    List<Log> logs = lines
	    		.map(line -> createLog(line))
	    		.collect(Collectors.toList());
	    
	    lines.close();
	    
	    return logs;
	}
	
	private Log createLog(String line) {
		try {
			return new Log(line);
		} catch (ParseException ex) {
			throw new RuntimeException(ex);
		}
	}
	

}
