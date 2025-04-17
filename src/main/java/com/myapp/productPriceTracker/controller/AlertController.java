package com.myapp.productPriceTracker.controller;

import com.myapp.productPriceTracker.dto.PriceAlertRequest;
import com.myapp.productPriceTracker.dto.UpdatePriceRequest;
import com.myapp.productPriceTracker.service.AlertService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/alerts")
public class AlertController {
    private final AlertService alertService;

    public AlertController(AlertService alertService) {
        this.alertService = alertService;
    }

    @PostMapping("/create-alert")
    public ResponseEntity<String> createAlert(@RequestBody PriceAlertRequest request) {
        alertService.saveAlert(request);
        return ResponseEntity.ok("Alert set for " + request.getProductUrl());
    }
    @PostMapping("/update-price")
    public ResponseEntity<String> updateProductPrice(@RequestBody UpdatePriceRequest request) {
        alertService.updateCurrentPriceForProduct(request.getProductUrl(), request.getCurrentPrice());
        return ResponseEntity.ok("Price updated for product: " + request.getProductUrl());
    }
}
