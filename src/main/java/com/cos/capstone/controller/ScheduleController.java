package com.cos.capstone.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.cos.capstone.dto.ScheduleRequestDto;
import com.cos.capstone.enumlist.ResultCodeEnum;
import com.cos.capstone.model.Location;
import com.cos.capstone.model.Schedule;
import com.cos.capstone.repository.ScheduleRepository;
import com.cos.capstone.service.ScheduleService;

@RestController
public class ScheduleController {

	@Autowired
	private ScheduleService scheduleService;

	@Autowired
	private ScheduleRepository ScheduleRepository;
	
	@GetMapping("/schedule") // load schedule
	public ResponseEntity<List<Schedule>> scheduleLoad(@RequestParam("userId") Long userId) {

		List<Schedule> list = scheduleService.scheduleLoad(userId);
		if(list.isEmpty()) {
			return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
		}
		return ResponseEntity.status(HttpStatus.OK).body(list);
	}
	
	@GetMapping("/schedule/title/{id}") // load schedule
	public ResponseEntity<Schedule> TitleLoad(@PathVariable Long id) {
		
		Optional<Schedule> schedule = ScheduleRepository.findById(id);

		if(schedule.isPresent()) {
			return ResponseEntity.status(HttpStatus.OK).body(schedule.get());
		}
		return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
	}
	
	@GetMapping("/schedule/{id}") // load schedule
	public ResponseEntity<List<Location>> LocationsLoad(@PathVariable Long id) {

		List<Location> list = scheduleService.LocationsLoad(id);
		if(list.isEmpty()) {
			return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
		}
		return ResponseEntity.status(HttpStatus.OK).body(list);
	}

	@GetMapping("/schedule/{id}/share") // load schedule
	public ResponseEntity<String> scheduleShare(@PathVariable Long id, @RequestParam("name") String name) {

		ResultCodeEnum resultCode = scheduleService.scheduleShare(id, name);

		if (resultCode == ResultCodeEnum.SUCCESS) {
			return ResponseEntity.status(HttpStatus.OK).build();
		}
		
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
	}
	
	
	
	@PostMapping("/schedule") // schedule 생성 public ResponseEntity<String>
	public ResponseEntity<String> scheduleCreate(@RequestParam("userId") Long userId,
			@RequestBody ScheduleRequestDto scheduleRequestDto) {

		ResultCodeEnum resultCode = scheduleService.scheduleCreate(userId, scheduleRequestDto);

		if (resultCode == ResultCodeEnum.SUCCESS) {
			return ResponseEntity.status(HttpStatus.CREATED).build();
		}
		
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
	}
	
	
	@PutMapping("/schedule") // schedule 생성 public ResponseEntity<String>
	public ResponseEntity<String> scheduleUpdate(@RequestParam("userId") Long userId,
			@RequestBody ScheduleRequestDto scheduleRequestDto) {

		ResultCodeEnum resultCode = scheduleService.scheduleUpdate(userId, scheduleRequestDto);

		if (resultCode == ResultCodeEnum.SUCCESS) {
			return ResponseEntity.status(HttpStatus.OK).build();
		}
		else if (resultCode == ResultCodeEnum.FAILURE) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
		}
		
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
	}

	@DeleteMapping("/schedule/{id}") //삭제
	public ResponseEntity<String> scheduleDelete(@PathVariable long id) {

		ResultCodeEnum resultCode = scheduleService.scheduleDelete(id);

		if (resultCode == ResultCodeEnum.SUCCESS) {
			return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
		}
		
		return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
	}
}
