package com.coding.tmobile;

import com.codahale.metrics.health.HealthCheck;

/**
 * Created by maxmjn20 on 6/30/17.
 *
 * App health check
 */
public class PricingCalculatorHealthCheck extends HealthCheck{

    private final String version;

    public PricingCalculatorHealthCheck(String version) {
        this.version = version;
    }

    @Override
    protected Result check() throws Exception {

        return HealthCheck.Result.healthy("OK with version: " + this.version);
    }
}
