package com.kbhc.board.api.user.controller;

import com.kbhc.board.api.user.dto.LoginRequest;
import com.kbhc.board.api.user.dto.UserRequest;
import com.kbhc.board.api.user.entity.Member;
import com.kbhc.board.api.user.service.UserService;
import com.kbhc.board.core.model.Response;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/user")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping
    public ResponseEntity<Response<Long>> createUser(
            @Valid @RequestBody UserRequest request) throws Exception {
        Map<String, String> response = new HashMap<>();
        return ResponseEntity.status(HttpStatus.OK).body(userService.createUser(request));
    }

    @PostMapping("/login")
    public ResponseEntity<Response<Boolean>> login(
            @Valid @RequestBody LoginRequest request) throws Exception {
        return ResponseEntity.status(HttpStatus.OK).body(userService.findUser(request));
    }

}
