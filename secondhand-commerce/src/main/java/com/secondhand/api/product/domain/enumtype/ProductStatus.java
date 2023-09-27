package com.secondhand.api.product.domain.enumtype;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ProductStatus {

    STANDBY ("판매대기"),
    ON_SALE ("판매중"),
    SUSPENSION ("판매중지"),
    COMPLETION ("판매완료");

    private final String text;

}
