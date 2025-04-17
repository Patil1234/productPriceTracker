package com.myapp.productPriceTracker.service;

import org.springframework.stereotype.Service;

@Service
public class PriceCheckerService {

    private final AlertService alertService;

    public PriceCheckerService(AlertService alertService) {
        this.alertService = alertService;
    }

    public double getCurrentPrice(String productUrl) {
        return alertService.getCurrentPriceFromDB(productUrl);
    }
}
