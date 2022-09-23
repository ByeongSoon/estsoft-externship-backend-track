package com.shop.projectlion.domain.item.repository;

import com.shop.projectlion.domain.item.model.Item;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface ItemRepository extends JpaRepository<Item, Long> {

    @Query("select i from Item i " +
        "where (:keyword is null " +
        "OR i.itemName like CONCAT('%',:keyword,'%') " +
        "OR i.itemDetail like CONCAT('%',:keyword,'%'))" +
        "AND i.itemSellStatus='SELL'")
    Page<Item> findBySearchKeyword(Pageable pageable,@Param("keyword") String keyword);

}
