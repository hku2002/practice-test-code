package com.secondhand.api.user.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
public class AddressResponse {

    private final String address;
    private final String addressDetail;
    private final String zipCode;

    @Builder
    public AddressResponse(String address, String addressDetail, String zipCode) {
        this.address = address;
        this.addressDetail = addressDetail;
        this.zipCode = zipCode;
    }
}
