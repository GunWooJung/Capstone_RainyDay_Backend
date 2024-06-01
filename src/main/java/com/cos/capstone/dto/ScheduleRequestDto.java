package com.cos.capstone.dto;

import java.util.List;

import lombok.Data;

@Data
public class ScheduleRequestDto {
	
	private long scheduleId = 0;
	
    private String title;
    
    private String hashTag;
	
    private List<LocationRequestDto> locations;
}
