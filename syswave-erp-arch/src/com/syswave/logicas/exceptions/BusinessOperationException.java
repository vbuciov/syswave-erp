package com.syswave.logicas.exceptions;

/**
 *
 * @author Victor Manuel Bucio Vargas
 */
public class BusinessOperationException extends Exception {
   int errorCode;

    public BusinessOperationException(String mensaje, int errorCode) {
        super(mensaje);
        this.errorCode = errorCode;
    }
}
