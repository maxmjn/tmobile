package com.coding.tmobile.bo;

import com.coding.tmobile.model.OrderItem;
import com.coding.tmobile.model.ProductInfo;
import com.coding.tmobile.request.OrderPriceRequest;
import com.coding.tmobile.response.OrderPriceResponse;
import com.coding.tmobile.service.JsonFileParser;
import com.coding.tmobile.service.FinalPriceCalculator;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.extern.log4j.Log4j;
import org.joda.time.LocalDateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by maxmjn20 on 6/30/17.
 */
@Setter
@NoArgsConstructor
@Service
@Log4j
public class OrderPriceCalculatorBO {

    @Autowired
    JsonFileParser jsonFileParser;

    @Autowired
    FinalPriceCalculator finalPriceCalculator;

    /**
     * calculate final price for all requested orders
     * -- for each itemId in request, build order
     * -- calculate final price for order
     *
     * @param orderPriceRequest
     * @return
     */
    public OrderPriceResponse calculateFinalPrice(OrderPriceRequest orderPriceRequest) {

        OrderPriceResponse orderPriceResponse = new OrderPriceResponse();
        List<OrderItem> orderItemList = new ArrayList<>();

        BigDecimal totalPrice = new BigDecimal(0.0);

        for (Integer itemId : orderPriceRequest.getItemId()) {
            ProductInfo productInfo = jsonFileParser.getProductCache().get(itemId);
            if (null != productInfo) {
                OrderItem orderItem = buildOrderItem(productInfo);
                BigDecimal finalPrice = finalPriceCalculator.finalPrice(orderItem, orderPriceRequest);
                orderItem.setFinalPrice(finalPrice);
                orderItemList.add(orderItem);
                totalPrice = totalPrice.add(finalPrice);
            }
        }

        //build response
        orderPriceResponse.setAsOf(orderPriceRequest.getAsOf());
        orderPriceResponse.setTotalPrice(totalPrice);
        orderPriceResponse.setItems(orderItemList);

        return orderPriceResponse;
    }

    /**
     * Converts ProductInfo java.time.LocalDateTime to Joda
     * @param productDetail
     * @return
     */
    private OrderItem buildOrderItem(ProductInfo productDetail) {

        OrderItem orderItem = new OrderItem();
        orderItem.setItemId(productDetail.getItemId());
        orderItem.setBasePrice(productDetail.getBasePrice());
        orderItem.setDiscount(productDetail.getDiscount());
        orderItem.setPromotions(productDetail.getPromotions());

        LocalDateTime jodaLocalDateTime;
        if(null != productDetail.getDiscountStart()) {
            jodaLocalDateTime = new org.joda.time.LocalDateTime(
                    productDetail.getDiscountStart()
                            .atZone(ZoneId.systemDefault())
                            .toInstant()
                            .toEpochMilli()
            );

            orderItem.setDiscountStart(jodaLocalDateTime);
        } else{
            orderItem.setDiscountStart(null);
        }
        if(null != productDetail.getDiscountEnd()) {
            jodaLocalDateTime = new org.joda.time.LocalDateTime(
                    productDetail.getDiscountEnd()
                            .atZone(ZoneId.systemDefault())
                            .toInstant()
                            .toEpochMilli()
            );
            orderItem.setDiscountEnd(jodaLocalDateTime);
        } else{
            orderItem.setDiscountEnd(null);
        }

        return orderItem;
    }
}
