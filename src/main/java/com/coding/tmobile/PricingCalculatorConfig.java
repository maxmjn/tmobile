package com.coding.tmobile;

import io.dropwizard.Configuration;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.NotEmpty;

/**
 * Created by maxmjn20 on 6/30/17.
 */
@Getter
@Setter
public class PricingCalculatorConfig extends Configuration {
    @NotEmpty
    private String version;
}
