package com.shop.projectlion.domain.item.service;

import com.shop.projectlion.domain.item.model.Item;
import com.shop.projectlion.domain.item.model.enumclass.ItemSellStatus;
import com.shop.projectlion.domain.item.repository.ItemRepository;
import com.shop.projectlion.global.error.exception.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

import static com.shop.projectlion.domain.item.model.enumclass.ItemSellStatus.SELL;
import static com.shop.projectlion.domain.item.model.enumclass.ItemSellStatus.SOLD_OUT;
import static com.shop.projectlion.global.error.exception.ErrorCode.NO_ITEM_ERROR;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ItemService {

    private final ItemRepository itemRepository;

    @Transactional
    public Item register(Item item) {
        return itemRepository.save(item);
    }

    public Optional<Item> findById(Long itemId) {
        return itemRepository.findById(itemId);
    }

    public Page<Item> findAllBySearch(Pageable pageable, String keyword) {
        return itemRepository.findBySearchKeyword(pageable, keyword);
    }

    @Transactional
    public Item decreaseStock(Long itemId, Integer count) {
        Item findItem = findById(itemId)
            .orElseThrow(() -> new EntityNotFoundException(NO_ITEM_ERROR));

        findItem.decreaseStock(count);

        if (findItem.isSoldOut()) {
            findItem.updateItemSellStatus(SOLD_OUT);
        }

        return findItem;
    }

//    @Transactional
//    public void increaseStock(Long itemId, Integer count) {
//        Item findItem = findById(itemId)
//            .orElseThrow(() -> new EntityNotFoundException(NO_ITEM_ERROR));
//
//        findItem.increaseStock(count);
//
//        if (!findItem.isSoldOut()) {
//            findItem.updateItemSellStatus(SELL);
//        }
//
//    }

}
