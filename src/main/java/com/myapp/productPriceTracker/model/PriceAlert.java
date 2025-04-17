package com.myapp.productPriceTracker.model;

import jakarta.persistence.*;


@Entity
@Table(name = "price_alert")
public class PriceAlert {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String productUrl;
    private double desiredPrice;
    private String frequency;
    private String email;
    private Boolean notified;
    private double currentPrice;

    public PriceAlert() {
    }

    public PriceAlert(Long id, String productUrl, double desiredPrice, String frequency, String email, Boolean notified, double currentPrice) {
        this.id = id;
        this.productUrl = productUrl;
        this.desiredPrice = desiredPrice;
        this.frequency = frequency;
        this.email = email;
        this.notified = notified;
        this.currentPrice = currentPrice;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getProductUrl() {
        return productUrl;
    }

    public void setProductUrl(String productUrl) {
        this.productUrl = productUrl;
    }

    public String getFrequency() {
        return frequency;
    }

    public void setFrequency(String frequency) {
        this.frequency = frequency;
    }

    public double getDesiredPrice() {
        return desiredPrice;
    }

    public void setDesiredPrice(double desiredPrice) {
        this.desiredPrice = desiredPrice;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Boolean getNotified() {
        return notified;
    }

    public void setNotified(Boolean notified) {
        this.notified = notified;
    }

    public double getCurrentPrice() {
        return currentPrice;
    }

    public void setCurrentPrice(double currentPrice) {
        this.currentPrice = currentPrice;
    }
}
