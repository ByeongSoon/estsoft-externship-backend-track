package com.shop.projectlion.web.orderhist.controller;

import com.shop.projectlion.domain.member.model.Member;
import com.shop.projectlion.global.config.security.CustomUserDetails;
import com.shop.projectlion.web.orderhist.dto.OrderHistDto;
import com.shop.projectlion.web.orderhist.service.OrderHistService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequiredArgsConstructor
@RequestMapping("/orderhist")
public class OrderHistController {

    private final OrderHistService orderHistService;

    @GetMapping
    public String orderHist(@PageableDefault(size = 6) Pageable pageable, Model model,
                            @AuthenticationPrincipal CustomUserDetails userDetails) {
        Member member = userDetails.getMember();

        Page<OrderHistDto> pageOrderHistDtos = orderHistService.getOrderHistDto(member, pageable);

        model.addAttribute("orders", pageOrderHistDtos);
        model.addAttribute("page", pageOrderHistDtos.getNumber());
        model.addAttribute("maxPage", 5);
        return "orderhist/orderhist";
    }

    @PostMapping("/{orderId}/cancel")
    public ResponseEntity<String> cancelOrder(@PathVariable Long orderId, @AuthenticationPrincipal CustomUserDetails userDetails) {
        if (userDetails == null) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }

        return orderHistService.cancelOrder(orderId);
    }

}
