package com.commerce.order.domain.enumtype;

import lombok.Getter;

@Getter
public enum OrderStatus {

    CREATED("주문생성"),
    COMPLETED("주문완료"),
    CANCELED("주문취소");

    private String name;

    OrderStatus(String name) {
        this.name = name;
    }
}
