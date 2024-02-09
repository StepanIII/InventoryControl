package com.example.inventory.control.controllers;

import com.example.inventory.control.api.ExceptionResponse;
import com.example.inventory.control.api.StatusResponse;
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

@ControllerAdvice
public class RestExceptionHandler extends ResponseEntityExceptionHandler {

    private static final Logger LOGGER = Logger.getLogger(RestExceptionHandler.class.getName());

    private static final Pattern PATTERN_ARG_NOT_VALID = Pattern.compile("default message \\[(.*?)]");

    @Override
    protected ResponseEntity<Object> handleHttpMessageNotReadable(HttpMessageNotReadableException ex,
                                                                  HttpHeaders headers,
                                                                  HttpStatusCode status,
                                                                  WebRequest request) {
        LOGGER.warning(ex.getMessage());
        return ResponseEntity.badRequest().body(
                new ExceptionResponse(StatusResponse.MALFORMED_JSON_REQUEST, ex.getMessage()));
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
                                                                  HttpHeaders headers,
                                                                  HttpStatusCode status,
                                                                  WebRequest request) {
        // Переделать получение ошибок
        Matcher matcher = PATTERN_ARG_NOT_VALID.matcher(ex.getMessage());
        StringBuilder responseDescriptionBuilder = new StringBuilder();
        int i = 0;
        while (matcher.find()) {
            if (i % 2 != 0) {
                responseDescriptionBuilder
                        .append(matcher.group(1))
                        .append("; ");
            }
            i++;
        }
        if (responseDescriptionBuilder.isEmpty()) {
            responseDescriptionBuilder.append(ex.getMessage());
        }
        String responseDescription = responseDescriptionBuilder.toString().trim();
        LOGGER.warning(ex.getMessage());
        return ResponseEntity.badRequest().body(
                new ExceptionResponse(StatusResponse.METHOD_ARGUMENT_NOT_VALID, responseDescription));
    }

    //    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
//    protected ResponseEntity<Object> handleMethodArgumentTypeMismatch(MethodArgumentTypeMismatchException ex) {
//        ExceptionResponse exceptionResponse = new ExceptionResponse();
//        exceptionResponse.setMessage(String.format("The parameter '%s' of value '%s' could not be converted to type '%s'",
//                ex.getName(), ex.getValue(), ex.getRequiredType().getSimpleName()));
//        exceptionResponse.setDescription(ex.getErrorCode());
//        return ResponseEntity.badRequest().body(exceptionResponse);
//    }

    @ExceptionHandler(Exception.class)
    protected ResponseEntity<Object> handleAllExceptions(Exception ex) {
        String errorMessage = ex.getMessage();
        ExceptionResponse response;
        if (errorMessage.contains("Cannot delete or update a parent row")) {
            response = new ExceptionResponse(StatusResponse.NOT_DELETE_PARENT, "Невозможно удалить запись, так как она используется в другом месте.");
        } else {
            response = new ExceptionResponse(StatusResponse.INTERNAL_SERVER_ERROR, ex.getMessage());
        }

        LOGGER.warning(ex.getMessage());
        return ResponseEntity.internalServerError().body(response);
    }

//            while (matcher.find()) {
//        responseDescriptionBuilder.append(matcher.group(1));
//        if (i % 2 == 0) {
//            responseDescriptionBuilder.append(" - ");
//        } else {
//            responseDescriptionBuilder.append("; ");
//        }
//        i++;
//    }
}
