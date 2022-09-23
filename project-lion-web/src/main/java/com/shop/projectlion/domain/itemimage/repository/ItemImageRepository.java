package com.shop.projectlion.domain.itemimage.repository;

import com.shop.projectlion.domain.itemimage.model.ItemImage;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ItemImageRepository extends JpaRepository<ItemImage, Long> {
}
