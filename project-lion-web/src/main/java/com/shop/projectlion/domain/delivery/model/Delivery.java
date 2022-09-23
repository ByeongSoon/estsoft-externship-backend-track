package com.shop.projectlion.domain.delivery.model;

import com.shop.projectlion.domain.base.BaseEntity;
import com.shop.projectlion.domain.item.model.Item;
import com.shop.projectlion.domain.member.model.Member;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Delivery extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long deliveryId;

    @Column(length = 11)
    @NotNull
    private Integer deliveryFee;

    @Column(length = 50)
    @NotNull
    private String deliveryName;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id", nullable = false)
    private Member member;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "delivery")
    private List<Item> itemList;

}
