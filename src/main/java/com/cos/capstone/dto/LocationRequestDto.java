package com.cos.capstone.dto;

import java.time.LocalDateTime;
import lombok.Data;

@Data
public class LocationRequestDto {

	private String name;
	
	private double lat;
	
	private double lng;
	
    private LocalDateTime departTime;
    
    private int durationMin = 0;
	
}
