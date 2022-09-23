package com.shop.projectlion.web.itemdtl.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class OrderDto {

    private Long itemId;

    private Integer count;

}
