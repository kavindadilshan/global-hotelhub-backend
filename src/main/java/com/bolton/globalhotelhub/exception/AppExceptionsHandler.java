package com.bolton.globalhotelhub.exception;


import com.bolton.globalhotelhub.constant.ApplicationConstant;
import com.bolton.globalhotelhub.dto.response.ErrorMessageResponseDTO;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.HashMap;
import java.util.Map;


@ControllerAdvice
public class AppExceptionsHandler extends ResponseEntityExceptionHandler {

    private static final Logger LOGGER = LogManager.getLogger(AppExceptionsHandler.class);

    @ExceptionHandler(value = {Exception.class})
    public ResponseEntity handleAnyException(Exception ex, WebRequest webRequest) {
        LOGGER.error("Function handleAnyException : " + ex.getMessage());
        return new ResponseEntity<>(new ErrorMessageResponseDTO(false, 100, ApplicationConstant.APPLICATION_ERROR_OCCURRED_MESSAGE), HttpStatus.EXPECTATION_FAILED);
    }


    @ExceptionHandler(value = {GlobalHotelHubServiceException.class})
    public ResponseEntity handleFoodOrderingServiceException(GlobalHotelHubServiceException ex, WebRequest webRequest) {
        LOGGER.error("Function handleFoodOrderingServiceException : " + ex.getMessage());
        return new ResponseEntity<>(new ErrorMessageResponseDTO(false, ex.getStatus(), ex.getMessage()), HttpStatus.OK);
    }

    @ExceptionHandler({HttpUnauthorizedException.class})
    @ResponseBody
    @ResponseStatus(value = HttpStatus.OK)
    Map<String, String> unauthorizedAccess(Exception e) {
        Map<String, String> exception = new HashMap<String, String>();
        exception.put("code", "401");
        exception.put("reason", "Invalid Token");

        return exception;
    }

}
