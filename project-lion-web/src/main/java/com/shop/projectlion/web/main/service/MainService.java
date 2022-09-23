package com.shop.projectlion.web.main.service;

import com.shop.projectlion.domain.item.model.Item;
import com.shop.projectlion.domain.item.service.ItemService;
import com.shop.projectlion.web.main.dto.ItemSearchDto;
import com.shop.projectlion.web.main.dto.MainItemDto;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class MainService {

    private final ItemService itemService;

    public Page<MainItemDto> getMainItemDtoFromSearch(Pageable pageable, ItemSearchDto itemSearchDto) {
        Page<Item> items = itemService.findAllBySearch(pageable, itemSearchDto.getSearchQuery());

        List<MainItemDto> mainItemDtos = items.getContent().stream()
            .map(MainItemDto::from)
            .collect(Collectors.toList());

        return new PageImpl<>(mainItemDtos, pageable, items.getTotalElements());
    }
}
