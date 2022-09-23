package com.shop.projectlion.domain.order.repository;

import com.shop.projectlion.domain.member.entity.Member;
import com.shop.projectlion.domain.order.model.Orders;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Orders, Long> {
    Page<Orders> findByMember(Pageable pageable, Member member);
}
