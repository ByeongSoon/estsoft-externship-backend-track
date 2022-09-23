package com.shop.projectlion.web.adminitem.dto;

import com.shop.projectlion.domain.delivery.model.Delivery;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Getter @Setter
public class DeliveryDto {

    private Long deliveryId;
    private String deliveryName;
    private int deliveryFee;

    public static DeliveryDto fromEntity(Delivery delivery) {
        return DeliveryDto.builder()
            .deliveryId(delivery.getDeliveryId())
            .deliveryName(delivery.getDeliveryName())
            .deliveryFee(delivery.getDeliveryFee())
            .build();
    }

}