package com.kbhc.board.api.user.service;

import com.kbhc.board.api.user.dto.LoginRequest;
import com.kbhc.board.api.user.dto.LoginResponse;
import com.kbhc.board.api.user.dto.UserCacheDTO;
import com.kbhc.board.api.user.dto.UserRequest;
import com.kbhc.board.api.user.entity.Member;
import com.kbhc.board.api.user.repository.MemberRepository;
import com.kbhc.board.core.model.Response;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

import static com.kbhc.board.core.enums.Message.*;
import static com.kbhc.board.core.util.CommonUtil.encodeLoginInfo;

@Service
@Slf4j
public class UserServiceImpl implements UserService {

    private final MemberRepository memberRepository;
    private final BCryptPasswordEncoder passwordEncoder;
    private final RedisTemplate<String, Object> redisTemplate;

    public UserServiceImpl(MemberRepository memberRepository
            , RedisTemplate<String, Object> redisTemplate) {
        this.memberRepository = memberRepository;
        this.passwordEncoder = new BCryptPasswordEncoder();
        this.redisTemplate = redisTemplate;
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

        UserCacheDTO cachedMember = (UserCacheDTO) redisTemplate.opsForValue().get("user::" + email);

        if (Objects.nonNull(cachedMember)) {
            return new User(cachedMember.getEmail(), cachedMember.getPassword(),
                    Collections.singletonList(new SimpleGrantedAuthority("ROLE_" + cachedMember.getRole()))
            );
        }

        Member member = memberRepository.findByEmail(email);

        if (Objects.isNull(member)) {
            throw new UsernameNotFoundException("User not found");
        }

        redisTemplate.opsForValue().set("user::" + email, new UserCacheDTO(member), 30, TimeUnit.MINUTES);

        return new User(member.getEmail(), member.getPassword(),
                Collections.singletonList(new SimpleGrantedAuthority("ROLE_" + member.getRole())));
    }

}
