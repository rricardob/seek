package org.ricardo.examen.infraestructure.input.rest.handler;

import lombok.extern.slf4j.Slf4j;

import org.ricardo.examen.infraestructure.input.rest.handler.data.ExceptionResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Slf4j
@ControllerAdvice
public class ClientExceptionHandler {

    @ExceptionHandler(Exception.class)
    public final ResponseEntity<Object> handleAllExceptions(Exception ex, WebRequest request) {
        ExceptionResponse exceptionResponse = ExceptionResponse.builder()
                .message(ex.getMessage())
                .build();
        log.error(ex.getMessage(), ex);
        return new ResponseEntity<>(exceptionResponse, HttpStatus.INTERNAL_SERVER_ERROR);
    }


    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Object> validationHandler(MethodArgumentNotValidException exception) {
        HashMap<String, Object> resObj = new HashMap<>();
        String errorMsg = "validation is failed!";
        if (exception.getErrorCount() > 0) {
            List<String> errorDetails = new ArrayList<>();
            for (ObjectError error : exception.getBindingResult().getAllErrors()) {
                errorDetails.add(error.getDefaultMessage());
            }

            if (!errorDetails.isEmpty()) errorMsg = errorDetails.get(0);
        }

        resObj.put("message", errorMsg);
        return new ResponseEntity<>(resObj, HttpStatus.BAD_REQUEST);
    }

    /*@ExceptionHandler(CustomerDuplicationViolationException.class)
    public final ResponseEntity<Object> handleUserNotFoundException(CustomerDuplicationViolationException ex, WebRequest request) {
        ExceptionResponse exceptionResponse = ExceptionResponse.builder()
                .message(ex.getMessage())
                .build();
        log.error(ex.getMessage(), ex);
        return new ResponseEntity<>(exceptionResponse, HttpStatus.CONFLICT);
    }*/

}

