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
        // 해당 일자에 결제완료된 주문들을 가져온다.
        List<Order> orders = orderRepository.findOrdersBy(
                orderDate.atStartOfDay(),
                orderDate.plusDays(1).atStartOfDay(),
                PAYMENT_COMPLETED
        );

        // 총 매출 합계를 계산한다.
        int totalAmount = orders.stream()
                .mapToInt(Order::getTotalPrice)
                .sum();

        // 메일을 전송한다.
        boolean sendResult = mailService.sendMail(
                "no-reply@cafe-kiosk.com"
                , email
                , String.format("[매출통계] %s", orderDate)
                , String.format("총 매출 합계 : %s 원", totalAmount));

        if (!sendResult) throw new IllegalStateException("매출 통계 메일 전송에 실패하였습니다.");

        return true;
    }
}
