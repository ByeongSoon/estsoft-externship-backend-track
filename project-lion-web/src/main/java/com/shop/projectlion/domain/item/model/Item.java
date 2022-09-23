package com.shop.projectlion.domain.item.model;

import com.shop.projectlion.domain.delivery.model.Delivery;
import com.shop.projectlion.domain.itemimage.model.ItemImage;
import com.shop.projectlion.domain.item.model.enumclass.ItemSellStatus;
import com.shop.projectlion.domain.base.BaseEntity;
import com.shop.projectlion.domain.member.model.Member;
import com.shop.projectlion.domain.orderitem.model.OrderItem;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.util.List;

import static com.shop.projectlion.domain.item.model.enumclass.ItemSellStatus.SELL;
import static com.shop.projectlion.domain.item.model.enumclass.ItemSellStatus.SOLD_OUT;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Item extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long itemId;

    @Column(columnDefinition = "LONGTEXT")
    @NotBlank
    private String itemDetail;

    @Column(length = 100)
    @NotBlank
    private String itemName;

    @Enumerated(EnumType.STRING)
    @Column(length = 20)
    private ItemSellStatus itemSellStatus;

    @Column(length = 11)
    private Integer price;

    @Column(length = 11)
    private Integer stockNumber;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id", nullable = false)
    private Member member;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "delivery_id", nullable = false)
    private Delivery delivery;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "item", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ItemImage> itemImageList;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "item")
    private List<OrderItem> orderItemList;

    @Builder
    public Item(String itemName, String itemDetail, ItemSellStatus itemSellStatus,
                Integer price, Integer stockNumber, Member member, Delivery delivery) {
        this.itemDetail = itemDetail;
        this.itemName = itemName;
        this.itemSellStatus = itemSellStatus;
        this.price = price;
        this.stockNumber = stockNumber;
        this.member = member;
        this.delivery = delivery;
    }

    public static Item of(String itemName, String itemDetail, ItemSellStatus itemSellStatus,
                          Integer price, Integer stockNumber, Member member, Delivery delivery) {
        return Item.builder()
            .itemName(itemName)
            .itemDetail(itemDetail)
            .itemSellStatus(itemSellStatus)
            .price(price)
            .stockNumber(stockNumber)
            .member(member)
            .delivery(delivery)
            .build();
    }

    public static Item createItem(Item item, Member member, Delivery delivery) {
        return Item.builder()
            .itemName(item.getItemName())
            .itemDetail(item.getItemDetail())
            .itemSellStatus(item.getItemSellStatus())
            .price(item.getPrice())
            .stockNumber(item.getStockNumber())
            .member(member)
            .delivery(delivery)
            .build();
    }

    public void updateItem(String itemName, String itemDetail, ItemSellStatus itemSellStatus,
                           Integer price, Integer stockNumber, Delivery delivery) {
        this.itemName = itemName;
        this.itemDetail = itemDetail;
        this.itemSellStatus = itemSellStatus;
        this.price = price;
        this.stockNumber = stockNumber;
        this.delivery = delivery;
    }

    public void decreaseStock(Integer orderStock) {
        this.stockNumber -= orderStock;
        if (isSoldOut()) {
            updateItemSellStatus(SOLD_OUT);
        }
    }

    public void increaseStock(Integer orderStock) {
        this.stockNumber += orderStock;
        if (!isSoldOut()) {
            updateItemSellStatus(SELL);
        }
    }

    public void updateItemSellStatus(ItemSellStatus itemSellStatus) {
        this.itemSellStatus = itemSellStatus;
    }

    public boolean isSoldOut() {
        return stockNumber == 0;
    }

    public boolean isStockLessThan(Integer count) {
        return stockNumber < count;
    }

}
