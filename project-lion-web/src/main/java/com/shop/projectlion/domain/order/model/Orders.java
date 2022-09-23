package com.shop.projectlion.domain.order.model;

import com.shop.projectlion.domain.base.BaseEntity;
import com.shop.projectlion.domain.delivery.model.Delivery;
import com.shop.projectlion.domain.member.model.Member;
import com.shop.projectlion.domain.order.model.enumclass.OrderStatus;
import com.shop.projectlion.domain.orderitem.model.OrderItem;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.List;

import static com.shop.projectlion.domain.order.model.enumclass.OrderStatus.CANCEL;
import static com.shop.projectlion.domain.order.model.enumclass.OrderStatus.ORDER;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Orders extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long orderId;

    @Enumerated(EnumType.STRING)
    private OrderStatus orderStatus;

    @CreatedDate
    private LocalDateTime orderTime;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "orders", cascade = CascadeType.ALL)
    private List<OrderItem> orderItemList;

    @Builder
    public Orders(OrderStatus orderStatus, Member member) {
        this.orderStatus = orderStatus;
        this.member = member;
    }

    public static Orders of(Member member) {
        return Orders.builder()
            .orderStatus(ORDER)
            .member(member)
            .build();
    }

    public void cancel() {
        this.orderStatus = CANCEL;
    }

    public Integer getTotalPrice(List<Delivery> deliveryList) {
        int totalPrice = 0;
        for (OrderItem orderItem: orderItemList) {
            totalPrice += (orderItem.getOrderPrice() * orderItem.getCount());
        }

        return totalPrice + getTotalDeliveryFee(deliveryList);
    }

    public Integer getTotalDeliveryFee(List<Delivery> deliveryList) {
        int totalFee = 0;
        for (Delivery delivery: deliveryList) {
            totalFee += delivery.getDeliveryFee();
        }

        return totalFee;
    }

}
