package com.coding.tmobile.response;

import com.coding.tmobile.model.OrderItem;
import com.coding.tmobile.provider.LocalDateTimeSerializer;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.joda.time.LocalDateTime;

import java.math.BigDecimal;
import java.util.List;

/**
 * Created by maxmjn20 on 6/30/17.
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class OrderPriceResponse {
    private BigDecimal totalPrice;
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    private LocalDateTime asOf;
    List<OrderItem> items;
}
