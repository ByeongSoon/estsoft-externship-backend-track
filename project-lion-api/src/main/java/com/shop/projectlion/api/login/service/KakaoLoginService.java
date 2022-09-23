package com.shop.projectlion.api.login.service;

import com.shop.projectlion.api.login.client.LoginFeignClient;
import com.shop.projectlion.api.login.dto.KakaoUserInfo;
import com.shop.projectlion.api.login.dto.LoginRequestDto;
import com.shop.projectlion.domain.jwt.dto.TokenDto;
import com.shop.projectlion.domain.jwt.service.TokenManager;
import com.shop.projectlion.domain.member.entity.Member;
import com.shop.projectlion.domain.member.service.MemberService;
import com.shop.projectlion.global.error.exception.BusinessException;
import com.shop.projectlion.global.util.DateTimeUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Date;

import static com.shop.projectlion.domain.member.constant.MemberType.KAKAO;
import static com.shop.projectlion.domain.member.constant.Role.ADMIN;
import static com.shop.projectlion.global.error.exception.ErrorCode.*;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class KakaoLoginService {

    private final TokenManager tokenManager;

    private final MemberService memberService;

    private final LoginFeignClient loginFeignClient;

    @Transactional
    public TokenDto registeredLogin(String authorization, LoginRequestDto loginRequestDto) throws BusinessException{
        if (!KAKAO.equals(loginRequestDto.getMemberType())) {
            throw new BusinessException(INVALID_MEMBER_TYPE);
        }

        KakaoUserInfo response = loginFeignClient.getKakaoUserInfo(authorization);
        String email = response.getKakaoAccount().getEmail();
        String nickname = response.getProperties().get("nickname");

        // todo 가입/업데이트 메서드 분리가 좋은것인가?
        // todo 한 번의 조회를 통해 있다면 업데이트 하는 것이 나은가?
        if (memberService.validAlreadyMember(email)) {
            return updateRefreshInfo(email);
        }
        return register(email, nickname, loginRequestDto);
    }

    public TokenDto register(String email, String name, LoginRequestDto loginRequestDto) {
        Member member = loginRequestDto.toEntity(email, name);
        Member saveMember = Member.createMember(member);

        TokenDto tokenDto = tokenManager.createTokenDto(email, saveMember.getRole());

        updateMemberRefreshToken(saveMember, tokenDto);
        memberService.register(saveMember);

        return tokenDto;
    }

    public TokenDto updateRefreshInfo(String email) {
        TokenDto tokenDto = tokenManager.createTokenDto(email, ADMIN);
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
