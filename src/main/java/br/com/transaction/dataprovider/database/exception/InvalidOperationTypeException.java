package br.com.transaction.dataprovider.database.exception;

public class InvalidOperationTypeException extends RuntimeException {

    public InvalidOperationTypeException(final String type) {
        super(String.format("Operation Type is invalid, this type=%s not is mapped!", type));
    }

}
