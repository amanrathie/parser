package com.ef;
import static org.assertj.core.api.Assertions.assertThat;

import java.util.Date;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Profile;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import com.ef.log.BlockedIp;
import com.ef.log.BlockedLogRepository;
import com.ef.log.DurationType;
import com.ef.log.Log;
import com.ef.log.LogService;
import com.ef.util.DateUtil;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = TestApplicationConfiguration.class)
@Profile("test")
@Transactional
public class ParserTest {
	
	@Autowired
	private LogService logService;
	
	@Autowired
	private BlockedLogRepository blockedLogRepository;


	@Test
	public void test_with_first_example() throws Exception {
		Date startDate = DateUtil.LOG_DATEFORMAT.parse("2017-01-01 15:00:00");
		DurationType duration = DurationType.hourly;
		int threshold = 200;
		
		logService.execute(startDate, duration, threshold);

		List<BlockedIp> blockedIps = blockedLogRepository.findAll();
		System.out.println("IPs detected: " +blockedIps);
		
		assertThat(blockedIps.size()).isEqualTo(2);
		assertThat(blockedLogRepository.findAll().size()).isEqualTo(2);
	}
	
	@Test
	public void test_with_second_example() throws Exception {
		Date startDate = DateUtil.LOG_DATEFORMAT.parse("2017-01-01 00:00:00");
		DurationType duration = DurationType.daily;
		int threshold = 500;
		
		logService.execute(startDate, duration, threshold);

		List<BlockedIp> blockedIps = blockedLogRepository.findAll();

		assertThat(blockedIps.size()).isEqualTo(15);
		assertThat(blockedLogRepository.findAll().size()).isEqualTo(15);
		
	}
	
	@Test
	public void test_split_is_adding_correct_fields() throws Exception {
		String sampleLine = "2017-01-01 00:02:36.179|192.168.52.153|\"GET / HTTP/1.1\"|200|\"Mozilla/5.0 (Windows NT 6.1; WOW64) SkypeUriPreview Preview/0.5\"";
		
		Log log = new Log(sampleLine);

		assertThat(DateUtil.LOG_DATEFORMAT.format(log.getStartDate())).isEqualTo("2017-01-01 00:02:36");
		assertThat(log.getIp()).isEqualTo("192.168.52.153");
	}
}
