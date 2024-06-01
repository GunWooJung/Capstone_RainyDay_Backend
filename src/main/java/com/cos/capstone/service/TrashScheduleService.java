package com.cos.capstone.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cos.capstone.model.TrashSchedule;
import com.cos.capstone.repository.TrashScheduleRepository;

@Service
public class TrashScheduleService {

	@Autowired
	private TrashScheduleRepository trashScheduleRepository;

	public long scheduleBackUp(Long userId) {

		List<TrashSchedule> list = trashScheduleRepository.findByUserId(userId);

		long maxId = -1;
		long scheduleId = -1;

		for (TrashSchedule trashSchedule : list) {
			if (trashSchedule.getId() > maxId) {
				maxId = trashSchedule.getId();
				scheduleId = trashSchedule.getScheduleId();
			}
		}
		if (maxId != -1) {
			trashScheduleRepository.deleteById(maxId);
			return scheduleId;
		}
		return -1;
	}

}
