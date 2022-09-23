package com.shop.projectlion.web.adminitem.controller;

import com.shop.projectlion.domain.item.model.Item;
import com.shop.projectlion.domain.member.model.Member;
import com.shop.projectlion.global.config.security.CustomUserDetails;
import com.shop.projectlion.global.error.exception.ErrorCode;
import com.shop.projectlion.web.adminitem.dto.DeliveryDto;
import com.shop.projectlion.web.adminitem.dto.InsertItemDto;
import com.shop.projectlion.web.adminitem.dto.UpdateItemDto;
import com.shop.projectlion.web.adminitem.service.AdminItemService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.IOException;
import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/admin/items")
public class AdminItemController {

    private final AdminItemService adminItemService;

    @ModelAttribute("deliveryDtos")
    public List<DeliveryDto> deliveryDtos(@AuthenticationPrincipal CustomUserDetails userDetails) {
        Member member = userDetails.getMember();
        List<DeliveryDto> deliveryDtos = adminItemService.findDeliveryByMember(member);

        return deliveryDtos;
    }

    @GetMapping("/new")
    public String itemForm(Model model) {
        model.addAttribute("insertItemDto", new InsertItemDto());

        return "adminitem/registeritemform";
    }

    @PostMapping("/new")
    public String registerItem(@Validated InsertItemDto insertItemDto, BindingResult bindingResult,
                               @AuthenticationPrincipal CustomUserDetails userDetails, RedirectAttributes redirectAttributes) {
        Member member = userDetails.getMember();
        if (bindingResult.hasErrors() || insertItemDto.getItemImageFiles().get(0).isEmpty()) {

            if (insertItemDto.getItemImageFiles().get(0).isEmpty()) {
                bindingResult.reject("noUploadImageFile", ErrorCode.NO_REQUIRED_FIRST_IMAGE.getMessage());
            }

            return "adminitem/registeritemform";
        }

        try {
            Item savedItem = adminItemService.registerItem(insertItemDto, member);
            redirectAttributes.addAttribute("itemId", savedItem.getItemId());
        } catch (IOException e) {
            bindingResult.reject("noImageFile",e.getMessage());
            return "adminitem/registeritemform";
        }

        return "redirect:/admin/items/{itemId}";
    }

    @GetMapping("/{itemId}")
    public String itemEdit(@PathVariable Long itemId, Model model) {
        UpdateItemDto updateItemDto = adminItemService.getUpdateItemDto(itemId);
        model.addAttribute("updateItemDto", updateItemDto);

        return "adminitem/updateitemform";
    }

    @PostMapping("/{itemId}")
    public String updateItem(@PathVariable Long itemId, @Validated UpdateItemDto updateItemDto,
                             BindingResult bindingResult, RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors() || updateItemDto.getOriginalImageNames().get(0).isEmpty()) {

            if (updateItemDto.getOriginalImageNames().get(0).isEmpty()) {
                bindingResult.reject("noUploadImageFile", ErrorCode.NO_REQUIRED_FIRST_IMAGE.getMessage());
            }

            List<UpdateItemDto.ItemImageDto> itemImageDtos = adminItemService.getItemImageDtos(itemId);
            updateItemDto.setItemImageDtos(itemImageDtos);

            return "adminitem/updateitemform";
        }

        try {
            adminItemService.updateItem(updateItemDto);
        } catch (IOException e) {
            bindingResult.reject("noImageFile",e.getMessage());
            return "adminitem/updateitemform";
        }

        redirectAttributes.addAttribute("itemId", itemId);
        return "redirect:/admin/items/{itemId}";
    }

}