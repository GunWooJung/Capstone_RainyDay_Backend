package com.cos.capstone.model;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
public class AlarmMessage {
	@Id
	@Column(name = "alarm_id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long alarmId;
	
	@Column(name = "state")
	private int DBState;
	
	@ManyToOne
	@JoinColumn(name = "userId")
	private User userId;
	
	@Column(name = "title")
	private String title;
	
	@Column(name = "content")
	private String content;
	
	@Column(name = "recevied_time")
	private LocalDateTime receviedTime;
	
}
