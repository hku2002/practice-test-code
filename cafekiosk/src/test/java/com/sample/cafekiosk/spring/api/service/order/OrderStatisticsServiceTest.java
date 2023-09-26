package com.sample.cafekiosk.spring.api.service.order;

import com.sample.cafekiosk.spring.IntegrationTestSupport;
import com.sample.cafekiosk.spring.api.domain.history.MailSendHistory;
import com.sample.cafekiosk.spring.api.domain.history.MailSendHistoryRepository;
import com.sample.cafekiosk.spring.api.domain.order.Order;
import com.sample.cafekiosk.spring.api.domain.order.OrderRepository;
import com.sample.cafekiosk.spring.api.domain.order.enumtype.OrderStatus;
import com.sample.cafekiosk.spring.api.domain.product.Product;
import com.sample.cafekiosk.spring.api.domain.product.ProductRepository;
import com.sample.cafekiosk.spring.api.domain.product.enumtype.ProductSellingStatus;
import com.sample.cafekiosk.spring.api.domain.product.enumtype.ProductType;
import com.sample.cafekiosk.spring.client.MailSendClient;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import static com.sample.cafekiosk.spring.api.domain.order.enumtype.OrderStatus.INIT;
import static com.sample.cafekiosk.spring.api.domain.order.enumtype.OrderStatus.PAYMENT_COMPLETED;
import static com.sample.cafekiosk.spring.api.domain.product.enumtype.ProductSellingStatus.HOLD;
import static com.sample.cafekiosk.spring.api.domain.product.enumtype.ProductSellingStatus.SELLING;
import static com.sample.cafekiosk.spring.api.domain.product.enumtype.ProductType.BOTTLE;
import static com.sample.cafekiosk.spring.api.domain.product.enumtype.ProductType.HANDMADE;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

class OrderStatisticsServiceTest extends IntegrationTestSupport {

    @Autowired
    private OrderStatisticsService orderStatisticsService;

    @Autowired
    private MailSendHistoryRepository mailSendHistoryRepository;

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private ProductRepository productRepository;

    @MockBean
    private MailSendClient mailSendClient;

    @Test
    @DisplayName("결제완료 주문들을 조회하여 매출 통계 메일을 전송한다.")
    void sendOrderStatisticsMail() {
        // given
        Product product1 = createProduct("001", HANDMADE, SELLING, "아메리카노", 4000);
        Product product2 = createProduct("002", BOTTLE, SELLING, "카페모카", 4500);
        Product product3 = createProduct("003", BOTTLE, HOLD, "라떼", 5000);
        productRepository.saveAll(List.of(product1, product2, product3));

        createOrder(List.of(product1, product2), PAYMENT_COMPLETED, LocalDateTime.of(2023, 9, 23, 23, 59, 59));
        createOrder(List.of(product1, product2), PAYMENT_COMPLETED, LocalDateTime.of(2023, 9, 24, 23, 59, 59));
        createOrder(List.of(product1, product2), INIT, LocalDateTime.of(2023, 9, 24, 23, 59, 59));
        createOrder(List.of(product1, product2), PAYMENT_COMPLETED, LocalDateTime.of(2023, 9, 25, 0, 0, 0));

        // stubbing
        when(mailSendClient.sendEmail(anyString(), anyString(), anyString(), anyString())).thenReturn(true);

        // when
        boolean result = orderStatisticsService.sendOrderStatisticsMail(LocalDate.of(2023, 9, 24), "tester@test.com");

        // then
        assertThat(result).isTrue();

        List<MailSendHistory> mailSendHistories = mailSendHistoryRepository.findAll();
        assertThat(mailSendHistories).hasSize(1)
                .extracting("content")
                .contains("총 매출 합계 : 8500 원");
    }

    private Order createOrder(List<Product> products, OrderStatus orderStatus, LocalDateTime registeredDateTime) {
        Order order = Order.builder()
                .products(products)
                .orderStatus(orderStatus)
                .registeredDateTime(registeredDateTime)
                .build();
        orderRepository.save(order);
        return order;
    }

    private Product createProduct(
            String productNumber
            , ProductType type
            , ProductSellingStatus sellingStatus
            , String name
            , int price
    ) {
        return Product.builder()
                .productNumber(productNumber)
                .type(type)
                .sellingStatus(sellingStatus)
                .name(name)
                .price(price)
                .build();
    }


}