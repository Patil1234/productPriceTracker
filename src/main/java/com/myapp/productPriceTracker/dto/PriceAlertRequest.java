package com.myapp.productPriceTracker.dto;

public class PriceAlertRequest {
        private String productUrl;
        private double desiredPrice;
        private String frequency; // e.g., daily, morning, midnight
        private String email;
        private double currentPrice;

        public String getProductUrl() {
                return productUrl;
        }

        public void setProductUrl(String productUrl) {
                this.productUrl = productUrl;
        }

        public double getDesiredPrice() {
                return desiredPrice;
        }

        public void setDesiredPrice(double desiredPrice) {
                this.desiredPrice = desiredPrice;
        }

        public String getFrequency() {
                return frequency;
        }

        public void setFrequency(String frequency) {
                this.frequency = frequency;
        }

        public String getEmail() {
                return email;
        }

        public void setEmail(String email) {
                this.email = email;
        }

        public double getCurrentPrice() {
                return currentPrice;
        }

        public void setCurrentPrice(double currentPrice) {
                this.currentPrice = currentPrice;
        }
}
