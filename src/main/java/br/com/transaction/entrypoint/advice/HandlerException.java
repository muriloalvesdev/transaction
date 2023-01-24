package br.com.transaction.entrypoint.advice;

import br.com.transaction.core.exception.AccountNotFoundException;
import br.com.transaction.core.exception.AlreadyDocumentNumberException;
import br.com.transaction.core.exception.InvalidAmountException;
import br.com.transaction.dataprovider.database.exception.InvalidOperationTypeException;
import br.com.transaction.entrypoint.dto.ResponseError;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class HandlerException {

    private static final int BAD_REQUEST = 400;
    private static final int CONFLICT = 409;
    private static final int NOT_FOUND = 404;

    @ExceptionHandler(InvalidAmountException.class)
    public ResponseEntity<ResponseError> handleInvalidAmountException(
        final InvalidAmountException exception) {
        final var responseError = build(exception.getMessage(), BAD_REQUEST);
        return ResponseEntity.badRequest().body(responseError);
    }

    @ExceptionHandler(AccountNotFoundException.class)
    public ResponseEntity<ResponseError> handleAccountNotFoundException(
        final AccountNotFoundException exception) {
        final var responseError = build(exception.getMessage(), NOT_FOUND);
        return ResponseEntity.status(NOT_FOUND).body(responseError);
    }

    @ExceptionHandler(InvalidOperationTypeException.class)
    public ResponseEntity<ResponseError> handleInvalidOperationTypeException(
        final InvalidOperationTypeException exception) {
        final var responseError = build(exception.getMessage(), BAD_REQUEST);
        return ResponseEntity.status(BAD_REQUEST).body(responseError);
    }

    @ExceptionHandler(AlreadyDocumentNumberException.class)
    @ResponseStatus(value = HttpStatus.CONFLICT)
    public ResponseEntity<ResponseError> handleAlreadyDocumentNumberException(
        final AlreadyDocumentNumberException exception) {
        final var responseError = build(exception.getMessage(), CONFLICT);
        return ResponseEntity.status(CONFLICT).body(responseError);
    }


    private ResponseError build(final String message, final int code) {
        return new ResponseError(message, code);
    }

}
