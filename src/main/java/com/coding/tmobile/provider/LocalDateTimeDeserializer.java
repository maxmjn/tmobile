package com.coding.tmobile.provider;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.ObjectCodec;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.node.TextNode;
import org.joda.time.LocalDateTime;
import org.joda.time.format.DateTimeFormat;

import javax.ws.rs.ext.Provider;
import java.io.IOException;
import java.util.Locale;

/**
 * Created by maxmjn20 on 7/1/17.
 */
@Provider
public class LocalDateTimeDeserializer extends JsonDeserializer<LocalDateTime> {

    @Override
    public LocalDateTime deserialize(JsonParser jp,
                                     DeserializationContext dc) throws IOException, JsonProcessingException {
        ObjectCodec codec = jp.getCodec();
        TextNode node = (TextNode)codec.readTree(jp);
        String dateString = node.textValue();
        LocalDateTime dateTime =
                DateTimeFormat.forPattern("yyyy-MM-dd'T'HH:mm:ss'Z'")
                        .withLocale(Locale.ENGLISH)
                        .parseLocalDateTime(dateString);
        return dateTime;
    }
}

