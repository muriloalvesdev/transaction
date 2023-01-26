package br.com.transaction.core.usecase.operation;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static br.com.transaction.ConstantsTests.AMOUNT_NEGATIVE;
import static br.com.transaction.ConstantsTests.AMOUNT_POSITIVE;
import static br.com.transaction.ConstantsTests.AMOUNT_ZERO;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class OperationRuleTest {

    @Test
    @DisplayName("Mock - Operação inválida com valor negativo e regra positiva")
    void testOperationInvalidWithNegativeAmount() {
        //GIVEN
        final var operationRule = new OperationRule() {

            @Override
            boolean mustBePositive() {
                return true;
            }

            @Override
            boolean mustBeNegative() {
                return false;
            }
        };

        //WHEN
        final var result = operationRule.isOperationInvalid(AMOUNT_NEGATIVE);

        //THEN
        assertTrue(result);
    }

    @Test
    @DisplayName("Mock - Operação inválida com valor positivo e regra negativa")
    void testOperationInvalidWithPositiveAmountAndNegativeRule() {
        //WHEN
        final var operationRule = new OperationRule() {

            @Override
            boolean mustBePositive() {
                return false;
            }

            @Override
            boolean mustBeNegative() {
                return true;
            }
        };

        //WHEN
        final var result = operationRule.isOperationInvalid(AMOUNT_POSITIVE);

        //THEN
        assertTrue(result);
    }

    @Test
    @DisplayName("Mock - Operação inválida com valor zero")
    void testOperationInvalidWithZeroAmount() {
        //GIVEN
        final var operationRule = new OperationRule() {

            @Override
            boolean mustBePositive() {
                return true;
            }

            @Override
            boolean mustBeNegative() {
                return false;
            }
        };

        //WHEN
        final var result = operationRule.isOperationInvalid(AMOUNT_ZERO);

        //THEN
        assertTrue(result);
    }

    @Test
    @DisplayName("Mock - Operação válida com valor positivo e regra positiva")
    void testOperationValidWithPositiveAmountAndPositiveRule() {
        //GIVEN
        final var operationRule = new OperationRule() {
            @Override
            boolean mustBePositive() {
                return true;
            }

            @Override
            boolean mustBeNegative() {
                return false;
            }
        };
        //WHEN
        final var result = operationRule.isOperationInvalid(AMOUNT_POSITIVE);

        //THEN
        assertFalse(result);
    }

    @Test
    @DisplayName("Mock - Operação válida com valor negativo e regra negativa")
    public void testOperationValidWithNegativeAmountAndNegativeRule() {
        //GIVEN
        final var operationRule = new OperationRule() {
            @Override
            boolean mustBePositive() {
                return false;
            }

            @Override
            boolean mustBeNegative() {
                return true;
            }
        };

        //WHEN
        final var result = operationRule.isOperationInvalid(AMOUNT_NEGATIVE);

        //THEN
        assertFalse(result);
    }

}
