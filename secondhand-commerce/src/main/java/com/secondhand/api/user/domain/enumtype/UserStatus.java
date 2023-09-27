package com.secondhand.api.user.domain.enumtype;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum UserStatus {

    ACTIVE("활성"),
    WITHDRAWAL("탈퇴");

    private final String text;

}
