package com.myapp.productPriceTracker.service;

import com.myapp.productPriceTracker.dto.PriceAlertRequest;
import com.myapp.productPriceTracker.model.PriceAlert;
import com.myapp.productPriceTracker.repository.PriceAlertRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AlertService {


    private final PriceAlertRepository repository;

    public AlertService(PriceAlertRepository repository) {
        this.repository = repository;
    }

    public void saveAlert(PriceAlertRequest request) {
        repository.save(new PriceAlert(
                null,
                request.getProductUrl(),
                request.getDesiredPrice(),
                request.getFrequency(),
                request.getEmail(),
        false,
                request.getCurrentPrice()
        ));
    }

    public List<PriceAlert> getAllAlerts() {
        return repository.findAll();
    }

    public void updateAlert(PriceAlert alert) {
        repository.save(alert);
    }

    public double getCurrentPriceFromDB(String productUrl) {
        PriceAlert alert = repository.findTopByProductUrlOrderByIdDesc(productUrl);
        return alert != null ? alert.getCurrentPrice() : Double.MAX_VALUE;
    }

    public void updateCurrentPriceForProduct(String productUrl, double newPrice) {
        List<PriceAlert> alerts = repository.findAllByProductUrl(productUrl);
        for (PriceAlert alert : alerts) {
            alert.setCurrentPrice(newPrice);
            alert.setNotified(false);
        }
        repository.saveAll(alerts);
    }

}
