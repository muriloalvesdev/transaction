package br.com.transaction.core.usecase.operation;

import java.math.BigDecimal;

public abstract class OperationRule {

    /**
     * Verifica se a operação é inválida com base no valor do amount
     * @param amount valor a ser verificado
     * @return true se a operação for inválida, false caso contrário
     */
    public boolean isOperationInvalid(final BigDecimal amount) {
        return isAmountPositiveAndRuleRequiresNegative(amount) ||
            isAmountNegativeAndRuleRequiresPositive(amount) ||
            isZero(amount);
    }

    /**
     * Verifica se o valor é positivo e a regra requerida deve ser negativa, ou seja,
     * se a regra está invertida.
     * @param amount
     * @return true se a regra estiver invertida, false caso contrário
     */
    private boolean isAmountPositiveAndRuleRequiresNegative(final BigDecimal amount) {
        return isPositive(amount) && mustBeNegative();
    }

    /**
     * Verifica se o valor é negativo e a regra requerida deve ser positiva, ou seja,
     * se a regra está invertida.
     * @param amount
     * @return true se a regra estiver invertida, false caso contrário
     */
    private boolean isAmountNegativeAndRuleRequiresPositive(final BigDecimal amount) {
        return isNegative(amount) && mustBePositive();
    }

    /**
     * Verifica se o valor é igual a zero
     * @param amount valor a ser verificado
     * @return true se o valor for igual a zero, false caso contrário
     */
    private boolean isZero(final BigDecimal amount) {
        return amount.equals(BigDecimal.ZERO);
    }

    /**
     * Verifica se o valor é negativo
     * @param amount valor a ser verificado
     * @return true se o valor for negativo, false caso contrário
     */
    private boolean isNegative(final BigDecimal amount) {
        return amount.compareTo(BigDecimal.ZERO) < 0;
    }

    /**
     * Verifica se o valor é positivo
     * @param amount valor a ser verificado
     * @return true se o valor for positivo, false caso contrário
     */
    public boolean isPositive(final BigDecimal amount) {
        return amount.compareTo(BigDecimal.ZERO) > 0;
    }

    /**
     * Verifica se a operação deve ser positiva
     * @return true se a operação deve ser positiva, false caso contrário
     */
    abstract boolean mustBePositive();

    /**
     * Verifica se a operação deve ser negativa
     * @return true se a operação deve ser negativa, false caso contrário
     */
    abstract boolean mustBeNegative();

}
