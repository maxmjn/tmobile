package com.coding.tmobile.service;

import com.coding.tmobile.model.OrderItem;
import com.coding.tmobile.request.OrderPriceRequest;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by maxmjn20 on 7/2/17.
 */
@RunWith(MockitoJUnitRunner.class)
public class FinalPriceCalculatorTest {

    @InjectMocks
    FinalPriceCalculator finalPriceCalculator;

    @Test
    public void finalPrice() throws Exception {
        OrderItem orderItem = new OrderItem();
        OrderPriceRequest orderPriceRequest = new OrderPriceRequest();
        Map<String, Map<String, Object>> promotions = new HashMap<>();
        Map<String, Object> promo = new HashMap<>();
        promo.put("A", "a");
        promo.put("B", 200);
        promotions.put("P1", promo);
        orderItem.setPromotions(promotions);
        finalPriceCalculator.finalPrice(orderItem, orderPriceRequest);
    }

}