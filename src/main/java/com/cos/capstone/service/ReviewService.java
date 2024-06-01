package com.cos.capstone.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cos.capstone.dto.AlarmDto;
import com.cos.capstone.dto.ReviewDto;
import com.cos.capstone.enumlist.DBState;
import com.cos.capstone.enumlist.ResultCodeEnum;
import com.cos.capstone.model.AlarmMessage;
import com.cos.capstone.model.Review;
import com.cos.capstone.model.User;
import com.cos.capstone.repository.AlarmRepository;
import com.cos.capstone.repository.ReviewRepository;
import com.cos.capstone.repository.UserRepository;

@Service
public class ReviewService {

	@Autowired
	private ReviewRepository reviewRepository;
	
	@Autowired
	private UserRepository userRepository;

	@Transactional(readOnly = true)
	public Review getReview() {
		
		List<Review> reviews = reviewRepository.findAll();
		if(reviews.isEmpty()) {
			return null;
		}
		  // 리스트의 크기
        int size = reviews.size();

        // Random 객체 생성
        Random random = new Random();

        // 0부터 리스트의 크기 - 1까지의 랜덤 정수 생성
        int randomNumber = random.nextInt(size);
		Review review = reviews.get(randomNumber);
		return review;
	}

	@Transactional
	public ResultCodeEnum reviewCreate(Long userId, ReviewDto reviewDto) {
		
		Optional<User> user = userRepository.findById(userId);
		
		if(user.isPresent()) {
			Review review = new Review();
			review.setUserId(user.get());
			review.setLocation(reviewDto.getLocation());
			review.setContents(reviewDto.getContents());
			review.setDBState(DBState.USE.getValue());
			review.setReceviedTime(LocalDateTime.now());
			reviewRepository.save(review);
			return ResultCodeEnum.SUCCESS;
		}
		
		return ResultCodeEnum.FAILURE;
	}
	
}
