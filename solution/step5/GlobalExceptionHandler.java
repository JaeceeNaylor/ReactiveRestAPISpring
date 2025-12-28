package com.example.products.api.error;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import java.time.Instant;
import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(Exception.class)
    @ResponseBody
    public ResponseEntity<Map<String, Object>> handle(Exception e) {
        Map<String, Object> problemDetails = new HashMap<>();
        problemDetails.put("type", "about:blank");
        problemDetails.put("title", "Internal Server Error");
        problemDetails.put("status", HttpStatus.INTERNAL_SERVER_ERROR.value());
        problemDetails.put("detail", e.getMessage());
        problemDetails.put("instance", Instant.now().toString());

        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(problemDetails);
    }
}
