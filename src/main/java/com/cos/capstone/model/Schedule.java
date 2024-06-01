package com.cos.capstone.model;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@JsonIgnoreProperties("userId")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
public class Schedule {
	
	@Id
	@Column(name = "schedule_id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long scheduleId;
	
	@Column(name = "state")
	private int DBState;
	
	@ManyToOne
	@JoinColumn(name = "userId")
	private User userId;
	
	@Column(name = "title")
	private String title;
	
	@Column(name = "depart_name")
	private String departName;
	
	@Column(name = "depart_time")
	private LocalDateTime departTime;
	
	@Column(name = "hashTag")
	private String hashTag;
	
	public Schedule(Schedule other) {
	        this.DBState = other.DBState;
	        this.title = other.title;
	        this.departName = other.departName;
	        this.departTime = other.departTime;
	        this.hashTag = other.hashTag;
	 }

}
