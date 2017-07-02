package com.coding.tmobile.service;

import org.hamcrest.Matchers;
import org.junit.Test;

import java.io.InputStream;

import static org.junit.Assert.*;

/**
 * Created by maxmjn20 on 7/2/17.
 */
public class FileLoaderTest {
    @Test
    public void loadFile() throws Exception {
        InputStream inputStream = FileLoader.loadFile("ProductDetails.json");
        assertTrue("loadFile failed", inputStream.available() > 0);
    }

    @Test(expected = RuntimeException.class)
    public void loadFile_empty() throws Exception {
        InputStream inputStream = FileLoader.loadFile("XYZ");
    }

}