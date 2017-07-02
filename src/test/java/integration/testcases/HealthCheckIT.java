package integration.testcases;

import com.coding.tmobile.PricingCalculatorApp;
import com.coding.tmobile.PricingCalculatorConfig;
import com.jayway.restassured.RestAssured;
import com.jayway.restassured.path.json.JsonPath;
import io.dropwizard.testing.ResourceHelpers;
import io.dropwizard.testing.junit.DropwizardAppRule;
import org.apache.http.HttpStatus;
import org.glassfish.jersey.client.JerseyClientBuilder;
import org.hamcrest.Matchers;
import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;

import javax.ws.rs.client.Client;
import java.util.Map;

import static com.jayway.restassured.RestAssured.given;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsString;

/**
 * Created by maxmjn20 on 7/1/17.
 */
public class HealthCheckIT {

    @Rule
    public final DropwizardAppRule<PricingCalculatorConfig> RULE =
            new DropwizardAppRule<PricingCalculatorConfig>(PricingCalculatorApp.class,
                    ResourceHelpers.resourceFilePath("config.yml"));

    @Test
    public void runServerTest() {
        Client client = new JerseyClientBuilder().build();
        String result = client.target(
                String.format("http://localhost:%d/healthcheck", RULE.getAdminPort())
        ).queryParam("name", "dropwizard").request().get(String.class);

        Assert.assertThat(result, Matchers.containsString("OK with version:"));

        RestAssured.port = RULE.getAdminPort();
        RestAssured.basePath = "/";
        RestAssured.baseURI = "http://localhost";
        JsonPath result1 = given()
                .when()
                .get("/healthcheck")
                .then()
                .statusCode(HttpStatus.SC_OK)
                .extract().jsonPath();
        Map<String,Map<String, String>> map = (Map<String, Map<String, String>>)result1.get();
        Map<String, String> result2 = map.get("template");
        assertThat("testHealthCheck failed", result2.get("message") , containsString("OK with version:"));

    }

}
