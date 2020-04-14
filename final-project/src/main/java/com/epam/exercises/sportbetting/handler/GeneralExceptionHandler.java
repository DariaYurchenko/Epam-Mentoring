package com.epam.exercises.sportbetting.handler;

import com.epam.exercises.sportbetting.exceptions.*;
import com.epam.exercises.sportbetting.response.error.GeneralErrorResponse;
import com.epam.exercises.sportbetting.response.error.ValidationErrorResponse;
import com.epam.exercises.sportbetting.response.error.ValidationErrorResponse.Violation;
import io.jsonwebtoken.ExpiredJwtException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.NoHandlerFoundException;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import java.net.ConnectException;
import java.time.LocalDateTime;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.NoSuchElementException;

@RestControllerAdvice
@EnableWebMvc
@Slf4j
public class GeneralExceptionHandler {
    private ResourceBundleMessageSource messageSource;

    @Autowired
    public GeneralExceptionHandler(ResourceBundleMessageSource messageSource) {
        this.messageSource = messageSource;
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ValidationErrorResponse> handleConstraintViolationException(MethodArgumentNotValidException ex) {
        log.warn(ex.getClass().getSimpleName() + " was thrown.");
        ValidationErrorResponse response = new ValidationErrorResponse(new ArrayList<>());

        ex.getBindingResult().getGlobalErrors()
                .forEach(error -> response.getViolations().add(new Violation(buildI18nMessage(error.getDefaultMessage()))));

        ex.getBindingResult().getFieldErrors()
                .forEach(error -> response.getViolations().add(new Violation(error.getField(), buildI18nMessage(error.getDefaultMessage()))));

        return ResponseEntity.badRequest().body(response);
    }

    @ExceptionHandler(MailsSenderException.class)
    public ResponseEntity<GeneralErrorResponse> handleMailsSenderException() {
        log.warn(MailsSenderException.class.getSimpleName() + " was thrown.");
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(buildErrorResponse(buildI18nMessage("messaging.exception"), HttpStatus.INTERNAL_SERVER_ERROR.value()));
    }

    @ExceptionHandler(NoHandlerFoundException.class)
    public ResponseEntity<GeneralErrorResponse> handleNoHandlerFoundException() {
        log.warn(NoHandlerFoundException.class.getSimpleName() + " was thrown.");
        return ResponseEntity.badRequest().body(buildErrorResponse(buildI18nMessage("page.not.found"), HttpStatus.NOT_FOUND.value()));
    }

    @ExceptionHandler(InvalidTokenException.class)
    public ResponseEntity<GeneralErrorResponse> handleInvalidTokenException() {
        log.warn(InvalidTokenException.class.getSimpleName() + " was thrown.");
        return ResponseEntity.badRequest().body(buildErrorResponse(buildI18nMessage("invalid.token"), HttpStatus.BAD_REQUEST.value()));
    }

    @ExceptionHandler(ConnectException.class)
    public ResponseEntity<GeneralErrorResponse> handleConnectException() {
        log.warn(ConnectException.class.getSimpleName() + " was thrown.");
        return ResponseEntity.badRequest().body(buildErrorResponse(buildI18nMessage("token.expired.exception"), HttpStatus.INTERNAL_SERVER_ERROR.value()));
    }

    @ExceptionHandler(ExpiredJwtException.class)
    public ResponseEntity<GeneralErrorResponse> handleExpiredJwtException() {
        log.warn(ExpiredJwtException.class.getSimpleName() + " was thrown.");
        return ResponseEntity.badRequest().body(buildErrorResponse(buildI18nMessage("connection.exception"), HttpStatus.BAD_REQUEST.value()));
    }

    @ExceptionHandler(TokenExpiredException.class)
    public ResponseEntity<GeneralErrorResponse> handleTokenExpiredException() {
        log.warn(TokenExpiredException.class.getSimpleName() + " was thrown.");
        return ResponseEntity.badRequest().body(buildErrorResponse(buildI18nMessage("expired.token"), HttpStatus.BAD_REQUEST.value()));
    }

    @ExceptionHandler(UserExistsException.class)
    public ResponseEntity<GeneralErrorResponse> handleUserExistsException() {
        log.warn(UserExistsException.class.getSimpleName() + " was thrown.");
        return ResponseEntity.badRequest().body(buildErrorResponse(buildI18nMessage("registration.user.exists"), HttpStatus.BAD_REQUEST.value()));
    }

    @ExceptionHandler(WagerProcessedException.class)
    public ResponseEntity<GeneralErrorResponse> handleWagerProcessedException() {
        log.warn(WagerProcessedException.class.getSimpleName() + " was thrown.");
        return ResponseEntity.badRequest().body(buildErrorResponse(buildI18nMessage("wager.processed.exception"), HttpStatus.BAD_REQUEST.value()));
    }

    @ExceptionHandler(NoSuchElementException.class)
    public ResponseEntity<GeneralErrorResponse> handleNoSuchElementException(NoSuchElementException ex) {
        log.warn(NoSuchElementException.class.getSimpleName() + " was thrown.");
        return ResponseEntity.badRequest().body(buildErrorResponse(buildI18nMessage(ex.getMessage()), HttpStatus.BAD_REQUEST.value()));
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<GeneralErrorResponse> handleIllegalArgumentException(IllegalArgumentException ex) {
        log.warn(IllegalArgumentException.class.getSimpleName() + " was thrown.");
        return ResponseEntity.badRequest().body(buildErrorResponse(buildI18nMessage(ex.getMessage()), HttpStatus.BAD_REQUEST.value()));
    }

    @ExceptionHandler(DateTimeParseException.class)
    public ResponseEntity<GeneralErrorResponse> handleDateTimeParseException(DateTimeParseException ex) {
        log.warn(DateTimeParseException .class.getSimpleName() + " was thrown.");
        return ResponseEntity.badRequest().body(buildErrorResponse(buildI18nMessage("cant.parse.error") + " " + ex.getParsedString(), HttpStatus.BAD_REQUEST.value()));
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<GeneralErrorResponse> handleHttpMessageNotReadableException(HttpMessageNotReadableException ex) {
        log.warn(HttpMessageNotReadableException.class.getSimpleName() + " was thrown.");
        return ResponseEntity.badRequest().body(buildErrorResponse(buildI18nMessage("invalid.json.body.exception"), HttpStatus.BAD_REQUEST.value()));
    }

    private String buildI18nMessage(String code) {
        return messageSource.getMessage(code, null, LocaleContextHolder.getLocale());
    }

    private GeneralErrorResponse buildErrorResponse(String message, int status) {
        return GeneralErrorResponse.builder()
                .message(message)
                .status(status)
                .timeStamp(LocalDateTime.now().toString())
                .build();
    }


}
