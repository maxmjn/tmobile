package com.coding.tmobile.request;

import com.coding.tmobile.provider.LocalDateTimeDeserializer;
import com.coding.tmobile.provider.LocalDateTimeSerializer;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
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
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    private LocalDateTime asOf;
}
