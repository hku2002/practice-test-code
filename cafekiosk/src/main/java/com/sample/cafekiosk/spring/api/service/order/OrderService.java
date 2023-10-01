package com.sample.cafekiosk.spring.api.service.order;

import com.sample.cafekiosk.spring.api.domain.order.Order;
import com.sample.cafekiosk.spring.api.domain.order.OrderRepository;
import com.sample.cafekiosk.spring.api.domain.product.Product;
import com.sample.cafekiosk.spring.api.domain.product.ProductRepository;
import com.sample.cafekiosk.spring.api.domain.product.enumtype.ProductType;
import com.sample.cafekiosk.spring.api.domain.stock.Stock;
import com.sample.cafekiosk.spring.api.domain.stock.StockRepository;
import com.sample.cafekiosk.spring.api.service.order.request.OrderCreateServiceRequest;
import com.sample.cafekiosk.spring.api.service.order.response.OrderResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class OrderService {

    private final ProductRepository productRepository;
    private final OrderRepository orderRepository;
    private final StockRepository stockRepository;

    public OrderResponse createOrder(OrderCreateServiceRequest request, LocalDateTime registeredDateTime) {
        List<String> productNumbers = request.getProductNumbers();
        List<Product> products = findDuplicateProducts(productNumbers);

        deductStockQuantities(products);

        Order order = Order.create(products, registeredDateTime);
        return OrderResponse.from(orderRepository.save(order));
    }

    private void deductStockQuantities(List<Product> products) {
        List<String> stockProductNumbers = extractStockProductNumbers(products);
        Map<String, Stock> stockMap = createStockMap(stockProductNumbers);
        Map<String, Long> productCountingMap = createCountingMap(stockProductNumbers);

        for (String stockProductNumber : new HashSet<>(stockProductNumbers)) {
            Stock stock = stockMap.get(stockProductNumber);
            int quantity = productCountingMap.get(stockProductNumber).intValue();

            if (stock.isQuantityLessThan(quantity)) {
                throw new IllegalStateException("재고가 부족한 상품이 있습니다.");
            }
            stock.deductQuantity(quantity);
        }
    }

    private List<String> extractStockProductNumbers(List<Product> products) {
        return products.stream()
                .filter(product -> ProductType.containsStockManagedType(product.getType()))
                .map(Product::getProductNumber)
                .collect(Collectors.toList());
    }

    private Map<String, Stock> createStockMap(List<String> stockProductNumbers) {
        List<Stock> stocks = stockRepository.findAllByProductNumberIn(stockProductNumbers);
        return stocks.stream()
                .collect(Collectors.toMap(Stock::getProductNumber, s -> s));
    }

    private Map<String, Long> createCountingMap(List<String> stockProductNumbers) {
        return stockProductNumbers.stream()
                .collect(Collectors.groupingBy(p -> p, Collectors.counting()));
    }

    private List<Product> findDuplicateProducts(List<String> productNumbers) {
        List<Product> products = productRepository.findAllByProductNumberIn(productNumbers);
        Map<String, Product> productMap = products.stream()
                .collect(Collectors.toMap(Product::getProductNumber, p -> p));

        return productNumbers.stream()
                .map(productMap::get)
                .collect(Collectors.toList());
    }
}
