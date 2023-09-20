package com.sample.cafekiosk.unit.beverage;

import com.sample.cafekiosk.unit.Beverage;

public class Americano implements Beverage {
    @Override
    public String getName() {
        return "아메리카노";
    }

    @Override
    public int getPrice() {
        return 4_000;
    }
}
