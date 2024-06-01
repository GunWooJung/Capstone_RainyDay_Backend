package com.cos.capstone.service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cos.capstone.coordinate.ConvertGRID;
import com.cos.capstone.coordinate.LatXLngY;
import com.cos.capstone.dto.LocationRequestDto;
import com.cos.capstone.dto.ScheduleRequestDto;
import com.cos.capstone.enumlist.DBState;
import com.cos.capstone.enumlist.ResultCodeEnum;
import com.cos.capstone.model.Location;
import com.cos.capstone.model.Schedule;
import com.cos.capstone.model.TrashSchedule;
import com.cos.capstone.model.User;
import com.cos.capstone.repository.LocationRepository;
import com.cos.capstone.repository.ScheduleRepository;
import com.cos.capstone.repository.TrashScheduleRepository;
import com.cos.capstone.repository.UserRepository;

@Service
public class ScheduleService {

	@Autowired
	private ScheduleRepository scheduleRepository;

	@Autowired
	private UserRepository useRepository;
	

	@Autowired
	private LocationRepository locationRepository;
	
	@Autowired
	private LocationService locationService;
	
	@Autowired
	private TrashScheduleRepository trashScheduleRepository;

	@Transactional(readOnly = true)
	public List<Schedule> scheduleLoad(long userId) {

		Optional<User> optional_user = useRepository.findById(userId);
		if (!optional_user.isPresent()) {
			return null;
		}

		User user = optional_user.get();
		if (user.getDBState() == DBState.NOT_USE.getValue()) {
			return null;
		}

		List<Schedule> list = scheduleRepository.findByUserId(user);
		List<Schedule> useList = list.stream().filter(s -> s.getDBState() == DBState.USE.getValue())
				.collect(Collectors.toList());
		return useList;
	}

	@Transactional
	public ResultCodeEnum scheduleCreate(Long userId, ScheduleRequestDto scheduleRequestDto) {

		String title = scheduleRequestDto.getTitle();
		String hashTag = scheduleRequestDto.getHashTag();
		long id = scheduleRequestDto.getScheduleId();
		List<LocationRequestDto> list = scheduleRequestDto.getLocations();
		
		Optional<User> user = useRepository.findById(userId);
		if (!user.isPresent()) {
			return ResultCodeEnum.FAILURE;
		} 
		else if (user.get().getDBState() == DBState.NOT_USE.getValue()) {
			System.out.println("ee");
			return ResultCodeEnum.FAILURE;
		}
		
		Schedule schedule;
		Optional<Schedule> find;
		if(id == 0) {
			schedule = new Schedule();
		}
		else {
			find = scheduleRepository.findById(id);
			schedule = find.get();
			locationRepository.deleteByScheduleId(schedule);
		}
		
		schedule.setTitle(title);
		schedule.setUserId(user.get());
		schedule.setDBState(DBState.USE.getValue());
		schedule.setHashTag(hashTag);
		scheduleRepository.save(schedule);
		System.out.println("ee");
		
		LocalDateTime time = list.get(0).getDepartTime();
	
		for(int i = 0; i < list.size(); i++) {
			
			LocationRequestDto locations = list.get(i);
			
			Location location = new Location();
			
			location.setScheduleId(schedule);
			location.setName(locations.getName());
			location.setLat(locations.getLat());
			location.setLng(locations.getLng());
			
			if(i == 0) {
				location.setDepartTime(time);
				schedule.setDepartTime(time);
				schedule.setDepartName(locations.getName());
				scheduleRepository.save(schedule);
			}
			else if(i == list.size()-1) {
				LocationRequestDto beforelocations = list.get(i-1);	//장소가 2개 이상이여야함
				long duration = LocationService.durationTime(
						beforelocations.getLat(),
						beforelocations.getLng(), 
						locations.getLat(), 
						locations.getLng(),
						time);
				time = time.plusSeconds(duration);
				location.setDestTime(time);
			}
			else {
				LocationRequestDto beforelocations = list.get(i-1);	//장소가 2개 이상이여야함
				long duration = LocationService.durationTime(
						beforelocations.getLat(),
						beforelocations.getLng(), 
						locations.getLat(), 
						locations.getLng(),
						time);
				time = time.plusSeconds(duration);
				location.setDestTime(time);
				
				time = time.plusMinutes(locations.getDurationMin());
				location.setDepartTime(time);
			}
			
			LatXLngY xy = ConvertGRID.convertGRID(locations.getLat(),
					locations.getLng());
			location.setNx((int)xy.x);
			location.setNy((int)xy.y);
			String regioncode = locationService.getRegion(locations.getLat(), locations.getLng());
			location.setRegioncode(regioncode);
			locationRepository.save(location);
		}
		return ResultCodeEnum.SUCCESS;
	}

	
	@Transactional
	public ResultCodeEnum scheduleDelete(long id) {
		
		Optional<Schedule> optional_schedule = scheduleRepository.findById(id);
		if (!optional_schedule.isPresent()) {
			return ResultCodeEnum.FAILURE;
		} 
		
		Schedule schedule = optional_schedule.get();
		
		if (schedule.getDBState() == DBState.NOT_USE.getValue()) {
			return ResultCodeEnum.FAILURE;
		}
		
		
		schedule.setDBState(DBState.NOT_USE.getValue());
		scheduleRepository.save(schedule);
		TrashSchedule trash = new TrashSchedule();
		trash.setScheduleId(schedule.getScheduleId());
		trash.setUserId(schedule.getUserId().getUserId());
		trashScheduleRepository.save(trash);
		return ResultCodeEnum.SUCCESS;
	}

	@Transactional
	public ResultCodeEnum scheduleUpdate(Long userId, ScheduleRequestDto scheduleRequestDto) {
		// TODO 자동 생성된 메소드 스텁
		
		return null;
	}

	@Transactional(readOnly = true)
	public List<Location> LocationsLoad(Long scheduleId) {
		
		Optional<Schedule> optional_schedule = scheduleRepository.findById(scheduleId);
		
		if (optional_schedule.isPresent()) {
			Schedule schedule = optional_schedule.get();
			if (schedule.getDBState() == DBState.NOT_USE.getValue()) {
				return null;
			}
			List<Location> list = locationRepository.findByScheduleId(schedule);
			return list;
		}
		return null;
	}
	
	@Transactional
	public ResultCodeEnum scheduleShare(Long id, String name) {
		
		
		Optional<Schedule> optional_schedule = scheduleRepository.findById(id);
		
		if (!optional_schedule.isPresent()) {
			return ResultCodeEnum.FAILURE;
		}
		Schedule schedule = optional_schedule.get();
		
		if (schedule.getDBState() == DBState.NOT_USE.getValue()) {
			return ResultCodeEnum.FAILURE;
		}
	
		
		Optional<User> optional_user = useRepository.findByUserName(name);
		if (!optional_user.isPresent()) {
			return ResultCodeEnum.FAILURE;
		}

		User user = optional_user.get();
		if (user.getDBState() == DBState.NOT_USE.getValue()) {
			return ResultCodeEnum.FAILURE;
		}
		
		Schedule newSchedule = new Schedule(schedule);
		newSchedule.setUserId(user);
		scheduleRepository.save(newSchedule);
		
		List<Location> list = locationRepository.findByScheduleId(schedule);
		List<Location> newList = new ArrayList<>();
		for(Location loc : list) {
			Location newLoc = new Location(loc);
			newLoc.setScheduleId(newSchedule);
			newList.add(newLoc);
		}
		locationRepository.saveAll(newList);
		return ResultCodeEnum.SUCCESS;
	}

}
