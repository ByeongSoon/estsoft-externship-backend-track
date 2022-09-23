package com.shop.projectlion.domain.member.constant;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum MemberType {

    GENERAL(0,"일반회원"),
    KAKAO(1,"카카오회원")
    ;

    private Integer id;
    private String title;
}
