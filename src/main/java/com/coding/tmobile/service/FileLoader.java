package com.coding.tmobile.service;

import lombok.Setter;
import org.springframework.stereotype.Service;

import java.io.InputStream;

/**
 * Created by maxmjn20 on 6/30/17.
 *
 * Generic file loader to find, load file as inputstream
 */
@Service
@Setter
public class FileLoader {

    /**
     * synchronized to make thread-safe
     */
    public static synchronized InputStream loadFile(String fileName) {

        ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
        InputStream input = null;
        try {
            input = classLoader.getResourceAsStream(fileName);
            if(input == null){
                throw new
                        RuntimeException("File not found");
            }
        } catch (Exception e) {
            throw new
                    RuntimeException("File not found");
        } finally {

        }

        return input;
    }

}
