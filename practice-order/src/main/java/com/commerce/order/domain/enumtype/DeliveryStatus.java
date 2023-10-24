package com.commerce.order.domain.enumtype;

import lombok.Getter;

@Getter
public enum DeliveryStatus {
    WAIT("대기중"),
    IN_TRANSIT("배송중"),
    DELIVERED("배송완료");

    private String name;

    DeliveryStatus(String name) {
        this.name = name;
    }
}
