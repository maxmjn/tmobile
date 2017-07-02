package integration.common;

import com.coding.tmobile.app.SpringContextConfiguration;
import com.coding.tmobile.resource.OrderPriceCalculatorResource;
import com.jayway.restassured.RestAssured;
import org.glassfish.grizzly.http.server.HttpServer;
import org.glassfish.jersey.grizzly2.httpserver.GrizzlyHttpServerFactory;
import org.glassfish.jersey.server.ResourceConfig;
import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.io.IOException;
import java.net.URI;

/**
 * Created by maxmjn20 on 7/1/17.
 */
public class StartServer {

    public static final String BASE_URI = "http://localhost:9000";
    public static final int PORT = 9000;
    private HttpServer server;

    @Before
    public void setUp() throws Exception {
        final ResourceConfig rc = new ResourceConfig(OrderPriceCalculatorResource.class);
        rc.property("contextConfig",
                new AnnotationConfigApplicationContext(SpringContextConfiguration.class));
        server = GrizzlyHttpServerFactory.createHttpServer(URI.create(BASE_URI), rc);
    }

    @After
    public void tearDown() throws Exception {
        server.shutdown();
    }

    @BeforeClass
    public static void setup() throws IOException {


        String port = System.getProperty("server.port");
        if (port == null) {
            RestAssured.port = PORT;
        }
        else{
            RestAssured.port = Integer.valueOf(port);
        }


        String basePath = System.getProperty("server.base");
        if(basePath==null){
            basePath = "/orders/";
        }
        RestAssured.basePath = basePath;

        String baseHost = System.getProperty("server.host");
        if(baseHost==null){
            baseHost = "http://localhost";
        }
        RestAssured.baseURI = baseHost;

    }
}
