package com.cos.capstone.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.cos.capstone.coordinate.RegionCode;

@Repository
public interface RegionCodeRepository extends JpaRepository<RegionCode, Long> {

	List<RegionCode> findByXAndY(int x, int y);

}
