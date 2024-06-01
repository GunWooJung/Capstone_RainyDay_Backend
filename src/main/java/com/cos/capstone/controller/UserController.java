package com.cos.capstone.controller;

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
import com.cos.capstone.dto.UserRequestDto;
import com.cos.capstone.enumlist.ResultCodeEnum;
import com.cos.capstone.model.User;
import com.cos.capstone.service.UserService;

@RestController
public class UserController {

	@Autowired
	private UserService userService;

	@GetMapping("/user/{id}")
	public ResponseEntity<User> userInfo(@PathVariable long id) {

		User user = userService.userInfo(id);

		if (user != null) {
			return ResponseEntity.status(HttpStatus.OK).body(user);
		} 
		
		return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
	}
	 

	@PostMapping("/user/login") // 로그인 요청
	public ResponseEntity<User> userLogin(@RequestBody UserRequestDto userRequestDto) {

		long userIdResult = userService.userLogin(userRequestDto);
		

		if (userIdResult >= 0) { // id랑 비밀번호 일치 성공
			User user = userService.userInfo(userIdResult);
			return ResponseEntity.status(HttpStatus.OK).body(user);
		} else if (userIdResult == -1) { // id랑 비밀번호 일치 실패
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
		}
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
	}
	
	@PutMapping("/user/{userId}/password") // 비밀번호 변경
	public ResponseEntity<String> userChangePassword(@PathVariable long userId, 
			@RequestBody UserRequestDto userRequestDto) {
		
		String password = userRequestDto.getPassword();
		ResultCodeEnum resultCode = userService.userChangePassword(userId, password);
		
		if (resultCode == ResultCodeEnum.SUCCESS) { // id랑 비밀번호 일치 성공
			return ResponseEntity.status(HttpStatus.OK).build();
		} 
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
	}


	@PostMapping("/user") // user 생성 요청
	public ResponseEntity<String> userCreate(@RequestBody UserRequestDto userRequestDto) {


		ResultCodeEnum loginResultCode = userService.userCreate(userRequestDto);

		if (loginResultCode == ResultCodeEnum.SUCCESS) {
			return ResponseEntity.status(HttpStatus.CREATED).build();
		} else if (loginResultCode == ResultCodeEnum.EXIST_ID) {
			return ResponseEntity.status(HttpStatus.CONFLICT).body("id");
		} else if (loginResultCode == ResultCodeEnum.EXIST_NAME) {
			return ResponseEntity.status(HttpStatus.CONFLICT).body("name");
		}

		return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
	}

	
	@DeleteMapping("/user/{id}")
	public ResponseEntity<String> userDelete(@PathVariable long id) {

		ResultCodeEnum loginResultCode = userService.userDelete(id);

		if (loginResultCode == ResultCodeEnum.SUCCESS) {
			return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
		} else if (loginResultCode == ResultCodeEnum.NOTFOUND) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
		}
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
	}

}
