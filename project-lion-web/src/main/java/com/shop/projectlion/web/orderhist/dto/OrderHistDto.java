package com.shop.projectlion.web.orderhist.dto;

import com.shop.projectlion.domain.delivery.model.Delivery;
import com.shop.projectlion.domain.item.model.Item;
import com.shop.projectlion.domain.itemimage.model.ItemImage;
import com.shop.projectlion.domain.order.model.Orders;
import com.shop.projectlion.domain.order.model.enumclass.OrderStatus;
import com.shop.projectlion.domain.orderitem.model.OrderItem;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Getter @Setter
@Builder
public class OrderHistDto {

    private Long orderId;
    private LocalDateTime orderTime;
    private OrderStatus orderStatus;
    private int totalPrice;
    private int totalDeliveryFee;

    private List<OrderItemHistDto> orderItemHistDtos;

    public static OrderHistDto from(Orders orders, List<Delivery> deliveryList) {
        return OrderHistDto.builder()
            .orderId(orders.getOrderId())
            .orderTime(orders.getOrderTime())
            .orderStatus(orders.getOrderStatus())
            .totalPrice(orders.getTotalPrice(deliveryList))
            .totalDeliveryFee(orders.getTotalDeliveryFee(deliveryList))
            .build();
    }

    @Getter @Setter
    @Builder
    public static class OrderItemHistDto {
        private String itemName;
        private int count;
        private int orderPrice;
        private String imageUrl;

        public static OrderItemHistDto from(OrderItem orderItem, Item item) {
            return OrderItemHistDto.builder()
                .itemName(item.getItemName())
                .count(orderItem.getCount())
                .orderPrice(orderItem.getOrderPrice())
                .imageUrl(getFirstImageUrl(item.getItemImageList()))
                .build();
        }

        private static String getFirstImageUrl(List<ItemImage> itemImageList) {
            return itemImageList.get(0).getImageName();
        }

    }

}