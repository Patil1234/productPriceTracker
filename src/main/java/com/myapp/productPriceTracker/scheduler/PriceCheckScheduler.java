package com.myapp.productPriceTracker.scheduler;

import com.myapp.productPriceTracker.model.PriceAlert;
import com.myapp.productPriceTracker.service.AlertService;
import com.myapp.productPriceTracker.service.EmailService;
import com.myapp.productPriceTracker.service.PriceCheckerService;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class PriceCheckScheduler {

    private final AlertService alertService;
    private final PriceCheckerService checkerService;
    private final EmailService emailService;

    public PriceCheckScheduler(AlertService alertService, PriceCheckerService checkerService, EmailService emailService) {
        this.alertService = alertService;
        this.checkerService = checkerService;
        this.emailService = emailService;
    }

    @Scheduled(fixedRate = 60000) // every 1 minute for demo purposes
    public void checkPrices() {
        for (PriceAlert alert : alertService.getAllAlerts()) {
            if (Boolean.FALSE.equals(alert.getNotified())) {
                double currentPrice = checkerService.getCurrentPrice(alert.getProductUrl());
                if (currentPrice <= alert.getDesiredPrice()) {
                    emailService.sendNotification(alert.getEmail(), alert.getProductUrl(), currentPrice);
                    alert.setNotified(true);
                    alert.setCurrentPrice(currentPrice);
                    alertService.updateAlert(alert);
                }
            }
        }
    }
}
