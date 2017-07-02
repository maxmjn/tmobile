package com.coding.tmobile.service;

import com.coding.tmobile.request.OrderPriceRequest;

/**
 * Created by maxmjn20 on 7/2/17.
 */
public interface IOrderPriceValidator {
    /**
     * Validates request params
     * @param orderPriceRequest
     * @throws Exception
     */
    void validate(OrderPriceRequest orderPriceRequest) throws Exception;
}
