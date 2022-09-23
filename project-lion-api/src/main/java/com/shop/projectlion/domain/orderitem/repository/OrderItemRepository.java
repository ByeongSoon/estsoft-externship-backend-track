package com.shop.projectlion.domain.orderitem.repository;

import com.shop.projectlion.domain.order.model.Orders;
import com.shop.projectlion.domain.orderitem.model.OrderItem;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderItemRepository extends JpaRepository<OrderItem, Long> {
    List<OrderItem> findByOrders(Orders orders);
}
