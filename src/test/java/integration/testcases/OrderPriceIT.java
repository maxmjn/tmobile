package integration.testcases;

import com.coding.tmobile.response.OrderPriceResponse;
import com.jayway.restassured.path.json.JsonPath;
import integration.common.StartServer;
import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.junit.Test;

import javax.ws.rs.core.Response;
import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import static com.jayway.restassured.RestAssured.given;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.core.IsNull.notNullValue;

/**
 * Created by maxmjn20 on 7/1/17.
 */

public class OrderPriceIT extends StartServer{

    @Test
    public void testOrderFinalPrice() {
        List<Integer> items = Arrays.asList(201, 101);
        DateTime dateTime = new DateTime(DateTime.parse("2017-07-01T23:59:59Z"));

        DateTimeFormatter fmt = DateTimeFormat.forPattern("yyyy-MM-dd'T'HH:mm:ss'Z'");
        String dtStr = fmt.print(dateTime);

        OrderPriceResponse result = given()
                .queryParam("items", items)
                .queryParam("asOf",dtStr)
                .when()
                .get("/finalPrice")
                .then()
                .statusCode(Response.Status.OK.getStatusCode())
                .extract().as(OrderPriceResponse.class);
        assertThat("testOrderFinalPrice result failed", result, is(notNullValue()));
        assertThat("testOrderFinalPrice result.getAsOf failed", result.getAsOf(), is(notNullValue()));
        assertThat("testOrderFinalPrice result.getItems failed", result.getItems(), is(notNullValue()));
    }

    @Test
    public void testOrderFinalPrice_EmptyItems() {
        List<Integer> items = Arrays.asList();
        DateTime dateTime = new DateTime(DateTime.parse("2017-07-01T23:59:59Z"));

        DateTimeFormatter fmt = DateTimeFormat.forPattern("yyyy-MM-dd'T'HH:mm:ss'Z'");
        String dtStr = fmt.print(dateTime);

        OrderPriceResponse result = given()
                .queryParam("items", items)
                .queryParam("asOf",dtStr)
                .when()
                .get("/finalPrice")
                .then()
                .statusCode(Response.Status.OK.getStatusCode())
                .extract().as(OrderPriceResponse.class);
        assertThat("testOrderFinalPrice_EmptyItems result failed", result, is(notNullValue()));
        assertThat("testOrderFinalPrice_EmptyItems result.getAsOf failed", result.getAsOf(), is(notNullValue()));
        assertThat("testOrderFinalPrice_EmptyItems result.getItems failed", result.getItems(), is(notNullValue()));
        assertThat("testOrderFinalPrice_EmptyItems result.totalPrice failed", result.getTotalPrice(), is(new BigDecimal(0)));
    }

    @Test
    public void testOrderFinalPrice_EmptyAsOf() {
        List<Integer> items = Arrays.asList(101);

        JsonPath result = given()
                .queryParam("items", items)
                .queryParam("asOf","")
                .when()
                .get("/finalPrice")
                .then()
                .statusCode(Response.Status.BAD_REQUEST.getStatusCode())
                .extract().jsonPath();
        assertThat("testOrderFinalPrice_EmptyAsOf result failed", result, is(notNullValue()));
        Map<String,Map<String, String>> map = (Map<String, Map<String, String>>)result.get();
        assertThat("testOrderFinalPrice_EmptyAsOf result message failed", result.get("message"), is(containsString("OrderPrice params are required")));
    }
}