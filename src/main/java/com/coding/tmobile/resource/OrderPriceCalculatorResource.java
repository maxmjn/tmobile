package com.coding.tmobile.resource;

import com.codahale.metrics.annotation.Timed;
import com.coding.tmobile.model.InvalidOrderPriceException;
import com.coding.tmobile.provider.ISO8601ParamDeserializer;
import com.coding.tmobile.response.OrderPriceResponse;
import com.coding.tmobile.service.OrderPriceCalculatorService;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

/**
 * Created by maxmjn20 on 6/30/17.
 */
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@Path("/orders")
@NoArgsConstructor
@Component
public class OrderPriceCalculatorResource {

    @Autowired
    protected OrderPriceCalculatorService orderPriceCalculatorService;

    @GET
    @Timed
    @Path("/finalPrice")
    public Response calculateFinalPrice(
            @QueryParam("items") List<Integer> listItems,
            @QueryParam("asOf") ISO8601ParamDeserializer asOf
    ) {
        OrderPriceResponse orderPriceResponse;
        try {
            orderPriceResponse = orderPriceCalculatorService.calculateFinalPrice(listItems, asOf);
        } catch (InvalidOrderPriceException e) {
            return Response.status(Response.Status.BAD_REQUEST).type(MediaType.APPLICATION_JSON_TYPE).entity(e).build();

        } catch (Exception e) {
            e.printStackTrace();
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).type(MediaType.APPLICATION_JSON_TYPE).entity(e).build();
        }

        return Response.status(Response.Status.OK).entity(orderPriceResponse).build();
    }
}
