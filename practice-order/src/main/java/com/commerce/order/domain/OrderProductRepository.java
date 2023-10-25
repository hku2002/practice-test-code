package com.commerce.order.domain;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderProductRepository extends JpaRepository<OrderProduct, Long> {

    List<OrderProduct> findByIdIn(List<Long> orderProductIds);
    List<OrderProduct> findByOrder(Order order);

}
