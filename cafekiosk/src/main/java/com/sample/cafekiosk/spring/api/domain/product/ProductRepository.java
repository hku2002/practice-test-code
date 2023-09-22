package com.sample.cafekiosk.spring.api.domain.product;

import com.sample.cafekiosk.spring.api.domain.product.enumtype.ProductSellingStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product ,Long> {

    List<Product> findAllBySellingStatusIn(List<ProductSellingStatus> sellingStatuses);

    List<Product> findAllByProductNumberIn(List<String> productNumbers);
}
