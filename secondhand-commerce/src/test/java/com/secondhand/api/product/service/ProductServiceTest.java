package com.secondhand.api.product.service;

import com.secondhand.api.common.domain.Address;
import com.secondhand.api.product.domain.enumtype.ProductStatus;
import com.secondhand.api.product.dto.ProductCreateRequest;
import com.secondhand.api.product.dto.ProductResponse;
import com.secondhand.api.user.domain.User;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@Transactional
@SpringBootTest
class ProductServiceTest {

    @Autowired
    ProductService productService;

    @Test
    @DisplayName("전달받은 상품 정보를 올바르게 저장한다.")
    void createProductTest() {
        // given
        ProductCreateRequest request = ProductCreateRequest.builder()
                .name("맥북 중고")
                .price(10_000)
                .status(ProductStatus.ON_SALE)
                .build();

        // when
        ProductResponse response = productService.createProduct(request);

        // then
        assertAll(
                () -> assertThat(response.getName()).isEqualTo("맥북 중고"),
                () -> assertThat(response.getPrice()).isEqualTo(10_000),
                () -> assertThat(response.getStatus()).isEqualTo(ProductStatus.ON_SALE)
        );

    }

}