package com.secondhand.api.product.domain;

import com.secondhand.api.product.domain.enumtype.ProductStatus;
import com.secondhand.api.user.domain.User;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

import static javax.persistence.FetchType.LAZY;
import static lombok.AccessLevel.PROTECTED;

@Getter
@Entity
@NoArgsConstructor(access = PROTECTED)
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "name", nullable = false, length = 100)
    private String name;

    @Column(name = "price", nullable = false)
    private int price;

    @Column(name = "status", nullable = false, length = 30)
    private ProductStatus status;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(nullable = false)
    private User user;

    @Builder
    public Product(String name, int price, ProductStatus status, User user) {
        this.name = name;
        this.price = price;
        this.status = status;
        this.user = user;
    }

}
