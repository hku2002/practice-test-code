package com.sample.cafekiosk.unit.beverage;

import com.sample.cafekiosk.unit.Beverage;

public class Latte implements Beverage {
    @Override
    public String getName() {
        return "라떼";
    }

    @Override
    public int getPrice() {
        return 4_500;
    }
}
