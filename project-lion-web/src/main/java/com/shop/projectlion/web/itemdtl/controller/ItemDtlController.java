package com.shop.projectlion.web.itemdtl.controller;

import com.shop.projectlion.domain.member.model.Member;
import com.shop.projectlion.global.config.security.CustomUserDetails;
import com.shop.projectlion.web.itemdtl.dto.ItemDtlDto;
import com.shop.projectlion.web.itemdtl.dto.OrderDto;
import com.shop.projectlion.web.itemdtl.service.ItemdtlService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import static com.shop.projectlion.global.error.exception.ErrorCode.LOGIN_ERROR;

@Controller
@RequiredArgsConstructor
@RequestMapping("/itemdtl")
public class ItemDtlController {

    private final ItemdtlService itemdtlService;

    @GetMapping(value = "/{itemId}")
    public String itemDtl(Model model, @PathVariable Long itemId){
        ItemDtlDto itemDtlDto = itemdtlService.getItemDtl(itemId);

        model.addAttribute("item", itemDtlDto);
        return "itemdtl/itemdtl";
    }


    @PostMapping("/order")
    public ResponseEntity<String> itemOrder(@RequestBody OrderDto orderDto, @AuthenticationPrincipal CustomUserDetails userDetails) {
        if (userDetails == null) {
            return new ResponseEntity<>(LOGIN_ERROR.getMessage(), HttpStatus.UNAUTHORIZED);
        }

        Member member = userDetails.getMember();
        return itemdtlService.order(orderDto, member);
    }

}
