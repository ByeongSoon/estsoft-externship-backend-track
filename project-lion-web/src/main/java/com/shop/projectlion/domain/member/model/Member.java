package com.shop.projectlion.domain.member.model;

import com.shop.projectlion.domain.delivery.model.Delivery;
import com.shop.projectlion.domain.item.model.Item;
import com.shop.projectlion.domain.base.BaseEntity;
import com.shop.projectlion.domain.member.model.enumclass.MemberRole;
import com.shop.projectlion.domain.member.model.enumclass.MemberType;
import com.shop.projectlion.domain.order.model.Orders;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Builder
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PACKAGE)
public class Member extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long memberId;

    @NotEmpty
    @Column(name = "email", unique = true, length = 50)
    private String email;

    @NotEmpty
    @Column(length = 20)
    private String memberName;

    @NotNull
    @Column(length = 10)
    @Enumerated(EnumType.STRING)
    private MemberType memberType;

    @NotEmpty
    @Column(length = 200)
    private String password;

    @Column(length = 250)
    private String refreshToken;

    @NotNull
    @Column(length = 20)
    @Enumerated(EnumType.STRING)
    private MemberRole role;

    private LocalDateTime tokenExpirationTime;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "member")
    private List<Item> itemList;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "member")
    private List<Delivery> deliveryList;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "member")
    private List<Orders> ordersList;

    private Member(String memberName, String email, String password) {
        this.memberName = memberName;
        this.email = email;
        this.password = password;
        this.memberType = MemberType.GENERAL;
        this.role = MemberRole.ADMIN;
        this.refreshToken = null;
        this.tokenExpirationTime = null;
    }

    // 정적 팩토리 메서드
    public static Member of(String memberName, String email, String password) {
        return new Member(memberName, email, password);
    }

}
