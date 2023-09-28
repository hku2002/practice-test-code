package com.secondhand.api.user.domain;

import com.secondhand.api.common.domain.Address;
import com.secondhand.api.common.domain.BaseEntity;
import com.secondhand.api.user.domain.enumtype.UserStatus;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

import static com.secondhand.api.user.domain.enumtype.UserStatus.ACTIVE;
import static javax.persistence.EnumType.STRING;
import static lombok.AccessLevel.PROTECTED;

@Getter
@Entity
@Table(name = "users")
@NoArgsConstructor(access = PROTECTED)
public class User extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "email", nullable = false, length = 200)
    private String email;

    @Column(name = "password", nullable = false)
    private String password;

    @Column(name = "name", nullable = false, length = 50)
    private String name;

    @Column(name = "phone_number", nullable = false, length = 11)
    private String phoneNumber;

    @Embedded
    private Address address;

    @Enumerated(STRING)
    @Column(name = "status", nullable = false, length = 30)
    private UserStatus status;

    @Builder
    public User(String email, String password, String name, String phoneNumber, Address address, UserStatus status) {
        this.email = email;
        this.password = password;
        this.name = name;
        this.phoneNumber = phoneNumber;
        this.address = address;
        this.status = status;
    }

    public static User create(String email, String password, String name, String phoneNumber, Address address) {
        return User.builder()
                .email(email)
                .password(password)
                .name(name)
                .phoneNumber(phoneNumber)
                .address(address)
                .status(ACTIVE)
                .build();
    }

}
