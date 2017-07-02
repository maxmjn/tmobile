package com.coding.tmobile.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Created by maxmjn20 on 7/2/17.
 * Custom exception for OrderPrice request
 */
public class InvalidOrderPriceException extends Exception {
    String message;

    public InvalidOrderPriceException(String message){
        super(message);
    }
}
