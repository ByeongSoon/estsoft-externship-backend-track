package com.shop.projectlion.domain.delivery.repository;

import com.shop.projectlion.domain.delivery.model.Delivery;
import com.shop.projectlion.domain.member.model.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface DeliveryRepository extends JpaRepository<Delivery, Long> {
    Optional<List<Delivery>> findByMember(Member member);
}
