package com.kbhc.board.api.user.controller;

import com.kbhc.board.api.user.dto.LoginRequest;
import com.kbhc.board.api.user.dto.LoginResponse;
import com.kbhc.board.api.user.dto.UserRequest;
import com.kbhc.board.api.user.service.UserService;
import com.kbhc.board.core.model.Response;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/user")
@Tag(name = "회원 API", description = "회원 관련 API")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping
    public ResponseEntity<Response<Long>> createUser(
            @Valid @RequestBody UserRequest request) throws Exception {
        return ResponseEntity.status(HttpStatus.OK).body(userService.createUser(request));
    }

    @PostMapping("/login")
    public ResponseEntity<Response<LoginResponse>> login(
            @Valid @RequestBody LoginRequest request) throws Exception {
        return ResponseEntity.status(HttpStatus.OK).body(userService.findUser(request));
    }

}
