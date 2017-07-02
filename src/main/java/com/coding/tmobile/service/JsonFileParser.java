package com.coding.tmobile.service;

import com.coding.tmobile.model.ProductInfo;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.joda.JodaModule;
import lombok.Setter;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.stereotype.Service;

import java.io.*;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by maxmjn20 on 6/30/17.
 *
 * Parse inputstream into POJO
 */
@Service
@Setter
public class JsonFileParser {

    private static Map<Integer, ProductInfo> productCache = new HashMap<>();

    public Map<Integer, ProductInfo> getProductCache(){
        return productCache;
    }

    public static void parseLoadProducts(InputStream inputStream) {

        JSONParser parser = new JSONParser();

        try {

            Reader targetReader = new InputStreamReader(inputStream);
            Object obj = parser.parse(targetReader);

            //org.json to parse array input
            JSONArray jsonArray = (JSONArray) obj;

            for(Object object: jsonArray){
                JSONObject jsonObject = (JSONObject) object;

                //JACKSON parser for JSONObject to POJO
                ObjectMapper mapper = new ObjectMapper();
                mapper.findAndRegisterModules(); //dependency jackson-datatype-jsr310 json date string serialization

                ProductInfo productDetail = mapper.readValue(jsonObject.toJSONString(), ProductInfo.class);

                productCache.put(productDetail.getItemId(), productDetail);
            }
        } catch (FileNotFoundException e){
            throw new
                    RuntimeException("Unable to load PRODUCTS", e);
        } catch (ParseException e){
            throw new
                    RuntimeException("Unable to load PRODUCTS", e);
        } catch (IOException e){
            throw new
                    RuntimeException("Unable to load PRODUCTS", e);
        } catch (Exception e){
            throw new
                    RuntimeException("Unable to load PRODUCTS", e);
        }
    }
}
