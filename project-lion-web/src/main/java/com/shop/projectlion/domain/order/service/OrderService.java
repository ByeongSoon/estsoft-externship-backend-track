package com.shop.projectlion.domain.order.service;

import com.shop.projectlion.domain.member.model.Member;
import com.shop.projectlion.domain.order.model.Orders;
import com.shop.projectlion.domain.order.model.enumclass.OrderStatus;
import com.shop.projectlion.domain.order.repository.OrderRepository;
import com.shop.projectlion.global.error.exception.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static com.shop.projectlion.global.error.exception.ErrorCode.NO_ORDER_ERROR;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;

    @Transactional
    public Orders register(Member member) {
        Orders orders = Orders.of(member);
        return orderRepository.save(orders);
    }

    public Page<Orders> findAllByMember(Pageable pageable, Member member) {
        return orderRepository.findByMember(pageable, member);
    }

    @Transactional
    public Orders cancelOrder(Long orderId) {
        Orders orders = orderRepository.findById(orderId)
            .orElseThrow(() -> new EntityNotFoundException(NO_ORDER_ERROR));

        orders.cancel();

        return orders;
    }
}
