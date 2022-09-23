package com.shop.projectlion.domain.orderitem.service;

import com.shop.projectlion.domain.item.model.Item;
import com.shop.projectlion.domain.order.model.Orders;
import com.shop.projectlion.domain.orderitem.model.OrderItem;
import com.shop.projectlion.domain.orderitem.repository.OrderItemRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class OrderItemService {

    private final OrderItemRepository orderItemRepository;

    @Transactional
    public void register(Integer count, Item item, Orders orders) {
        OrderItem orderItem = OrderItem.of(
            count,
            item,
            orders
        );

        orderItemRepository.save(orderItem);
    }

    public List<OrderItem> findByOrders(Orders orders) {
        return orderItemRepository.findByOrders(orders);
    }
}
