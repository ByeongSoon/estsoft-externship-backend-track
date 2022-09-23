package com.shop.projectlion.api.adminitem.controller;

import com.shop.projectlion.api.adminitem.dto.ItemDtlDto;
import com.shop.projectlion.api.adminitem.dto.UpdateItemDto;
import com.shop.projectlion.api.adminitem.service.AdminItemService;
import com.shop.projectlion.global.error.exception.BusinessException;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import static com.shop.projectlion.global.error.exception.ErrorCode.MISMATCHED_ITEM_ID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/admin/")
public class AdminItemController {

    private final AdminItemService adminItemService;

    @GetMapping("items/{itemId}")
    @ApiOperation(value = "상품 상세조회", notes = "상품 번호를 가진 해당 상품을 조회한다.")
    public ResponseEntity<ItemDtlDto> readItem(@PathVariable Long itemId) {

        return ResponseEntity.ok(adminItemService.getItemDtlDto(itemId));
    }

    @PatchMapping("items/{itemId}")
    @ApiOperation(value = "상품 수정", notes = "상품의 정보를 수정한다.")
    public ResponseEntity<UpdateItemDto.Response> updateItem(@RequestBody @Validated UpdateItemDto.Request updateRequestDto, @PathVariable Long itemId) {
        if (!itemId.equals(updateRequestDto.getItemId())) {
            throw new BusinessException(MISMATCHED_ITEM_ID);
        }
        UpdateItemDto.Response updateResponseDto = adminItemService.getUpdateResponseDto(updateRequestDto);

        return ResponseEntity.ok(updateResponseDto);
    }

}
