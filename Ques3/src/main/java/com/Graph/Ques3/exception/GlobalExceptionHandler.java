package com.Graph.Ques3.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(GraphException.NodeNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public String handleNodeNotFoundException(GraphException.NodeNotFoundException ex) {
        return ex.getMessage();
    }

    @ExceptionHandler(GraphException.InvalidNodeException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public String handleInvalidNodeException(GraphException.InvalidNodeException ex) {
        return ex.getMessage();
    }

    @ExceptionHandler(GraphException.RelationshipNotFoundException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public String handleRelationshipNotFoundException(GraphException.RelationshipNotFoundException ex) {
        return ex.getMessage();
    }

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public String handleGenericException(Exception ex) {
        return "An unexpected error occurred: " + ex.getMessage();
    }

    @ExceptionHandler(org.springframework.web.servlet.resource.NoResourceFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public String handleNoResourceFoundException(org.springframework.web.servlet.resource.NoResourceFoundException ex) {
        return "No static resource found";
    }
}
