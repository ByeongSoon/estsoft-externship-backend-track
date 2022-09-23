package com.shop.projectlion.global.config.security;

import com.shop.projectlion.domain.member.model.Member;
import com.shop.projectlion.domain.member.service.MemberService;
import com.shop.projectlion.global.error.exception.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    private final MemberService memberService;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Member member = memberService.findMemberByEmail(email)
            .orElseThrow( () -> new UsernameNotFoundException(ErrorCode.LOGIN_ERROR.getMessage()));

        return new CustomUserDetails(member);
    }

}
