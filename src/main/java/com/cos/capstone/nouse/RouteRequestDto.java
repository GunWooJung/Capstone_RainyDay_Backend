package com.cos.capstone.nouse;

import lombok.Data;

@Data
public class RouteRequestDto {

	private long scheduleId;

	private int departYear;

	private int departMonth;
	
	private int departDay;
	
	private int departHour;
	
	private int departMinute;
	
	private String departName;
	
	private double departLat; 	
	
	private double departLng;
	
	private String departAddress;

	private String destName; 	

	private double destLat; 	

	private double destLng;
	
	private String destAddress;

}
