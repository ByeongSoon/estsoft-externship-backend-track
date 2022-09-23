package com.shop.projectlion.domain.member.model.enumclass;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum MemberType {
    GENERAL(0,"일반회원")
    ;

    private Integer id;
    private String title;
}
