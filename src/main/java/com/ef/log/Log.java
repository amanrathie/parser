package com.ef.log;

import java.text.ParseException;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import com.ef.util.DateUtil;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@Data
@NoArgsConstructor
@EqualsAndHashCode
@ToString
public class Log {
	
	@Id
	@GeneratedValue
	private Integer id;
	
	private Date startDate;
	private String ip;
	private String httpMethod;
	private String httpCode;
	private String httpFrom;

	public Log(String line) throws ParseException {
		super();
		
		String[] split = line.split("\\|");
		setStartDate(DateUtil.LOG_DATEFORMAT.parse(split[0]));
		setIp(split[1]);
		setHttpMethod(split[2]);
		setHttpCode(split[3]);
		setHttpFrom(split[4]);
	}
} 	
