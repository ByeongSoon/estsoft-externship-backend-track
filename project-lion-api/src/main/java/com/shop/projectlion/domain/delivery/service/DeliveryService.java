package com.shop.projectlion.domain.delivery.service;

import com.shop.projectlion.domain.delivery.model.Delivery;
import com.shop.projectlion.domain.delivery.repository.DeliveryRepository;
import com.shop.projectlion.domain.member.entity.Member;
import com.shop.projectlion.global.error.exception.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

import static com.shop.projectlion.global.error.exception.ErrorCode.DELIVERY_NOT_EXISTS;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class DeliveryService {

    private final DeliveryRepository deliveryRepository;

    public Delivery findById(Long id) {
        return deliveryRepository.findById(id)
            .orElseThrow(() -> new EntityNotFoundException(DELIVERY_NOT_EXISTS));
    }

    public Optional<List<Delivery>> findByMember(Member member) {
        return deliveryRepository.findByMember(member);
    }
}
