package com.bolton.globalhotelhub.exception;

public class GlobalHotelHubServiceException extends RuntimeException {

    private final int status;
    private final String message;

    public GlobalHotelHubServiceException(int status, String message){
        super(message);
        this.status = status;
        this.message = message;
    }

    int getStatus() {
        return status;
    }

    @Override
    public String getMessage() {
        return message;
    }
}
