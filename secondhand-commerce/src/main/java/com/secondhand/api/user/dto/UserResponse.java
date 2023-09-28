package com.secondhand.api.user.dto;

import com.secondhand.api.common.domain.Address;
import com.secondhand.api.user.domain.User;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class UserResponse {

    private String email;
    private String name;
    private String phoneNumber;
    private String address;
    private String addressDetail;
    private String zipCode;

    @Builder
    public UserResponse(String email, String name, String phoneNumber, String address, String addressDetail, String zipCode) {
        this.email = email;
        this.name = name;
        this.phoneNumber = phoneNumber;
        this.address = address;
        this.addressDetail = addressDetail;
        this.zipCode = zipCode;
    }

    public static UserResponse of(User user) {
        return UserResponse.builder()
                .email(user.getEmail())
                .name(user.getName())
                .phoneNumber(user.getPhoneNumber())
                .address(user.getAddress().getAddress())
                .addressDetail(user.getAddress().getAddressDetail())
                .zipCode(user.getAddress().getZipCode())
                .build();
    }
}
