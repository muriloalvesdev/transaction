package br.com.transaction.core.exception;

public class ZeroBalanceException extends RuntimeException{

    public ZeroBalanceException() {
        super("This balance contains unavailable credit");
    }

}
