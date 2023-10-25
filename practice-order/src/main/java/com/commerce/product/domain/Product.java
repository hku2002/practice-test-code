package com.commerce.product.domain;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Getter
@Entity
@DynamicUpdate
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String productName;

    private int price;

    private int quantity;

    @Builder
    public Product(Long id, String productName, int price, int quantity) {
        this.id = id;
        this.productName = productName;
        this.price = price;
        this.quantity = quantity;
    }

    public void deductionQuantity(int deductionCount) {
        if (deductionCount < 0) {
            deductionCount = 0;
        }
        if (this.quantity < deductionCount) {
            throw new IllegalStateException("재고가 존재하지 않습니다.");
        }
        this.quantity -= deductionCount;
    }

}
