package com.kbhc.board.api.user.service;

import com.kbhc.board.api.user.dto.LoginRequest;
import com.kbhc.board.api.user.dto.UserRequest;
import com.kbhc.board.core.model.Response;

public interface UserService {
    Response<Long> createUser(UserRequest request) throws Exception;
    Response<Boolean> findUser(LoginRequest request) throws Exception;

}
