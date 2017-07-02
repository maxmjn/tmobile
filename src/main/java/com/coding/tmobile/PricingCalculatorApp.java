package com.coding.tmobile;

import com.coding.tmobile.resource.OrderPriceCalculatorResource;
import io.dropwizard.Application;
import io.dropwizard.setup.Environment;

/**
 * Created by maxmjn20 on 6/30/17.
 */
public class PricingCalculatorApp extends Application<PricingCalculatorConfig> {

    public static void main(String[] args) throws Exception {
        new PricingCalculatorApp().run(args);
    }

    @Override
    public void run(PricingCalculatorConfig config, Environment env) {
        final OrderPriceCalculatorResource orderPriceCalculatorResource = new OrderPriceCalculatorResource();
        env.jersey().register(orderPriceCalculatorResource);

        env.healthChecks().register("template",
                new PricingCalculatorHealthCheck(config.getVersion()));
    }

}