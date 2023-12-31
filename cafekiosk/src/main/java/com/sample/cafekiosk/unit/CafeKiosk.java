package com.sample.cafekiosk.unit;

import com.sample.cafekiosk.unit.order.Order;
import lombok.Getter;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Getter
public class CafeKiosk {

    public static final LocalTime SHOP_OPEN_TIME = LocalTime.of(10, 0);
    public static final LocalTime SHOP_CLOSE_TIME = LocalTime.of(22, 0);

    private final List<Beverage> beverages = new ArrayList<>();

    public void add(Beverage beverage) {
        beverages.add(beverage);
    }

    public void add(Beverage beverage, int count) {
        if (count <= 0) {
            throw new IllegalStateException("음료는 1잔 이상 주문하실 수 있습니다.");
        }

        beverages.addAll(IntStream.range(0, count)
                .mapToObj(i -> beverage)
                .collect(Collectors.toList()));
    }

    public void remove(Beverage beverage) {
        beverages.remove(beverage);
    }

    public void clear() {
        beverages.clear();
    }

    public int calculateTotalPrice() {
        return beverages.stream().mapToInt(Beverage::getPrice).sum();
    }

    public Order createOrder(LocalDateTime localDateTime) {
        LocalTime currentTime = localDateTime.toLocalTime();
        if (currentTime.isBefore(SHOP_OPEN_TIME) || currentTime.isAfter(SHOP_CLOSE_TIME)) {
            throw new IllegalStateException("주문 시간이 아닙니다. 관리자에게 문의하세요.");
        }

        return new Order(LocalDateTime.now(), beverages);
    }
}
