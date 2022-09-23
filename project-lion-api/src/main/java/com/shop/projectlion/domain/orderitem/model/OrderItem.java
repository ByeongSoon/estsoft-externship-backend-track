package com.shop.projectlion.domain.orderitem.model;

import com.shop.projectlion.domain.common.BaseEntity;
import com.shop.projectlion.domain.item.model.Item;
import com.shop.projectlion.domain.order.model.Orders;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class OrderItem extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long orderItemId;

    private Integer count;

    private Integer orderPrice;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "item_id")
    private Item item;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id")
    private Orders orders;

    @Builder
    public OrderItem(Integer count, Integer orderPrice, Item item, Orders orders) {
        this.count = count;
        this.orderPrice = orderPrice;
        this.item = item;
        this.orders = orders;
    }

    public static OrderItem of(Integer count, Item item, Orders orders) {
        return OrderItem.builder()
            .count(count)
            .orderPrice(item.getPrice())
            .item(item)
            .orders(orders)
            .build();
    }

}
