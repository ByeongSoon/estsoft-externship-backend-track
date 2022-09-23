package com.shop.projectlion.web.adminitem.dto;

import com.shop.projectlion.domain.item.model.Item;
import com.shop.projectlion.domain.itemimage.model.ItemImage;
import com.shop.projectlion.domain.item.model.enumclass.ItemSellStatus;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.List;

@Builder
@Getter @Setter
public class UpdateItemDto {

    private Long itemId;

    @NotBlank(message = "상품명은 필수 입력 값입니다.")
    private String itemName;

    @NotNull(message = "가격은 필수 입력 값입니다.")
    private Integer price;

    @NotBlank(message = "상품 상세는 필수 입력 값입니다.")
    private String itemDetail;

    @NotNull(message = "재고는 필수 입력 값입니다.")
    private Integer stockNumber;

    private ItemSellStatus itemSellStatus;

    @NotNull(message = "배송 정보는 필수 입력 값입니다.")
    private Long deliveryId;

    private List<MultipartFile> itemImageFiles;

    private List<ItemImageDto> itemImageDtos;

    private List<String> originalImageNames;

    public static UpdateItemDto from(Item item, List<ItemImageDto> itemImageDtos) {
        return UpdateItemDto.builder()
            .itemId(item.getItemId())
            .itemName(item.getItemName())
            .price(item.getPrice())
            .itemDetail(item.getItemDetail())
            .stockNumber(item.getStockNumber())
            .itemSellStatus(item.getItemSellStatus())
            .deliveryId(item.getDelivery().getDeliveryId())
            .itemImageDtos(itemImageDtos)
            .build();
    }

    @Builder
    @Getter @Setter
    public static class ItemImageDto {
        private Long itemImageId;
        private String originalImageName;

        public static ItemImageDto from(ItemImage itemImage) {
            return ItemImageDto.builder()
                .itemImageId(itemImage.getItemImageId())
                .originalImageName(itemImage.getOriginalImageName())
                .build();
        }
    }

}