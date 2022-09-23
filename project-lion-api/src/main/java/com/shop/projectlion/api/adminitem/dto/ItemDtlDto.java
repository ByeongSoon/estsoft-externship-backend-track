package com.shop.projectlion.api.adminitem.dto;

import com.shop.projectlion.domain.delivery.model.Delivery;
import com.shop.projectlion.domain.item.model.Item;
import com.shop.projectlion.domain.item.model.enumclass.ItemSellStatus;
import com.shop.projectlion.domain.itemimage.model.ItemImage;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter @Setter
@Builder
public class ItemDtlDto {

    private Long itemId;

    private String itemName;

    private Integer price;

    private String itemDetail;

    private Integer stockNumber;

    private ItemSellStatus itemSellStatus;

    private Long deliveryId;

    private Integer deliveryFee;

    private List<ItemImageDto> itemImageDtos;

    public static ItemDtlDto from(Item item, List<ItemImageDto> itemImageDtos, Delivery delivery) {
        return ItemDtlDto.builder()
            .itemId(item.getItemId())
            .itemName(item.getItemName())
            .price(item.getPrice())
            .itemDetail(item.getItemDetail())
            .stockNumber(item.getStockNumber())
            .itemSellStatus(item.getItemSellStatus())
            .deliveryId(delivery.getDeliveryId())
            .deliveryFee(delivery.getDeliveryFee())
            .itemImageDtos(itemImageDtos)
            .build();
    }

    @Getter @Setter
    @Builder @AllArgsConstructor
    public static class ItemImageDto {
        private String imageUrl;

        private static final String IMAGE_PATH = "/images/";

        public static ItemImageDto from(ItemImage itemImage) {
            return ItemImageDto.builder()
                .imageUrl(hasImage(itemImage.getImageName()) ? "" : IMAGE_PATH + itemImage.getImageName())
                .build();
        }

        private static boolean hasImage(String imageUrl) {
            return imageUrl == null;
        }

    }
}
