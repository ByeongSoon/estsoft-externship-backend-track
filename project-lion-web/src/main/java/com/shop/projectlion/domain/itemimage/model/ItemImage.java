package com.shop.projectlion.domain.itemimage.model;


import com.shop.projectlion.domain.base.BaseEntity;
import com.shop.projectlion.domain.item.model.Item;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@Getter
@Builder
@AllArgsConstructor(access = AccessLevel.PACKAGE)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ItemImage extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long itemImageId;

    @Column(length = 500)
    private String imageName;

    @Column(length = 500)
    private String imageUrl;

    @Column
    @NotNull
    private boolean isReqImage;

    @Column(length = 200)
    private String originalImageName;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "item_id")
    @NotNull
    private Item item;

    public void setReqImage(boolean isReqImage) {
        this.isReqImage = isReqImage;
    }

    public void updateImage(String imageName, String imageUrl, String originalImageName) {
        this.imageName = imageName;
        this.imageUrl = imageUrl;
        this.originalImageName = originalImageName;
    }

    public static ItemImage of(String imageName, String imageUrl, boolean isReqImage,
                               String originalImageName, Item item) {
        return ItemImage.builder()
            .imageName(imageName)
            .imageUrl(imageUrl)
            .isReqImage(isReqImage)
            .originalImageName(originalImageName)
            .item(item)
            .build();
    }

}
