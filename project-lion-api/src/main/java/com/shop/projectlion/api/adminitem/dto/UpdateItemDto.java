package com.shop.projectlion.api.adminitem.dto;

import com.shop.projectlion.domain.item.model.Item;
import com.shop.projectlion.domain.item.model.enumclass.ItemSellStatus;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

public class UpdateItemDto {

    @Getter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    @ApiModel(value = "item update api request", description = "상품 정보 수정 API 호출 시 request 모델")
    public static class Request{
        @ApiModelProperty(value = "상품 ID", required = true, example = "1")
        @NotNull
        private Long itemId;

        @ApiModelProperty(value = "상풍명", required = true, example = "테스트 상품01")
        @NotBlank
        private String itemName;

        @ApiModelProperty(value = "상품 가격", required = true, example = "30000")
        @NotNull
        private Integer price;

        @ApiModelProperty(value = "상품 상세", required = true, example = "테스트 상품 상세")
        @NotBlank
        private String itemDetail;

        @ApiModelProperty(value = "재고", example = "999")
        @NotNull
        private Integer stockNumber;

        @ApiModelProperty(value = "판매 상태", required = true, example = "SELL")
        @NotNull
        private ItemSellStatus itemSellStatus;

        @ApiModelProperty(value = "배송 정보 ID", example = "3")
        @NotNull
        private Long deliveryId;
    }

    @Getter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    @ApiModel(value = "item update api response", description = "상품 정보 수정 API 호출 시 response 모델")
    public static class Response{
        @ApiModelProperty(value = "상품 ID", required = true, example = "1")
        private Long itemId;

        @ApiModelProperty(value = "상풍명", required = true, example = "테스트 상품01")
        private String itemName;

        @ApiModelProperty(value = "상품 가격", required = true, example = "30000")
        private Integer price;

        @ApiModelProperty(value = "상품 상세", required = true, example = "테스트 상품 상세")
        private String itemDetail;

        @ApiModelProperty(value = "재고", example = "999")
        private Integer stockNumber;

        @ApiModelProperty(value = "판매 상태", required = true, example = "SELL")
        private ItemSellStatus itemSellStatus;

        @ApiModelProperty(value = "배송 정보 ID", example = "3")
        private Long deliveryId;


        public static UpdateItemDto.Response from(Item item) {
            return UpdateItemDto.Response.builder()
                .itemId(item.getItemId())
                .itemName(item.getItemName())
                .price(item.getPrice())
                .itemDetail(item.getItemDetail())
                .stockNumber(item.getStockNumber())
                .itemSellStatus(item.getItemSellStatus())
                .deliveryId(item.getDelivery().getDeliveryId())
                .build();
        }
    }



}
