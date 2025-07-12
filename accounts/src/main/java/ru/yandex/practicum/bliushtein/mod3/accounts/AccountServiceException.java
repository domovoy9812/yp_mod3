package ru.yandex.practicum.bliushtein.mod3.accounts;

public class AccountServiceException extends Exception {

    public static final String CANT_DELETE_BANK_USER_USER_HAVE_SOME_ACCOUNTS_ERROR_MESSAGE
            = "Can't delete bank user with name = '%s'. User have some accounts";

    public AccountServiceException(String message) {
        super(message);
    }

    public static AccountServiceException accountsExist(String user) {
        return new AccountServiceException(CANT_DELETE_BANK_USER_USER_HAVE_SOME_ACCOUNTS_ERROR_MESSAGE.formatted(user));
    }
}
