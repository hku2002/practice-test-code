package com.secondhand.api.user.dto;

import com.secondhand.api.common.domain.Address;
import lombok.Builder;
import lombok.Getter;
import javax.validation.constraints.NotEmpty;

@Getter
public class UserCreateRequest {

    @NotEmpty(message = "이름은 필수 입력값입니다.")
    private String name;

    @NotEmpty(message = "전화번호는 필수 입력값입니다.")
    private String phoneNumber;

    @NotEmpty(message = "주소는 필수 입력값입니다.")
    private String address;
    private String addressDetail;

    @NotEmpty(message = "우편번호는 필수 입력값입니다.")
    private String zipCode;

    @Builder
    public UserCreateRequest(String name, String phoneNumber, String address, String addressDetail, String zipCode) {
        this.name = name;
        this.phoneNumber = phoneNumber;
        this.address = address;
        this.addressDetail = addressDetail;
        this.zipCode = zipCode;
    }

    public Address toAddress(String address, String addressDetail, String zipCode) {
        return Address.builder()
                .address(address)
                .addressDetail(addressDetail)
                .zipCode(zipCode)
                .build();
    }

}
