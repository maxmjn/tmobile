package com.coding.tmobile.service;

import com.coding.tmobile.bo.OrderPriceCalculatorBO;
import com.coding.tmobile.model.InvalidOrderPriceException;
import com.coding.tmobile.provider.ISO8601ParamDeserializer;
import com.coding.tmobile.request.OrderPriceRequest;
import com.coding.tmobile.response.OrderPriceResponse;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.extern.log4j.Log4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by maxmjn20 on 6/30/17.
 */
@Service
@Setter
@NoArgsConstructor
@Log4j
public class OrderPriceCalculatorService implements IOrderPriceValidator {

    @Autowired
    private OrderPriceCalculatorBO orderPriceCalculatorBO;

    /**
     * calculate final price for all requested orders
     *
     * @param listItems
     * @param asOf
     * @return
     */
    public OrderPriceResponse calculateFinalPrice(List<Integer> listItems, ISO8601ParamDeserializer asOf) throws Exception {

        OrderPriceRequest orderPriceRequest;
        try {
            orderPriceRequest = new OrderPriceRequest(listItems, asOf.getDate());
        } catch (Exception e) {
            throw new InvalidOrderPriceException("OrderPrice params are required");
        }
        validate(orderPriceRequest);
        return orderPriceCalculatorBO.calculateFinalPrice(orderPriceRequest);
    }

    @Override
    public void validate(OrderPriceRequest orderPriceRequest) throws Exception {
        if (null == orderPriceRequest || null == orderPriceRequest.getItemId() ||
                orderPriceRequest.getItemId().isEmpty() || null == orderPriceRequest.getAsOf()) {
            throw new InvalidOrderPriceException("OrderPrice params are required");
        }
    }
}
