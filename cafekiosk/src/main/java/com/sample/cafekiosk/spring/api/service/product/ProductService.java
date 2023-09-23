package com.sample.cafekiosk.spring.api.service.product;

import com.sample.cafekiosk.spring.api.controller.product.request.ProductCreateRequest;
import com.sample.cafekiosk.spring.api.domain.product.Product;
import com.sample.cafekiosk.spring.api.domain.product.ProductRepository;
import com.sample.cafekiosk.spring.api.domain.product.enumtype.ProductSellingStatus;
import com.sample.cafekiosk.spring.api.service.product.response.ProductResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ProductService {

    private final ProductRepository productRepository;

    public List<ProductResponse> getSellingProducts() {
        List<Product> products = productRepository.findAllBySellingStatusIn(ProductSellingStatus.forDisplay());
        return products.stream()
                .map(ProductResponse::of)
                .collect(Collectors.toList());
    }

    @Transactional
    public ProductResponse createProduct(ProductCreateRequest request) {
        // study 이므로 동시성 이슈는 무시
        Product product = request.toEntity(createNextProductNumber());
        return ProductResponse.of(productRepository.save(product));
    }

    private String createNextProductNumber() {
        String latestProductNumber = productRepository.findLatestProductNumber();
        if (!StringUtils.hasText(latestProductNumber)) return "001";
        int nextProductNumberInt = Integer.parseInt(latestProductNumber) + 1;
        return String.format("%03d", nextProductNumberInt);
    }
}
