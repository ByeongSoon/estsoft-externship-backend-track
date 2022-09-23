package com.shop.projectlion.web.adminitem.service;

import com.shop.projectlion.domain.delivery.model.Delivery;
import com.shop.projectlion.domain.item.model.Item;
import com.shop.projectlion.domain.itemimage.model.ItemImage;
import com.shop.projectlion.domain.delivery.service.DeliveryService;
import com.shop.projectlion.domain.itemimage.service.ItemImageService;
import com.shop.projectlion.domain.item.service.ItemService;
import com.shop.projectlion.domain.member.model.Member;
import com.shop.projectlion.global.error.exception.EntityNotFoundException;
import com.shop.projectlion.global.error.exception.ErrorCode;
import com.shop.projectlion.infra.FileService;
import com.shop.projectlion.infra.UploadFile;
import com.shop.projectlion.web.adminitem.dto.DeliveryDto;
import com.shop.projectlion.web.adminitem.dto.InsertItemDto;
import com.shop.projectlion.web.adminitem.dto.UpdateItemDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class AdminItemService {

    private final DeliveryService deliveryService;

    private final ItemService itemService;

    private final FileService fileService;

    private final ItemImageService itemImageService;

    public List<DeliveryDto> findDeliveryByMember(Member member) {
        List<Delivery> findDeliveries = deliveryService.findByMember(member)
            .orElse(new ArrayList<Delivery>());

        return findDeliveries.stream()
            .map(DeliveryDto::fromEntity)
            .collect(Collectors.toList());
    }

    @Transactional
    public Item registerItem(InsertItemDto dto, Member member) throws IOException {
        Delivery delivery = deliveryService.findById(dto.getDeliveryId());

        Item item = dto.toEntity();
        Item newItem = Item.createItem(item,member,delivery);

        List<UploadFile> uploadFiles = fileService.storeFiles(dto.getItemImageFiles());
        Item savedItem = itemService.register(newItem);
        itemImageService.registerItemImage(uploadFiles,newItem);

        return savedItem;
    }

    public Item findItemById(Long itemId) throws EntityNotFoundException{
        return itemService.findById(itemId)
            .orElseThrow(() -> new EntityNotFoundException(ErrorCode.NO_ITEM_ERROR));
    }

    @Transactional
    public void updateItem(UpdateItemDto dto) throws IOException, EntityNotFoundException {
        Item item = itemService.findById(dto.getItemId())
            .orElseThrow(() -> new EntityNotFoundException(ErrorCode.NO_ITEM_ERROR));;
        Delivery delivery = deliveryService.findById(dto.getDeliveryId());

        item.updateItem(dto.getItemName(), dto.getItemDetail(),
            dto.getItemSellStatus(), dto.getPrice(),
            dto.getStockNumber(), delivery);

        List<MultipartFile> files = dto.getItemImageFiles();

        // 수정한 이미지가 있을때만 없으면 상품 정보만 수정
        if (hasUpdateImage(files)) {
            List<UploadFile> uploadFiles = fileService.storeFiles(files);
            List<ItemImage> itemImageList = item.getItemImageList();

            for (int i = 0; i < uploadFiles.size(); i++) {
                String deleteImageUrl = itemImageService.updateItemImage(uploadFiles.get(i), itemImageList.get(i).getItemImageId());
                if (deleteImageUrl != null) {
                    fileService.deleteFile(deleteImageUrl);
                }
            }
        }
    }

    public boolean hasUpdateImage(List<MultipartFile> files) {
        boolean isUpdate = false;

        for (MultipartFile file: files) {
            if (!"".equals(file.getOriginalFilename())){
                isUpdate = true;
            }
        }

        return isUpdate;
    }

    private List<UpdateItemDto.ItemImageDto> getItemImageDtoFromItem(Item item) {
        List<ItemImage> itemImageList = item.getItemImageList();

        List<UpdateItemDto.ItemImageDto> itemImageDtos = itemImageList.stream()
            .map(UpdateItemDto.ItemImageDto::from)
            .collect(Collectors.toList());

        return itemImageDtos;
    }

    public UpdateItemDto getUpdateItemDto(Long itemId) {
        Item findItem = findItemById(itemId);

        List<UpdateItemDto.ItemImageDto> itemImageDtos = getItemImageDtoFromItem(findItem);

        return UpdateItemDto.from(findItem, itemImageDtos);
    }

    public List<UpdateItemDto.ItemImageDto> getItemImageDtos(Long itemId) {
        Item findItem = findItemById(itemId);

        List<UpdateItemDto.ItemImageDto> itemImageDtos = getItemImageDtoFromItem(findItem);

        return itemImageDtos;
    }
}
