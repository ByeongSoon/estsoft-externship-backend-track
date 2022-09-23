package com.shop.projectlion.api.adminitem;

import com.shop.projectlion.domain.item.model.Item;
import com.shop.projectlion.domain.item.model.enumclass.ItemSellStatus;
import com.shop.projectlion.domain.item.repository.ItemRepository;
import com.shop.projectlion.domain.item.service.ItemService;
import com.shop.projectlion.global.error.exception.BusinessException;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.context.ActiveProfiles;

import java.util.Optional;

import static com.shop.projectlion.global.error.exception.ErrorCode.ITEM_NOT_EXISTS;

@ActiveProfiles("test")
@ExtendWith(MockitoExtension.class)
public class UpdateItemServiceTeat {

    @InjectMocks
    ItemService itemService;

    @Mock
    ItemRepository itemRepository;

    @Test
    void findByItem() {
        // given
        Long itemId = 1L;
        Item item = Item.builder()
            .itemName("상품명")
            .itemSellStatus(ItemSellStatus.SOLD_OUT)
            .itemDetail("상품 상세")
            .price(5000)
            .stockNumber(50)
            .build();
        Mockito.when(itemRepository.findById(itemId)).thenReturn(Optional.ofNullable(item));

        // when
        Item savedItem = itemService.findById(itemId)
            .orElseThrow(() -> new BusinessException(ITEM_NOT_EXISTS));

        // then
        Assertions.assertThat(savedItem.getItemName()).isEqualTo(item.getItemName());
        Assertions.assertThat(savedItem.getItemSellStatus()).isEqualTo(item.getItemSellStatus());
        Assertions.assertThat(savedItem.getItemDetail()).isEqualTo(item.getItemDetail());
        Assertions.assertThat(savedItem.getPrice()).isEqualTo(item.getPrice());
        Assertions.assertThat(savedItem.getStockNumber()).isEqualTo(item.getStockNumber());
    }




}
