package com.PersonalProject.identity_service.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;

@Getter
public enum ErrorCode {
    UNCATEGORIZED(9999, "Uncategorized", HttpStatus.INTERNAL_SERVER_ERROR),
    INVALID_KEY(1001, "Key invalid", HttpStatus.BAD_REQUEST),
    USER_EXISTED(1002, "User name already existed", HttpStatus.BAD_REQUEST),
    USERNAME_INVALID(1003,"User name must be at least {min} characters", HttpStatus.BAD_REQUEST),
    PASSWORD_INVALID(1004, "Password must be at least {min} characters ", HttpStatus.BAD_REQUEST),
    USER_NOT_EXISTED(1005, "User not existed", HttpStatus.NOT_FOUND),
    UNAUTHENTICATED(1006, "Unauthenticated", HttpStatus.UNAUTHORIZED),
    UNAUTHORIZED(1007, "You do not have permission", HttpStatus.FORBIDDEN),
    INVALID_DOB(1008, "You must me at least {min} years old", HttpStatus.BAD_REQUEST),
    FIRST_NAME_REQUIRED(1009, "First name is required, don't let it empty", HttpStatus.BAD_REQUEST),
    LAST_NAME_REQUIRED(1010,"First name is required, don't let it empty", HttpStatus.BAD_REQUEST),
    USERNAME_INVALID_FORMAT(1011, "You username are not correct format, only text, number, dot, underline and cross ", HttpStatus.BAD_REQUEST)
    ;

    ErrorCode(int code, String message, HttpStatusCode statusCode) {
        this.code = code;
        this.message = message;
        this.statusCode = statusCode;
    }

    private String message;
    private int code;
    private HttpStatusCode statusCode;






}
