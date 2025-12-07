//package com.toy.project.handler;
//
//import java.util.HashMap;
//import java.util.Map;
//
//import org.springframework.dao.DataIntegrityViolationException;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.ExceptionHandler;
//import org.springframework.web.bind.annotation.RestControllerAdvice;
//
//@RestControllerAdvice
//public class GlobalExceptionHandler {
//
//    @ExceptionHandler(DataIntegrityViolationException.class)
//    public ResponseEntity<Map<String, String>> handleDataIntegrityViolation(DataIntegrityViolationException ex) {
//        Map<String, String> error = new HashMap<>();
//        error.put("error", "데이터 중복 오류");
//        error.put("message", ex.getRootCause().getMessage());
//        return ResponseEntity.status(HttpStatus.CONFLICT).body(error);
//    }
//}
