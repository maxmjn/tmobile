package com.coding.tmobile.service;

import com.coding.tmobile.bo.OrderPriceCalculatorBO;
import com.coding.tmobile.model.InvalidOrderPriceException;
import com.coding.tmobile.provider.ISO8601ParamDeserializer;
import com.coding.tmobile.request.OrderPriceRequest;
import com.coding.tmobile.response.OrderPriceResponse;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.*;
import static org.mockito.Matchers.anyObject;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * Created by maxmjn20 on 7/2/17.
 */
@RunWith(MockitoJUnitRunner.class)
public class OrderPriceCalculatorServiceTest {

    @InjectMocks
    OrderPriceCalculatorService orderPriceCalculatorService;

    @Mock
    OrderPriceCalculatorBO orderPriceCalculatorBO;

    @Test
    public void calculateFinalPrice() throws Exception {
        List<Integer> items = Arrays.asList(1,2);
        ISO8601ParamDeserializer asOf = new ISO8601ParamDeserializer("2017-07-01T23:59:59Z");
        OrderPriceResponse response = new OrderPriceResponse();
        when(orderPriceCalculatorBO.calculateFinalPrice(anyObject())).thenReturn(response);

        orderPriceCalculatorService.calculateFinalPrice(items, asOf);

        verify(orderPriceCalculatorBO).calculateFinalPrice(anyObject());
    }

    @Test(expected = InvalidOrderPriceException.class)
    public void testValidate_empty() throws Exception {
        OrderPriceRequest orderPriceRequest = new OrderPriceRequest();
        orderPriceCalculatorService.validate(orderPriceRequest);

        List<Integer> items = Arrays.asList(1,2);
        orderPriceRequest.setItemId(items);
        orderPriceCalculatorService.validate(orderPriceRequest);
    }

    @Test(expected = InvalidOrderPriceException.class)
    public void testValidate_emptyAsOf() throws Exception {
        OrderPriceRequest orderPriceRequest = new OrderPriceRequest();
        List<Integer> items = Arrays.asList(1,2);
        orderPriceRequest.setItemId(items);
        orderPriceCalculatorService.validate(orderPriceRequest);
    }

}