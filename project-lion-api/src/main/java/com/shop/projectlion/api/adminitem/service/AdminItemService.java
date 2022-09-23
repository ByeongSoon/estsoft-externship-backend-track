package com.shop.projectlion.api.adminitem.service;

import com.shop.projectlion.api.adminitem.dto.ItemDtlDto;
import com.shop.projectlion.api.adminitem.dto.UpdateItemDto;
import com.shop.projectlion.domain.delivery.model.Delivery;
import com.shop.projectlion.domain.delivery.service.DeliveryService;
import com.shop.projectlion.domain.item.model.Item;
import com.shop.projectlion.domain.item.service.ItemService;
import com.shop.projectlion.global.error.exception.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

import static com.shop.projectlion.global.error.exception.ErrorCode.ITEM_NOT_EXISTS;


@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class AdminItemService {

    private final ItemService itemService;

    private final DeliveryService deliveryService;

    public ItemDtlDto getItemDtlDto(Long itemId) {
        Item findItem = itemService.findById(itemId)
            .orElseThrow(() -> new EntityNotFoundException(ITEM_NOT_EXISTS));
        Delivery delivery = findItem.getDelivery();

        List<ItemDtlDto.ItemImageDto> itemImageDtos = findItem.getItemImageList().stream()
            .map(ItemDtlDto.ItemImageDto::from)
            .collect(Collectors.toList());

        return ItemDtlDto.from(findItem, itemImageDtos, delivery);
    }

    @Transactional
    public UpdateItemDto.Response getUpdateResponseDto(UpdateItemDto.Request dto) {
        Item item = itemService.findById(dto.getItemId())
            .orElseThrow(() -> new EntityNotFoundException(ITEM_NOT_EXISTS));;
        Delivery delivery = deliveryService.findById(dto.getDeliveryId());

        item.updateItem(dto.getItemName(), dto.getItemDetail(),
            dto.getItemSellStatus(), dto.getPrice(),
            dto.getStockNumber(), delivery);

        return UpdateItemDto.Response.from(item);
    }
}
