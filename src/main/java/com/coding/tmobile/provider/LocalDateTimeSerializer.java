package com.coding.tmobile.provider;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import org.joda.time.LocalDateTime;
import org.joda.time.format.DateTimeFormat;

import javax.ws.rs.ext.Provider;
import java.io.IOException;
import java.util.Locale;

/**
 * Created by maxmjn20 on 7/1/17.
 */
@Provider
public class LocalDateTimeSerializer extends JsonSerializer<LocalDateTime> {

    @Override
    public void serialize(LocalDateTime dateTime, JsonGenerator jg,
                          SerializerProvider sp) throws IOException, JsonProcessingException {
        jg.writeString(DateTimeFormat.forPattern("yyyy-MM-dd'T'HH:mm:ss").withLocale(Locale.ENGLISH).print(dateTime));
    }
}
