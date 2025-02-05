package com.kbhc.board.api.user.service;

import com.kbhc.board.api.user.dto.LoginRequest;
import com.kbhc.board.api.user.dto.LoginResponse;
import com.kbhc.board.api.user.dto.UserRequest;
import com.kbhc.board.core.model.Response;
import org.springframework.security.core.userdetails.UserDetails;

public interface UserService {
    Response<Long> createUser(UserRequest request) throws Exception;
    Response<LoginResponse> findUser(LoginRequest request) throws Exception;
    UserDetails findUserByEmail(String email);

}
