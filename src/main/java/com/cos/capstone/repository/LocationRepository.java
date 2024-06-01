package com.cos.capstone.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.cos.capstone.model.Location;
import com.cos.capstone.model.Schedule;

@Repository
public interface LocationRepository extends JpaRepository<Location, Long> {

	List<Location> findByScheduleId(Schedule schedule);

	void deleteByScheduleId(Schedule schedule);

}
