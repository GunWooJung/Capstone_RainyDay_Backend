package com.cos.capstone.model;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Review {

	@Id
	@Column(name = "review_id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long reviewId;
	
	@Column(name = "state")
	private int DBState;
	
	@ManyToOne
	@JoinColumn(name = "userId")
	private User userId;
	
	@Column(name = "location")
	private String location;
	
	@Lob
	@Column(name = "contents")
	private String contents;
	
	@Column(name = "recevied_time")
	private LocalDateTime receviedTime;
	
}
