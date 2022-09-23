package com.shop.projectlion.api.login.dto;

import com.shop.projectlion.domain.member.constant.MemberType;
import com.shop.projectlion.domain.member.entity.Member;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import static com.shop.projectlion.domain.member.constant.Role.ADMIN;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class LoginRequestDto {

    private MemberType memberType;

    public Member toEntity(String email, String nickname){
        return Member.builder()
            .email(email)
            .memberName(nickname)
            .role(ADMIN)
            .memberType(memberType)
            .build();
    }

}
