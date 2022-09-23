package com.shop.projectlion.web.itemdtl.service;

import com.shop.projectlion.domain.delivery.model.Delivery;
import com.shop.projectlion.domain.item.model.Item;
import com.shop.projectlion.domain.item.service.ItemService;
import com.shop.projectlion.domain.itemimage.model.ItemImage;
import com.shop.projectlion.domain.member.model.Member;
import com.shop.projectlion.domain.order.model.Orders;
import com.shop.projectlion.domain.order.service.OrderService;
import com.shop.projectlion.domain.orderitem.service.OrderItemService;
import com.shop.projectlion.global.error.exception.EntityNotFoundException;
import com.shop.projectlion.web.itemdtl.dto.ItemDtlDto;
import com.shop.projectlion.web.itemdtl.dto.OrderDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

import static com.shop.projectlion.global.error.exception.ErrorCode.NO_ITEM_ERROR;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ItemdtlService {

    private final ItemService itemService;

    private final OrderService orderService;

    private final OrderItemService orderItemService;

    public ItemDtlDto getItemDtl(Long itemId) {
        Item findItem = itemService.findById(itemId)
            .orElseThrow(() -> new EntityNotFoundException(NO_ITEM_ERROR));

        Delivery delivery = findItem.getDelivery();
        Integer deliveryFee = delivery.getDeliveryFee();

        List<ItemDtlDto.ItemImageDto> itemImageDtos = findItem.getItemImageList().stream()
            .map(ItemDtlDto.ItemImageDto::from)
            .collect(Collectors.toList());

        return ItemDtlDto.from(findItem, itemImageDtos, deliveryFee);
    }

    @Transactional
    public ResponseEntity<String> order(OrderDto orderDto, Member member) {
        Long itemId = orderDto.getItemId();
        Integer count = orderDto.getCount();

        Item findItem = itemService.findById(itemId)
            .orElseThrow(() -> new EntityNotFoundException(NO_ITEM_ERROR));

        if (findItem.isStockLessThan(count)) {
            String errorMessage = String.format("상품의 재고가 부족합니다. (현재 재고 수량: %d)",findItem.getStockNumber());
            return new ResponseEntity<>(errorMessage, HttpStatus.BAD_REQUEST);
        }

        Orders orders = orderService.register(member);

        findItem.decreaseStock(count);
        orderItemService.register(count, findItem, orders);

        return new ResponseEntity<>(HttpStatus.OK);
    }
}
