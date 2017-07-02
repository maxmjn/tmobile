package com.coding.tmobile.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Map;

/**
 * Created by maxmjn20 on 6/30/17.
 */
@Getter
@Setter
@NoArgsConstructor
public class ProductInfo {
    private Integer itemId;
    private BigDecimal basePrice;
    private BigDecimal discount;

    private LocalDateTime discountStart;

    private LocalDateTime discountEnd;

    private Map<String,Map<String, Object>> promotions;
}
