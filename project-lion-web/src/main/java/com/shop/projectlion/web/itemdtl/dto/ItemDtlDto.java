package com.shop.projectlion.web.itemdtl.dto;

import com.shop.projectlion.domain.delivery.model.Delivery;
import com.shop.projectlion.domain.item.model.Item;
import com.shop.projectlion.domain.item.model.enumclass.ItemSellStatus;
import com.shop.projectlion.domain.itemimage.model.ItemImage;
import lombok.*;

import java.util.List;
import java.util.stream.Collectors;

@Getter @Setter
@Builder
public class ItemDtlDto {

    private Long itemId;

    private String itemName;

    private Integer price;

    private String itemDetail;

    private Integer stockNumber;

    private ItemSellStatus itemSellStatus;

    private Integer deliveryFee;

    private List<ItemImageDto> itemImageDtos;

    public static ItemDtlDto from(Item item, List<ItemImageDto> itemImageDtos, Integer deliveryFee) {
        return ItemDtlDto.builder()
            .itemId(item.getItemId())
            .itemName(item.getItemName())
            .price(item.getPrice())
            .itemDetail(item.getItemDetail())
            .stockNumber(item.getStockNumber())
            .itemSellStatus(item.getItemSellStatus())
            .deliveryFee(deliveryFee)
            .itemImageDtos(itemImageDtos)
            .build();
    }

    @Getter @Setter
    @Builder @AllArgsConstructor
    public static class ItemImageDto {
        private String imageUrl;

        public static ItemImageDto from(ItemImage itemImage) {
            return ItemImageDto.builder()
                .imageUrl(itemImage.getImageName())
                .build();
        }

    }
}
