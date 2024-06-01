package com.cos.capstone.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.cos.capstone.model.Schedule;
import com.cos.capstone.model.TrashSchedule;
import com.cos.capstone.model.User;

@Repository
public interface TrashScheduleRepository extends JpaRepository<TrashSchedule, Long> {

	List<TrashSchedule> findByUserId(Long userId);


}