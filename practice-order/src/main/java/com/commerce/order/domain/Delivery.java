package com.commerce.order.domain;

import com.commerce.order.domain.enumtype.DeliveryStatus;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Delivery {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String address;

    private String zipCode;

    @OneToOne
    private Order order;

    @Enumerated(EnumType.STRING)
    private DeliveryStatus status;

    @Builder
    public Delivery(Long id, String address, String zipCode, Order order, DeliveryStatus status) {
        this.id = id;
        this.address = address;
        this.zipCode = zipCode;
        this.order = order;
        this.status = status;
    }
}
