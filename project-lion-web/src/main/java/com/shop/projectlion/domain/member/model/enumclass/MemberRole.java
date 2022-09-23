package com.shop.projectlion.domain.member.model.enumclass;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum MemberRole {

    ADMIN(0,"관리자","관리자 권한"),
    USER(1,"회원","일반회원 권한")
    ;

    private Integer id;
    private String title;
    private String description;

}
