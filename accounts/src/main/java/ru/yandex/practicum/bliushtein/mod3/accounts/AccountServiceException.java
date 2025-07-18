package ru.yandex.practicum.bliushtein.mod3.accounts;

public class AccountServiceException extends Exception {

    private static final String CANT_DELETE_BANK_USER_USER_HAVE_SOME_ACCOUNTS_ERROR_MESSAGE
            = "Can't delete bank user with name = '%s'. User have some accounts";
    private static final String ACCOUNT_NOT_FOUND_ERROR_MESSAGE
            = "Account for user = '%s' and currency = '%s' is not found";

    private static final String ACCOUNT_ALREADY_EXISTS_ERROR_MESSAGE
            = "Account for user = '%s' and currency = '%s' is already exists";
    private static final String BALANCE_IS_NOT_0_ERROR_MESSAGE
            = "Can't delete account for user = '%s' and currency = '%s'. Balance is not 0";

    private static final String NOT_ENOUGH_MONEY_ERROR_MESSAGE
            = "Not enough money for user = '%s' and currency= '%s' for change = '%d'";

    private static final String ACCOUNTS_SHOULD_BE_DIFFERENT_ERROR_MESSAGE
            = "Can't transfer money. Accounts should be different. user = '%s' and currency= '%s'";

    public AccountServiceException(String message) {
        super(message);
    }

    public static AccountServiceException accountsExist(String user) {
        return new AccountServiceException(CANT_DELETE_BANK_USER_USER_HAVE_SOME_ACCOUNTS_ERROR_MESSAGE.formatted(user));
    }

    public static AccountServiceException accountNotFound(String user, String currency) {
        return new AccountServiceException(ACCOUNT_NOT_FOUND_ERROR_MESSAGE.formatted(user, currency));
    }

    public static AccountServiceException notEnoughMoney(String user, String currency, int change) {
        return new AccountServiceException(NOT_ENOUGH_MONEY_ERROR_MESSAGE.formatted(user, currency, change));
    }

    public static AccountServiceException accountAlreadyExists(String user, String currency) {
        return new AccountServiceException(ACCOUNT_ALREADY_EXISTS_ERROR_MESSAGE.formatted(user, currency));
    }

    public static AccountServiceException cantDeleteAccountWithNonZeroBalance(String user, String currency) {
        return new AccountServiceException(BALANCE_IS_NOT_0_ERROR_MESSAGE.formatted(user, currency));
    }

    public static AccountServiceException accountsShouldBeDifferent(String user, String currency) {
        return new AccountServiceException(ACCOUNTS_SHOULD_BE_DIFFERENT_ERROR_MESSAGE.formatted(user, currency));
    }
}
