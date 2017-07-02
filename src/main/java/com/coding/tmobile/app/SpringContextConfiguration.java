package com.coding.tmobile.app;

import com.coding.tmobile.service.FileLoader;
import com.coding.tmobile.service.JsonFileParser;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import java.io.InputStream;

/**
 * Created by maxmjn20 on 6/30/17.
 */
@Configuration
@ComponentScan({"com.coding.tmobile"})
public class SpringContextConfiguration {

    /*
        To keep it simple loading static file.
        Can be improved to be PeriodicCache.
        Can be improved to read filename as program param
     */

    static {
        InputStream inputStream = FileLoader.loadFile("ProductDetails.json");
        JsonFileParser.parseLoadProducts(inputStream);
    }
}