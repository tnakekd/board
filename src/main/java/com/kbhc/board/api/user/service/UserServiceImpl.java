package com.kbhc.board.api.user.service;

import com.kbhc.board.api.user.dto.LoginRequest;
import com.kbhc.board.api.user.dto.UserRequest;
import com.kbhc.board.api.user.entity.Member;
import com.kbhc.board.api.user.repository.MemberRepository;
import com.kbhc.board.core.model.Response;
import com.kbhc.board.core.util.CommonUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Objects;

import static com.kbhc.board.core.enums.Message.*;

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
        Member member = memberRepository.save(new Member(request));
        return Response.success(SUCCESS_USER_SAVE.getValue(), member.getId());
    }

    @Override
    public Response<Boolean> findUser(LoginRequest request) throws Exception {
        Member member = memberRepository.findByEmail(request.getEmail());

        if (Objects.isNull(member)) {
            return Response.failure(FAILURE_USER_NOT_FOUND.getValue());
        }

        boolean isMatch = passwordEncoder.matches(request.getPassword(), member.getPassword());

        if (isMatch) {
            return Response.success(SUCCESS_USER_LOGIN.getValue(), true);
        }

        return Response.failure(FAILURE_USER_PASSWORD_MISMATCH.getValue());
    }

}
