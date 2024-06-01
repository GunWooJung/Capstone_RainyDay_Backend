package com.cos.capstone.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.cos.capstone.dto.AlarmDto;
import com.cos.capstone.dto.ScheduleRequestDto;
import com.cos.capstone.enumlist.ResultCodeEnum;
import com.cos.capstone.model.AlarmMessage;
import com.cos.capstone.model.Schedule;
import com.cos.capstone.repository.AlarmRepository;
import com.cos.capstone.repository.ScheduleRepository;
import com.cos.capstone.service.AlarmService;

@RestController
public class AlarmController {
	
	
	@Autowired
	private AlarmService alarmService;

	@GetMapping("/alarm") // load alarm
	public ResponseEntity<List<AlarmMessage>> alarmLoad(@RequestParam("userId") Long userId) {

		List<AlarmMessage> list = alarmService.getMessage(userId);
		if(list.isEmpty()) {
			return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
		}
		return ResponseEntity.status(HttpStatus.OK).body(list);
	}
	
	@PostMapping("/alarm") // load alarm
	public ResponseEntity<String> alarmCreate(@RequestParam("userId") Long userId,
			@RequestBody AlarmDto alarmDto) {

		ResultCodeEnum resultCode = alarmService.alarmCreate(userId, alarmDto);

		if (resultCode == ResultCodeEnum.SUCCESS) {
			return ResponseEntity.status(HttpStatus.CREATED).build();
		}
		
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
		
	}
}
