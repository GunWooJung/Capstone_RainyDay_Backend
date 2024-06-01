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
import com.cos.capstone.dto.ReviewDto;
import com.cos.capstone.enumlist.ResultCodeEnum;
import com.cos.capstone.model.AlarmMessage;
import com.cos.capstone.model.Review;
import com.cos.capstone.service.AlarmService;
import com.cos.capstone.service.ReviewService;

@RestController
public class ReviewController {
	
	@Autowired
	private ReviewService reviewService;

	@GetMapping("/review") // load review
	public ResponseEntity<Review> reviewLoad() {

		Review review = reviewService.getReview();
		if(review == null) {
			return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
		}
		return ResponseEntity.status(HttpStatus.OK).body(review);
	}
	
	@PostMapping("/review") // load review
	public ResponseEntity<String> reviewCreate(@RequestParam("userId") Long userId,
			@RequestBody ReviewDto reviewDto) {

		ResultCodeEnum resultCode = reviewService.reviewCreate(userId, reviewDto);

		if (resultCode == ResultCodeEnum.SUCCESS) {
			return ResponseEntity.status(HttpStatus.CREATED).build();
		}
		
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
		
	}
}
