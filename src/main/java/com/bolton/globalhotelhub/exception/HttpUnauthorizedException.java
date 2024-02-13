package com.bolton.globalhotelhub.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.OK)
public class HttpUnauthorizedException extends RuntimeException {
}
