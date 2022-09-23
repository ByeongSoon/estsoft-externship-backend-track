package com.shop.projectlion.web.login.service;

import com.shop.projectlion.domain.member.model.Member;
import com.shop.projectlion.domain.member.service.MemberService;
import com.shop.projectlion.web.login.dto.MemberRegisterDto;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class LoginService {

    private final PasswordEncoder passwordEncoder;
    private final MemberService memberService;

    @Transactional
    public void register(MemberRegisterDto memberRegisterDto) {
        Member member = Member.of(
            memberRegisterDto.getName(),
            memberRegisterDto.getEmail(),
            passwordEncoder.encode(memberRegisterDto.getPassword())
        );

        memberService.register(member, memberRegisterDto.getPassword(), memberRegisterDto.getConfirmPassword());
    }

}
