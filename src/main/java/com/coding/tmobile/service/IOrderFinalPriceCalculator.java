package com.coding.tmobile.service;

import com.coding.tmobile.model.OrderItem;
import com.coding.tmobile.request.OrderPriceRequest;

import java.math.BigDecimal;

/**
 * Created by maxmjn20 on 6/30/17.
 */
public interface IOrderFinalPriceCalculator {

    /**
     * calculate finalPrice based on orderItem discount, promotions
     * @param orderItem
     * @param orderPriceRequest
     * @return
     */
    BigDecimal finalPrice(OrderItem orderItem, OrderPriceRequest orderPriceRequest);
}
