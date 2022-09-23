package com.shop.projectlion.domain.itemimage.service;

import com.shop.projectlion.domain.item.model.Item;
import com.shop.projectlion.domain.itemimage.model.ItemImage;
import com.shop.projectlion.domain.itemimage.repository.ItemImageRepository;
import com.shop.projectlion.infra.file.UploadFile;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ItemImageService {

    private final ItemImageRepository itemImageRepository;

    @Transactional
    public void registerItemImage(List<UploadFile> uploadFiles, Item item) {

        List<ItemImage> itemImageList = new ArrayList<>();

        for (int i = 0; i < uploadFiles.size(); i++) {
            UploadFile uploadFile = uploadFiles.get(i);

            String storeFileName = (uploadFile == null) ? null : uploadFile.getStoreFileName();
            String fileUploadUrl = (uploadFile == null) ? null : uploadFile.getFileUploadUrl();
            boolean isReqImage = isFirstIndex(i);
            String originalFileName = (uploadFile == null) ? null : uploadFile.getOriginalFileName();

            ItemImage itemImage = ItemImage.of(
                storeFileName,
                fileUploadUrl,
                isReqImage,
                originalFileName,
                item
            );

            itemImageList.add(itemImage);
        }
        itemImageRepository.saveAll(itemImageList);
    }

    @Transactional
    public String updateItemImage(UploadFile uploadFile, Long itemImageId) {
        ItemImage itemImage = itemImageRepository.findById(itemImageId).orElseThrow();
        String deleteImageUrl = itemImage.getImageUrl();
        if (uploadFile != null) {
            itemImage.updateImage(uploadFile.getStoreFileName(),
                uploadFile.getFileUploadUrl(),
                uploadFile.getOriginalFileName());
        } else {
            itemImage.updateImage(null,null,null);
        }

        return deleteImageUrl;
    }

    public boolean isFirstIndex(int index) {
        return index == 0;
    }
}
