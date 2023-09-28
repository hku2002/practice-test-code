package com.secondhand.api.product.service;

import com.secondhand.api.product.domain.Product;
import com.secondhand.api.product.domain.enumtype.ProductStatus;
import com.secondhand.api.product.dto.ProductCreateRequest;
import com.secondhand.api.product.dto.ProductResponse;
import com.secondhand.api.product.repository.ProductRepository;
import com.secondhand.api.user.domain.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static com.secondhand.api.product.domain.enumtype.ProductStatus.ON_SALE;
import static com.secondhand.api.user.domain.enumtype.UserStatus.ACTIVE;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ProductService {

    private final ProductRepository productRepository;

    @Transactional
    public ProductResponse createProduct(ProductCreateRequest request) {
//        // user 정보를 받아온 뒤 Entity에 담는다.
//        User user = User.builder()
//                .name("홍길동")
//                .phoneNumber("01012345678")
//                .status(ACTIVE)
//                .build();
//
//        // 전달 받은 request를 entity로 변환한다.
//        Product product = ProductCreateRequest.of(request, user);
//
//        // 변환된 entity를 save 하고 entity를 return 한다.
//        return ProductResponse.of(productRepository.save(product));
        return ProductResponse.builder()
                .name("맥북 중고")
                .price(10_000)
                .status(ON_SALE)
                .build();
    }
}
