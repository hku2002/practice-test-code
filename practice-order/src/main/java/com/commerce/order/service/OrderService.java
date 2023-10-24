package com.commerce.order.service;

import com.commerce.common.utils.OrderUtils;
import com.commerce.order.domain.Order;
import com.commerce.order.domain.OrderProduct;
import com.commerce.order.domain.OrderProductRepository;
import com.commerce.order.domain.OrderRepository;
import com.commerce.product.domain.Product;
import com.commerce.product.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

import static com.commerce.order.domain.enumtype.OrderStatus.CREATED;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final ProductService productService;
    private final OrderRepository orderRepository;
    private final OrderProductRepository orderProductRepository;

    @Transactional
    public Long create(List<Long> productIds) {
        // 전달 받은 상품 ID 리스트로 저장된 상품 정보를 불러온다.
        // 상품 정보가 존재하지 않으면 Exception 이 발생한다.
        List<Product> products = productService.findProducts(productIds);

        // Order 에 주문을 생성한다.
        Order order = Order.builder()
                .orderName(OrderUtils.makeOrderName(products))
                .totalPrice(OrderUtils.calculateTotalPrice(products))
                .status(CREATED)
                .build();
        orderRepository.save(order);

        // 조회한 상품 정보 데이터를 가공하여 생성된 Order 기준으로 OrderProduct 에 데이터를 저장한다.
        List<OrderProduct> orderProducts = new ArrayList<>();
        products.forEach(product -> orderProducts.add(OrderProduct.createOrderProduct(product, order)));
        orderProductRepository.saveAll(orderProducts);

        // orderId를 return 한다.
        return order.getId();
    }

    public void complete() {
        // 전달 받은 orderId를 기준으로 조회하여 주문이 존재하는지 확인한다.

        // 주문이 존재하지 않으면 Exception 이 발생한다.

        // OrderProduct 의 productId를 기준으로 해당 product 의 수량을 1개 차감한다.

        // 주문이 존재하면 주문 완료 처리를 한다.

        // orderId를 return 한다.
    }

    public void cancel() {
        // 전달 받은 orderId를 기준으로 조회하여 주문이 존재하는지 확인한다.

        // 주문이 존재하지 않으면 Exception 이 발생한다.

        // 주문이 존재하면 주문 취소 처리를 한다.

        // orderId를 return 한다.
    }

}
