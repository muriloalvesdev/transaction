package br.com.transaction.core.exception;

public class AlreadyDocumentNumberException extends RuntimeException {

    public AlreadyDocumentNumberException(String documentNumber) {
        super(String.format("Already exists account with document_number=%s", documentNumber));
    }

}
