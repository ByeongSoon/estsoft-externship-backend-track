package com.shop.projectlion.web.orderhist.service;

import com.shop.projectlion.domain.delivery.model.Delivery;
import com.shop.projectlion.domain.delivery.service.DeliveryService;
import com.shop.projectlion.domain.item.model.Item;
import com.shop.projectlion.domain.item.service.ItemService;
import com.shop.projectlion.domain.member.model.Member;
import com.shop.projectlion.domain.order.model.Orders;
import com.shop.projectlion.domain.order.service.OrderService;
import com.shop.projectlion.domain.orderitem.model.OrderItem;
import com.shop.projectlion.domain.orderitem.service.OrderItemService;
import com.shop.projectlion.global.error.exception.EntityNotFoundException;
import com.shop.projectlion.web.orderhist.dto.OrderHistDto;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
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
public class OrderHistService {

    private final OrderService orderService;

    private final OrderItemService orderItemService;

    private final DeliveryService deliveryService;

    private final ItemService itemService;


    public Page<OrderHistDto> getOrderHistDto(Member member, Pageable pageable) {
        Page<Orders> ordersPages = orderService.findAllByMember(pageable, member);

        long totalElements = ordersPages.getTotalElements();
        List<Orders> ordersList = ordersPages.getContent();

        // todo fetch join을 사용하여 주문 정보를 가져올 때 필요한 정보 영속화하여 사용
        List<OrderHistDto> orderHistDtos = ordersList.stream()
            .map(orders -> {
                List<OrderItem> orderItemList = orders.getOrderItemList();

                List<Delivery> deliveryList = orderItemList.stream()
                    .map(orderItem -> {
                        Item findItem = orderItem.getItem();
                        return findItem.getDelivery();
                    })
                    .collect(Collectors.toList());

                List<OrderHistDto.OrderItemHistDto> orderItemHistDtos = orderItemList.stream()
                    .map(orderItem -> {
                        Item findItem = orderItem.getItem();
                        return OrderHistDto.OrderItemHistDto.from(orderItem, findItem);
                    })
                    .collect(Collectors.toList());

                OrderHistDto orderHistDto = OrderHistDto.from(orders, deliveryList);
                orderHistDto.setOrderItemHistDtos(orderItemHistDtos);

                return orderHistDto;
            })
            .collect(Collectors.toList());

        return new PageImpl<>(orderHistDtos, pageable, totalElements);
    }

    @Transactional
    public ResponseEntity<String> cancelOrder(Long orderId) {
        Orders orders = orderService.cancelOrder(orderId);
        List<OrderItem> orderItemList = orderItemService.findByOrders(orders);

        for (OrderItem orderItem: orderItemList) {
            Item item = orderItem.getItem();
            item.increaseStock(orderItem.getCount());
        }

        return new ResponseEntity<>(HttpStatus.OK);
    }
}
