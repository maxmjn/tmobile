package com.coding.tmobile.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.joda.time.LocalDateTime;

import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * Created by maxmjn20 on 6/30/17.
 */
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class OrderPriceRequest {
    @NotNull
    List<Integer> itemId;
    private LocalDateTime asOf;
}
