package com.shop.projectlion.web.main.dto;

import com.shop.projectlion.domain.item.model.Item;
import com.shop.projectlion.domain.itemimage.model.ItemImage;
import lombok.*;

import java.util.List;

@Builder
@Getter @Setter
public class MainItemDto {

    private Long itemId;

    private String itemName;

    private String itemDetail;

    private String imageUrl;

    private Integer price;


    public static MainItemDto from(Item item) {
        return MainItemDto.builder()
            .itemId(item.getItemId())
            .itemName(item.getItemName())
            .itemDetail(item.getItemDetail())
            .imageUrl(getFirstImageUrl(item.getItemImageList()))
            .price(item.getPrice())
            .build();
    }

    public static String getFirstImageUrl(List<ItemImage> itemImageList) {
        return itemImageList.get(0).getImageName();
    }

}
