package com.coding.tmobile.bo;

import com.coding.tmobile.provider.ISO8601ParamDeserializer;
import com.coding.tmobile.request.OrderPriceRequest;
import com.coding.tmobile.response.OrderPriceResponse;
import com.coding.tmobile.service.JsonFileParser;
import com.coding.tmobile.service.FinalPriceCalculator;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;

/**
 * Created by maxmjn20 on 7/2/17.
 */
@RunWith(MockitoJUnitRunner.class)
public class OrderPriceCalculatorBOTest {

    @InjectMocks
    OrderPriceCalculatorBO orderPriceCalculatorBO;

    @Mock
    JsonFileParser jsonFileParser;

    @Mock
    FinalPriceCalculator finalPriceCalculator;

    @Test
    public void calculateFinalPrice() throws Exception {
        OrderPriceRequest orderPriceRequest = new OrderPriceRequest();
        List<Integer> items = Arrays.asList(1,2);
        orderPriceRequest.setItemId(items);
        ISO8601ParamDeserializer asOf = new ISO8601ParamDeserializer("2017-07-01T23:59:59Z");
        orderPriceRequest.setAsOf(asOf.getDate());
        BigDecimal finalPrice = new BigDecimal(100.00);
        Mockito.when(finalPriceCalculator.finalPrice(Mockito.anyObject(), Mockito.anyObject()))
                .thenReturn(finalPrice);
        OrderPriceResponse response = orderPriceCalculatorBO.calculateFinalPrice(orderPriceRequest);
    }

}