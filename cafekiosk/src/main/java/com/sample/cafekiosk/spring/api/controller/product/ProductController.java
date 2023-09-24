package com.sample.cafekiosk.spring.api.controller.product;

import com.sample.cafekiosk.spring.api.common.response.BodyResponse;
import com.sample.cafekiosk.spring.api.common.response.CommonResponse;
import com.sample.cafekiosk.spring.api.controller.product.request.ProductCreateRequest;
import com.sample.cafekiosk.spring.api.service.product.ProductService;
import com.sample.cafekiosk.spring.api.service.product.response.ProductResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;

    @PostMapping("/api/v1/products/new")
    public ResponseEntity<BodyResponse<ProductResponse>> createProduct(@RequestBody @Valid ProductCreateRequest request) {
        return CommonResponse.created(productService.createProduct(request));
    }

    @GetMapping("/api/v1/products/selling")
    public ResponseEntity<BodyResponse<List<ProductResponse>>> getSellingProducts() {
        return CommonResponse.ok(productService.getSellingProducts());
    }
}
