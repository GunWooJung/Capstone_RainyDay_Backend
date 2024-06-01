package com.cos.capstone.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.cos.capstone.model.Schedule;
import com.cos.capstone.service.LocationService;

@RestController
public class LocationController {
	
	
	@Autowired
	private LocationService locationService;
	
	@GetMapping("/location") // load schedule
	public ResponseEntity<String> scheduleLoad(@RequestParam("lat") double lat, @RequestParam("lng") double lng) {

		String result = locationService.getRegion(lat, lng);
		
		return ResponseEntity.status(HttpStatus.OK).body(result);
	}
	
	
	
	
}
