package com.cos.capstone.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.cos.capstone.enumlist.DBState;
import com.cos.capstone.model.Schedule;
import com.cos.capstone.model.TrashSchedule;
import com.cos.capstone.repository.ScheduleRepository;
import com.cos.capstone.repository.TrashScheduleRepository;
import com.cos.capstone.service.ScheduleService;
import com.cos.capstone.service.TrashScheduleService;

@RestController
public class TrashScheduleController {

	@Autowired
	private ScheduleService scheduleService;
	

	@Autowired
	private ScheduleRepository scheduleRepository;
	
	@Autowired
	private TrashScheduleService trashScheduleService;
	

	@GetMapping("/schedule/trash") // load schedule
	public ResponseEntity<List<Schedule>> scheduleBackUp(@RequestParam("userId") Long userId) {

		long scheduleId = trashScheduleService.scheduleBackUp(userId);
		if(scheduleId == -1) {
			return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
		}
		Schedule schedule = scheduleRepository.findById(scheduleId).orElse(null);
		if(schedule != null) {
			schedule.setDBState(DBState.USE.getValue());
			scheduleRepository.save(schedule);
			List<Schedule> list = scheduleService.scheduleLoad(userId);
			return ResponseEntity.status(HttpStatus.OK).body(list);
		}
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
	}
	
}
