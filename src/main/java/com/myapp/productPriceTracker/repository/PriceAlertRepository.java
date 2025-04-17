package com.myapp.productPriceTracker.repository;

import com.myapp.productPriceTracker.model.PriceAlert;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PriceAlertRepository extends JpaRepository<PriceAlert, Long> {

    PriceAlert findTopByProductUrlOrderByIdDesc(String productUrl);
    List<PriceAlert> findAllByProductUrl(String productUrl);
}
