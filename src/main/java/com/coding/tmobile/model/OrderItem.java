package com.coding.tmobile.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.joda.time.LocalDateTime;

import java.math.BigDecimal;
import java.util.Map;

/**
 * Created by maxmjn20 on 6/30/17.
 */
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class OrderItem {
    private Integer itemId;
    private BigDecimal finalPrice;
    private BigDecimal basePrice;
    private BigDecimal discount;
    Map<String,Map<String,Object>> promotions;

    @JsonIgnore
    private LocalDateTime discountStart;
    @JsonIgnore
    private LocalDateTime discountEnd;
}
