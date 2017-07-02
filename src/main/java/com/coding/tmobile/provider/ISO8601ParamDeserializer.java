package com.coding.tmobile.provider;

import org.joda.time.LocalDateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import javax.ws.rs.WebApplicationException;
import javax.ws.rs.ext.Provider;

/**
 * Created by maxmjn20 on 7/2/17.
 */
@Provider
public class ISO8601ParamDeserializer {

    private DateTimeFormatter df = DateTimeFormat.forPattern("yyyy-MM-dd'T'HH:mm:ss'Z'");
    private LocalDateTime date;

    public ISO8601ParamDeserializer(String dateTimeStr ) throws WebApplicationException {
        date = df.parseLocalDateTime(dateTimeStr);
    }

    public LocalDateTime getDate() {
        return date;
    }

    @Override
    public String toString() {
        if ( date != null ) {
            return date.toString();
        } else {
            return "";
        }
    }
}
