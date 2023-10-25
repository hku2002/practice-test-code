package com.commerce.order.service;

import com.commerce.common.utils.OrderUtils;
import com.commerce.order.domain.*;
import com.commerce.product.domain.Product;
import com.commerce.product.domain.ProductRepository;
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
    private final ProductRepository productRepository;
    private final DeliveryRepository deliveryRepository;

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

    @Transactional
    public Long complete(Long orderId) {
        // 전달 받은 orderId를 기준으로 조회하여 주문이 존재하는지 확인한다.
        // 주문이 존재하지 않으면 Exception 이 발생한다.
        Order order = orderRepository.findById(orderId).orElseThrow(() -> new IllegalArgumentException("올바르지 않은 주문번호 입니다."));

        // OrderProduct 의 productId를 기준으로 조회하여 해당 product 의 수량을 1개 차감한다.
        List<OrderProduct> orderProducts = orderProductRepository.findByOrder(order);

        List<Long> productIds = new ArrayList<>();
        orderProducts.forEach(orderProduct -> productIds.add(orderProduct.getProductId()));

        List<Product> products = productRepository.findByIdIn(productIds);
        products.forEach(product -> product.deductionQuantity(1));

        // 주문이 존재하면 주문 완료 처리를 한다.
        order.completeStatus();

        // 배송 준비 상태로 배송을 생성한다.
        Delivery delivery = Delivery.builder()
                .address("서울시 강남구 강남로 123 1005호")
                .zipCode("00001")
                .order(order)
                .build();
        deliveryRepository.save(delivery);

        // orderId를 return 한다.
        return order.getId();
    }

    public void cancel() {
        // 전달 받은 orderId를 기준으로 조회하여 주문이 존재하는지 확인한다.

        // 주문이 존재하지 않으면 Exception 이 발생한다.

        // 주문이 존재하면 주문 취소 처리를 한다.

        // orderId를 return 한다.
    }

}
