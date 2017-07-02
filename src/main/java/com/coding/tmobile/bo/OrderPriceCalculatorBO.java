package com.coding.tmobile.bo;

import com.coding.tmobile.model.OrderItem;
import com.coding.tmobile.model.ProductInfo;
import com.coding.tmobile.request.OrderPriceRequest;
import com.coding.tmobile.response.OrderPriceResponse;
import com.coding.tmobile.service.IOrderFinalPrice;
import com.coding.tmobile.service.JsonFileParser;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.joda.time.Hours;
import org.joda.time.LocalDateTime;
import org.joda.time.Minutes;
import org.joda.time.Period;
import org.joda.time.format.DateTimeFormat;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Map;

/**
 * Created by maxmjn20 on 6/30/17.
 */
@Setter
@NoArgsConstructor
@Service
public class OrderPriceCalculatorBO implements IOrderFinalPrice{

    @Autowired
    JsonFileParser jsonFileParser;

    /**
     * calculate final price for all requested orders
     * -- for each itemId in request, build order
     * -- calculate final price for order
     * @param orderPriceRequest
     * @return
     */
    public OrderPriceResponse calculateFinalPrice(OrderPriceRequest orderPriceRequest){

        OrderPriceResponse orderPriceResponse = new OrderPriceResponse();
        List<OrderItem> orderItemList =  new ArrayList<>();

        BigDecimal totalPrice = new BigDecimal(0.0);

        for (Integer itemId : orderPriceRequest.getItemId()) {
            ProductInfo productDetail = jsonFileParser.getProductCache().get(itemId);
            if(null != productDetail) {
                OrderItem orderItem = buildOrderItem(productDetail);
                BigDecimal finalPrice = finalPrice(orderItem, orderPriceRequest);
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
     * Discount% if available is applied on BasePrice
     * All available Promotions are applied on Discounted BasePrice
     * @param orderItem
     * @param orderPriceRequest
     * @return
     */
    @Override
    public BigDecimal finalPrice(OrderItem orderItem, OrderPriceRequest orderPriceRequest) {

        BigDecimal PERCENT = new BigDecimal(100);
        BigDecimal discount = orderItem.getDiscount();
        BigDecimal basePrice = orderItem.getBasePrice();
        Map<String,Map<String, Object>> promotions = orderItem.getPromotions();

        BigDecimal finalPrice;
        LocalDateTime asOf = orderPriceRequest.getAsOf();

        //TODO check Discount start-end within AsOf before applying discount
        //Discount available
        if(null != discount && discount.doubleValue() != 0.0){
            finalPrice = basePrice.subtract(basePrice.multiply(discount).divide(PERCENT));
        } else {
            finalPrice = basePrice;
        }

        //Promotions
        for (String promo:promotions.keySet()) {
            Map<String, Object> promoDetails = promotions.get(promo);
            /*
               if promotions need to be sorted by weight
		    Map<Integer, String> treeMap = new TreeMap<>(
		                (Comparator<Integer>) (o1, o2) -> o2.compareTo(o1)
		        );
		    */

            if(null != promoDetails.get("end")) {
                LocalDateTime end = DateTimeFormat.forPattern("yyyy-MM-dd'T'HH:mm:ss'Z'")
                        .withLocale(Locale.ENGLISH)
                        .parseLocalDateTime((String) promoDetails.get("end"));

                Minutes diff = Minutes.minutesBetween(asOf, end);
                if (diff.getMinutes() < 0) {
                    continue;
                }
            }

            BigDecimal promoDiscount = BigDecimal.valueOf((Double) promoDetails.get("percentOff"));
            finalPrice = finalPrice.subtract(basePrice.multiply(promoDiscount).divide(PERCENT));
        }
        return finalPrice;
    }

    /**
     *
     * @param productDetail
     * @return
     */
    private OrderItem buildOrderItem(ProductInfo productDetail) {

        OrderItem orderItem = new OrderItem();
        orderItem.setItemId(productDetail.getItemId());
        orderItem.setBasePrice(productDetail.getBasePrice());
        orderItem.setDiscount(productDetail.getDiscount());
        orderItem.setPromotions(productDetail.getPromotions());
        return orderItem;
    }
}
