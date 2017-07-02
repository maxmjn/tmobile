package com.coding.tmobile.service;

import com.coding.tmobile.model.ProductInfo;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.runners.MockitoJUnitRunner;

import java.io.InputStream;
import java.util.Map;

import static org.junit.Assert.assertTrue;

/**
 * Created by maxmjn20 on 7/2/17.
 */
@RunWith(MockitoJUnitRunner.class)
public class JsonFileParserTest {

    @InjectMocks
    JsonFileParser jsonFileParser;

    @Test
    public void parseLoadProducts() throws Exception {

        ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
        InputStream inputStream = classLoader.getResourceAsStream("ProductDetails.json");
        JsonFileParser.parseLoadProducts(inputStream);
        Map<Integer, ProductInfo> result = jsonFileParser.getProductCache();
        assertTrue("parseLoadProducts failed result", null!=result);
        assertTrue("parseLoadProducts failed result count", result.size() > 0);
    }

    @Test(expected = RuntimeException.class)
    public void parseLoadProducts_fail() throws Exception {

        ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
        InputStream inputStream = classLoader.getResourceAsStream("XYZ");
        JsonFileParser.parseLoadProducts(inputStream);
    }

    @Test(expected = RuntimeException.class)
    public void parseLoadProducts_fail2() throws Exception {

        ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
        InputStream inputStream = classLoader.getResourceAsStream("");
        JsonFileParser.parseLoadProducts(inputStream);
    }
}