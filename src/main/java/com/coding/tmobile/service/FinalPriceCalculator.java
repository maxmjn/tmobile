package com.coding.tmobile.service;

import com.coding.tmobile.model.OrderItem;
import com.coding.tmobile.request.OrderPriceRequest;
import lombok.extern.log4j.Log4j;
import org.joda.time.LocalDateTime;
import org.joda.time.Minutes;
import org.joda.time.format.DateTimeFormat;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Locale;
import java.util.Map;

/**
 * Created by maxmjn20 on 7/2/17.
 */
@Service
@Log4j
public class FinalPriceCalculator implements IOrderFinalPriceCalculator {

    private static BigDecimal PERCENT = new BigDecimal(100);

    /**
     * Discount% if available is applied on BasePrice
     * All available Promotions are applied on Discounted BasePrice
     *
     * @param orderItem
     * @param orderPriceRequest
     * @return
     */
    @Override
    public BigDecimal finalPrice(OrderItem orderItem, OrderPriceRequest orderPriceRequest) {

        BigDecimal discount = orderItem.getDiscount();
        LocalDateTime discountStart = orderItem.getDiscountStart();
        LocalDateTime discountEnd = orderItem.getDiscountEnd();
        BigDecimal basePrice = orderItem.getBasePrice();
        Map<String, Map<String, Object>> promotions = orderItem.getPromotions();

        BigDecimal finalPrice = new BigDecimal(0.0);

        LocalDateTime asOf = orderPriceRequest.getAsOf();

        //Discount available
        boolean isDiscount = false;
        if (null != discount && discount.doubleValue() != 0.0) {
            if (null != discountStart && null != discountEnd) {
                Minutes diffStart = Minutes.minutesBetween(asOf, discountStart);
                Minutes diffEnd = Minutes.minutesBetween(asOf, discountEnd);
                //asOf is within discount start-end
                isDiscount = diffStart.getMinutes() > 0 && diffEnd.getMinutes() > 0;
            }
        }

        if (isDiscount) {
            finalPrice = basePrice.subtract(basePrice.multiply(discount).divide(PERCENT));
        } else {
            finalPrice = basePrice;
        }

        //Promotions
        for (String promo : promotions.keySet()) {
            Map<String, Object> promoDetails = promotions.get(promo);
            /*
               if promotions need to be sorted by weight
		    Map<Integer, String> treeMap = new TreeMap<>(
		                (Comparator<Integer>) (o1, o2) -> o2.compareTo(o1)
		        );
		    */

            if (null != promoDetails.get("end")) {
                LocalDateTime end = DateTimeFormat.forPattern("yyyy-MM-dd'T'HH:mm:ss'Z'")
                        .withLocale(Locale.ENGLISH)
                        .parseLocalDateTime((String) promoDetails.get("end"));
                Minutes diff = Minutes.minutesBetween(asOf, end);
                if (diff.getMinutes() < 0) {
                    continue;
                }

                BigDecimal promoDiscount = BigDecimal.valueOf((Double) promoDetails.get("percentOff"));
                finalPrice = finalPrice.subtract(basePrice.multiply(promoDiscount).divide(PERCENT));
            }
        }
        return finalPrice;
    }
}
