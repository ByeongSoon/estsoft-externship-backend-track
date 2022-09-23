package com.shop.projectlion.api.login;

import com.shop.projectlion.api.login.client.LoginFeignClient;
import com.shop.projectlion.api.login.dto.LoginRequestDto;
import com.shop.projectlion.domain.jwt.dto.TokenDto;
import com.shop.projectlion.domain.jwt.service.TokenManager;
import com.shop.projectlion.domain.member.entity.Member;
import com.shop.projectlion.domain.member.service.MemberService;
import com.shop.projectlion.global.error.exception.BusinessException;
import com.shop.projectlion.global.util.DateTimeUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Date;

import static com.shop.projectlion.domain.member.constant.Role.USER;
import static com.shop.projectlion.global.error.exception.ErrorCode.MEMBER_NOT_EXISTS;

@Component
@RequiredArgsConstructor
public abstract class BaseLoginService {
    // todo 다형성 위해 추상클래스 말고 인터페이스로 공통된 기능만 묶는 쪽으로 변경

    protected  TokenManager tokenManager;

    protected  MemberService memberService;

    protected  LoginFeignClient loginFeignClient;

    abstract public TokenDto registeredLogin(HttpHeaders header, LoginRequestDto loginRequestDto) throws BusinessException;

    @Transactional
    public TokenDto register(String email, String name, LoginRequestDto loginRequestDto) {
        Member member = loginRequestDto.toEntity(email, name);
        Member saveMember = Member.createMember(member);

        TokenDto tokenDto = tokenManager.createTokenDto(email, saveMember.getRole());

        updateMemberRefreshToken(saveMember, tokenDto);
        memberService.register(saveMember);

        return tokenDto;
    }

    public TokenDto updateRefreshInfo(String email) {
        TokenDto tokenDto = tokenManager.createTokenDto(email, USER);
        Member findMember = memberService.findMemberByEmail(email)
            .orElseThrow(() -> new BusinessException(MEMBER_NOT_EXISTS));
        updateMemberRefreshToken(findMember, tokenDto);

        return tokenDto;
    }

    private void updateMemberRefreshToken(Member member, TokenDto tokenDto) {
        String refreshToken = tokenDto.getRefreshToken();
        Date expireTime = tokenDto.getRefreshTokenExpireTime();
        LocalDateTime refreshTokenExpireTime = DateTimeUtils.convertToLocalDateTime(expireTime);

        member.updateRefreshToken(refreshToken, refreshTokenExpireTime);
    }


}
