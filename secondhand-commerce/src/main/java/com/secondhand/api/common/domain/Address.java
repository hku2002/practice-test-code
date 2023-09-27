package com.secondhand.api.common.domain;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Embeddable;

import static lombok.AccessLevel.PROTECTED;

@Getter
@Embeddable
@NoArgsConstructor(access = PROTECTED)
public class Address {

    @Column(name = "address", length = 200, nullable = false)
    private String address;

    @Column(name = "address_detail", length = 200)
    private String addressDetail;

    @Column(name = "zip_code", length = 5, nullable = false)
    private String zipCode;

    @Builder
    public Address(String address, String addressDetail, String zipCode) {
        this.address = address;
        this.addressDetail = addressDetail;
        this.zipCode = zipCode;
    }
}
