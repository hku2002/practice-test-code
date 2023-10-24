package com.commerce.product.service;

import com.commerce.product.domain.Product;
import com.commerce.product.domain.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;

    public List<Product> findProducts(List<Long> productIds) {
        // 상품 id 리스트로 상품 목록 조회를 한다.
        List<Product> products = productRepository.findByIdIn(productIds);

        // 상품이 1개도 존재하지 않으면 Exception 을 발생시킨다.
        if (products.isEmpty()) {
            throw new IllegalArgumentException("상품이 존재하지 않습니다.");
        }

        // 상품이 존재하면 상품 목록을 반환한다.
        return products;
    }

}
