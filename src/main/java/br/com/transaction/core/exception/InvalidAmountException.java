package br.com.transaction.core.exception;

import br.com.transaction.dataprovider.database.entity.OperationsType;

public class InvalidAmountException extends RuntimeException {

    public InvalidAmountException(final OperationsType type) {
        super(String.format("operation_type=%s must have a value=%s", type, type.getSignal()));
    }

}
