package br.com.transaction.core.exception;

import java.util.UUID;

public class AccountNotFoundException extends RuntimeException {

    public AccountNotFoundException(final String accountId) {
        super(String.format("Account not found with id=%s", accountId));
    }

}
