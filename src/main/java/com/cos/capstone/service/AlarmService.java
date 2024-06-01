package com.cos.capstone.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cos.capstone.dto.AlarmDto;
import com.cos.capstone.enumlist.DBState;
import com.cos.capstone.enumlist.ResultCodeEnum;
import com.cos.capstone.model.AlarmMessage;
import com.cos.capstone.model.User;
import com.cos.capstone.repository.AlarmRepository;
import com.cos.capstone.repository.UserRepository;

@Service
public class AlarmService {

	@Autowired
	private AlarmRepository alarmRepository;
	
	@Autowired
	private UserRepository userRepository;

	@Transactional(readOnly = true)
	public List<AlarmMessage> getMessage(long user_id) {
		
		Optional<User> user = userRepository.findById(user_id);
		
		if(user.isPresent()) {
			List<AlarmMessage> list = alarmRepository.findByUserId(user.get());
			
			return list;
		}
		
		return null;
	}

	@Transactional
	public ResultCodeEnum alarmCreate(Long userId, AlarmDto alarmDto) {
		
		Optional<User> user = userRepository.findById(userId);
		
		if(user.isPresent()) {
			AlarmMessage message = new AlarmMessage();
			message.setUserId(user.get());
			message.setTitle(alarmDto.getTitle());
			message.setReceviedTime(LocalDateTime.now());
			message.setContent(alarmDto.getContent());
			message.setDBState(DBState.USE.getValue());
			alarmRepository.save(message);
			return ResultCodeEnum.SUCCESS;
		}
		
		return ResultCodeEnum.FAILURE;
	}
	
}