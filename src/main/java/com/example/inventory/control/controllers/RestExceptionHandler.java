package com.example.inventory.control.controllers;

import com.example.inventory.control.api.ExceptionResponse;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

// Написать тесты
@ControllerAdvice
public class RestExceptionHandler extends ResponseEntityExceptionHandler {

    private static final Logger LOGGER = Logger.getLogger(RestExceptionHandler.class.getName());

    @Override
    protected ResponseEntity<Object> handleHttpMessageNotReadable(HttpMessageNotReadableException ex,
                                                                  HttpHeaders headers,
                                                                  HttpStatusCode status,
                                                                  WebRequest request) {
        Pattern pattern = Pattern.compile("Unrecognized field \"(.*?)\"");
        Matcher matcher = pattern.matcher(errorMessage);
        if (matcher.find()) {
            return "Не валидное поле: " + matcher.group(1);
        } else {
            return "Не удалось извлечь наименование не валидного поля";
        }

        LOGGER.warning(ex.getCause().getLocalizedMessage());
        return ResponseEntity.badRequest().body(
                new ExceptionResponse("Malformed JSON Request", ex.getMessage()));
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
                                                                  HttpHeaders headers,
                                                                  HttpStatusCode status,
                                                                  WebRequest request) {
        LOGGER.warning(ex.getMessage());
        return ResponseEntity.badRequest().body(
                new ExceptionResponse("Method Argument Not Valid", ex.getMessage()));
    }

    //    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
//    protected ResponseEntity<Object> handleMethodArgumentTypeMismatch(MethodArgumentTypeMismatchException ex) {
//        ExceptionResponse exceptionResponse = new ExceptionResponse();
//        exceptionResponse.setMessage(String.format("The parameter '%s' of value '%s' could not be converted to type '%s'",
//                ex.getName(), ex.getValue(), ex.getRequiredType().getSimpleName()));
//        exceptionResponse.setDescription(ex.getMessage());
//        return ResponseEntity.badRequest().body(exceptionResponse);
//    }

    @ExceptionHandler(Exception.class)
    protected ResponseEntity<Object> handleAllExceptions(Exception ex) {
        LOGGER.warning(ex.getMessage());
        return ResponseEntity.internalServerError().body(
                new ExceptionResponse("Internal server error", ex.getMessage()));
    }
}
