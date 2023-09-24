package com.sample.cafekiosk.spring.api.service.order;

import com.sample.cafekiosk.spring.api.domain.order.Order;
import com.sample.cafekiosk.spring.api.domain.order.OrderRepository;
import com.sample.cafekiosk.spring.api.service.mail.MailService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

import static com.sample.cafekiosk.spring.api.domain.order.enumtype.OrderStatus.PAYMENT_COMPLETED;

@Service
@RequiredArgsConstructor
public class OrderStatisticsService {

    private final OrderRepository orderRepository;
    private final MailService mailService;

    public boolean sendOrderStatisticsMail(LocalDate orderDate, String email) {
        List<Order> orders = findPaymentCompletedOrders(orderDate);

        boolean sendResult = mailService.sendMail(
                "no-reply@cafe-kiosk.com"
                , email
                , String.format("[매출통계] %s", orderDate)
                , String.format("총 매출 합계 : %s 원", calculateTotalAmount(orders)));

        if (!sendResult) throw new IllegalStateException("매출 통계 메일 전송에 실패하였습니다.");

        return true;
    }

    private List<Order> findPaymentCompletedOrders(LocalDate orderDate) {
        return orderRepository.findOrdersBy(
                orderDate.atStartOfDay(),
                orderDate.plusDays(1).atStartOfDay(),
                PAYMENT_COMPLETED
        );
    }

    private int calculateTotalAmount(List<Order> orders) {
        return orders.stream()
                .mapToInt(Order::getTotalPrice)
                .sum();
    }
}
