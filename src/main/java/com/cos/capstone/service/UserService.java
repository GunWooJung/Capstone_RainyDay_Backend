package com.cos.capstone.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cos.capstone.dto.UserRequestDto;
import com.cos.capstone.enumlist.DBState;
import com.cos.capstone.enumlist.ResultCodeEnum;
import com.cos.capstone.model.User;
import com.cos.capstone.repository.UserRepository;

@Service
public class UserService {

	@Autowired
	private UserRepository useRepository;

	@Transactional(readOnly = true)
	public User userInfo(long user_id) {

		Optional<User> optional_user = useRepository.findById(user_id);

		if (optional_user.isPresent()) {
			User user = optional_user.get();
			if (user.getDBState() == DBState.NOT_USE.getValue()) {
				return null;
			}
			user.setLoginPassword(null);
			return user;
		}

		return null;
	}

	@Transactional(readOnly = true)
	public long userLogin(UserRequestDto userRequestDto) {
		
		String loginId = userRequestDto.getId();
		String loginPassword = userRequestDto.getPassword();

		Optional<User> optional_user = useRepository.findByLoginId(loginId);

		if (optional_user.isPresent()) {
			User user = optional_user.get();
			if (user.getDBState() == DBState.NOT_USE.getValue()) {
				return -1;
			} else if (user.getLoginPassword().equals(loginPassword)) {
				return user.getUserId();
			} else if (!user.getLoginPassword().equals(loginPassword)) {
				return -1;
			}
		}
		// login id가 존재 하지 않는 경우
		return -1;
	}

	@Transactional
	public ResultCodeEnum userCreate(UserRequestDto userRequestDto) {

		String loginId = userRequestDto.getId();
		String loginPassword = userRequestDto.getPassword();
		String username = userRequestDto.getName();
		
		Optional<User> optional_user = useRepository.findByLoginId(loginId);
		Optional<User> optional_user_name = useRepository.findByUserName(username);

		if (optional_user_name.isPresent()) {
			// 이미 등록된 id가 존재
			return ResultCodeEnum.EXIST_NAME;
		} 
		if (optional_user.isPresent()) {
			// 이미 등록된 id가 존재
			return ResultCodeEnum.EXIST_ID;
		} else {
			// 새로운 user을 등록
			User user = new User();
			user.setLoginId(loginId);
			user.setLoginPassword(loginPassword);
			user.setUserName(username);
			user.setDBState(DBState.USE.getValue());
			useRepository.save(user);
			return ResultCodeEnum.SUCCESS;
		}

	}
	
	@Transactional
	public ResultCodeEnum userChangePassword(long id, String password) {

		Optional<User> optional_user = useRepository.findById(id);

		if (optional_user.isPresent()) {
			User user = optional_user.get();
			
			if(user.getDBState() == DBState.NOT_USE.getValue()) {
				return ResultCodeEnum.FAILURE;
			}
			user.setLoginPassword(password);
			useRepository.save(user);
			return ResultCodeEnum.SUCCESS;
		} 
		return ResultCodeEnum.FAILURE;
	}
	

	@Transactional
	public ResultCodeEnum userDelete(long id) {
		
		Optional<User> optional_user = useRepository.findById(id);

		if (optional_user.isPresent()) {
			// 등록된 id가 존재
			User user = optional_user.get();
			if(user.getDBState() == DBState.NOT_USE.getValue()) {
				return ResultCodeEnum.NOTFOUND;
			}
			user.setDBState(DBState.NOT_USE.getValue());
			useRepository.save(user);
			return ResultCodeEnum.SUCCESS;
		}
	// 등록된 id가 없음
	return ResultCodeEnum.NOTFOUND;
}

}
