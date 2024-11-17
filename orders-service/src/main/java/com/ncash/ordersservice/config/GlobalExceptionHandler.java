package com.ncash.ordersservice.config;

import com.ncash.ordersservice.response.APIResponseUtil;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import java.util.concurrent.RejectedExecutionException;

/**
 * The type Global exception handler.
 */
@RestControllerAdvice
public class GlobalExceptionHandler {

    /**
     * Message not readable response entity.
     *
     * @param ex the ex
     * @return the response entity
     */
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<Object> messageNotReadable(HttpMessageNotReadableException ex) {
        return APIResponseUtil.getResponseWithErrorMessage(ex.getMessage());
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(RejectedExecutionException.class)
    public ResponseEntity<Object> rejectedExecution(RejectedExecutionException ex) {
        return APIResponseUtil.getResponseWithErrorMessage(ex.getMessage());
    }


    /**
     * Method argument type mismatch response entity.
     *
     * @param ex the ex
     * @return the response entity
     */
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<Object> methodArgumentTypeMismatch(MethodArgumentTypeMismatchException ex) {
        return APIResponseUtil.getResponseWithErrorMessage(ex.getMessage());
    }



    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<Object> runTimeException(RuntimeException ex) {
        return APIResponseUtil.getResponseWithErrorMessage(ex.getMessage());
    }



    /**
     * handlerOtherExceptions handles any unhandled exceptions.
     */
    @ExceptionHandler(value = {Exception.class})
    public ResponseEntity<Object> handleOtherExceptions(Exception ex) {
        return APIResponseUtil.getResponseWithErrorMessage(ex.getMessage());
    }
}
