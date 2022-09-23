package com.shop.projectlion.domain.item.service;

import com.shop.projectlion.domain.item.model.Item;
import com.shop.projectlion.domain.item.repository.ItemRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

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

}
