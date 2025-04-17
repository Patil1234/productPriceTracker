package com.myapp.productPriceTracker;

import com.myapp.productPriceTracker.dto.PriceAlertRequest;
import com.myapp.productPriceTracker.model.PriceAlert;
import com.myapp.productPriceTracker.repository.PriceAlertRepository;
import com.myapp.productPriceTracker.service.AlertService;
import com.myapp.productPriceTracker.service.EmailService;
import com.myapp.productPriceTracker.service.PriceCheckerService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class ProductPriceTrackerTests {

    @Mock
    private PriceAlertRepository repository;

    @Mock
    private EmailService emailService;

    @InjectMocks
    private AlertService alertService;

    private PriceCheckerService priceCheckerService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        priceCheckerService = new PriceCheckerService(alertService);
    }

    @Test
    void testCreateAlert() {
        PriceAlertRequest request = new PriceAlertRequest();
        request.setProductUrl("https://example.com/product12345");
        request.setDesiredPrice(100);
        request.setFrequency("daily");
        request.setEmail("dhirajkhade10@gmail.com");
        request.setCurrentPrice(120);

        alertService.saveAlert(request);

        ArgumentCaptor<PriceAlert> captor = ArgumentCaptor.forClass(PriceAlert.class);
        verify(repository, times(1)).save(captor.capture());

        PriceAlert saved = captor.getValue();
        assertEquals("https://example.com/product12345", saved.getProductUrl());
        assertEquals(100, saved.getDesiredPrice());
        assertEquals("dhirajkhade10@gmail.com", saved.getEmail());
        assertFalse(saved.getNotified());
    }

    @Test
    void testUpdateCurrentPrice() {
        PriceAlert alert = new PriceAlert(1L, "https://example.com/product12345", 150, "daily", "dhirajkhade10@gmail.com", false, 180);
        when(repository.findAllByProductUrl("https://example.com/product12345")).thenReturn(List.of(alert));

        alertService.updateCurrentPriceForProduct("https://example.com/product12345", 140);

        assertEquals(140, alert.getCurrentPrice());
        assertFalse(alert.getNotified());
        verify(repository, times(1)).saveAll(any());
    }

    @Test
    void testPriceCheckerService() {
        PriceAlert alert = new PriceAlert(1L, "https://example.com/product12345", 100, "daily", "dhirajkhade10@gmail.com", false, 90);
        when(repository.findTopByProductUrlOrderByIdDesc("https://example.com/product12345")).thenReturn(alert);

        double price = priceCheckerService.getCurrentPrice("https://example.com/product12345");

        assertEquals(90, price);
    }

    @Test
    void testSendEmailNotification() {
        doNothing().when(emailService).sendNotification(anyString(), anyString(), anyDouble());

        emailService.sendNotification("dhirajkhade10@gmail.com", "https://example.com/product12345", 79.99);

        verify(emailService, times(1)).sendNotification("dhirajkhade10@gmail.com", "https://example.com/product12345", 79.99);
    }

    @Test
    void testNoNotificationIfAlreadyNotified() {
        PriceAlert alert = new PriceAlert(1L, "https://example.com/product12345", 150, "daily", "dhirajkhade10@gmail.com", true, 100);
        when(repository.findAll()).thenReturn(List.of(alert));

        List<PriceAlert> all = alertService.getAllAlerts();
        assertTrue(all.get(0).getNotified());
    }
}