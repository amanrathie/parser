package com.ef.log;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@Data
@NoArgsConstructor
@EqualsAndHashCode
@ToString
public class BlockedIp {
	
	@Id
	@GeneratedValue
	private Integer id;
	
	private String ip;

	private String message;
	
	public BlockedIp(String ip, String message) {
		this.ip = ip;
		this.message = message;
	}
} 	
