package com.sample.cafekiosk.spring.api.domain.stock;

import com.sample.cafekiosk.spring.IntegrationTestSupport;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.tuple;

class StockRepositoryTest extends IntegrationTestSupport {

    @Autowired
    private StockRepository stockRepository;

    @Test
    @DisplayName("상품 번호 리스트로 재고를 조회한다.")
    void findAllByProductNumberIn() {
        // given
        Stock stock1 = Stock.create("001", 2);
        Stock stock2 = Stock.create("002", 3);
        stockRepository.saveAll(List.of(stock1, stock2));

        // when
        List<Stock> stocks = stockRepository.findAllByProductNumberIn(List.of("001", "002"));

        //then
        assertThat(stocks).hasSize(2)
                .extracting("productNumber", "quantity")
                .containsExactlyInAnyOrder(
                        tuple("001", 2),
                        tuple("002", 3)
                );
    }

}
