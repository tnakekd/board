package com.kbhc.board.api.user.service;

import com.kbhc.board.api.user.dto.LoginRequest;
import com.kbhc.board.api.user.dto.LoginResponse;
import com.kbhc.board.api.user.dto.UserRequest;
import com.kbhc.board.api.user.entity.Member;
import com.kbhc.board.api.user.repository.MemberRepository;
import com.kbhc.board.core.model.Response;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.Objects;

import static com.kbhc.board.core.enums.Message.*;
import static com.kbhc.board.core.util.CommonUtil.encodeLoginInfo;

@Service
@Slf4j
public class UserServiceImpl implements UserService {

    private final MemberRepository memberRepository;
    private final BCryptPasswordEncoder passwordEncoder;

    public UserServiceImpl(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
        this.passwordEncoder = new BCryptPasswordEncoder();
    }

    @Override
    public Response<Long> createUser(UserRequest request) throws Exception {
        if (memberRepository.existsByEmail(request.getEmail())) {
            return Response.failure(FAILURE_USER_DUPLICATE_EMAIL.getValue());
        }
        Member member = memberRepository.save(new Member(request));
        return Response.success(SUCCESS_USER_SAVE.getValue(), member.getId());
    }

    @Override
    public Response<LoginResponse> findUser(LoginRequest request) throws Exception {
        Member member = memberRepository.findByEmail(request.getEmail());

        if (Objects.isNull(member)) {
            return Response.failure(FAILURE_USER_NOT_FOUND.getValue());
        }

        boolean isMatch = passwordEncoder.matches(request.getPassword(), member.getPassword());

        if (isMatch) {
            String encoded = encodeLoginInfo(member.getEmail(), request.getPassword());
            return Response.success(SUCCESS_USER_LOGIN.getValue(), new LoginResponse(encoded));
        }

        return Response.failure(FAILURE_USER_PASSWORD_MISMATCH.getValue());
    }

    @Override
    public UserDetails findUserByEmail(String email) {
        Member member = memberRepository.findByEmail(email);

        if (Objects.isNull(member)) {
            throw new UsernameNotFoundException("User not found");
        }

        return new User(member.getEmail(), member.getPassword(),
                Collections.singletonList(new SimpleGrantedAuthority("ROLE_" + member.getRole())));
    }

}
