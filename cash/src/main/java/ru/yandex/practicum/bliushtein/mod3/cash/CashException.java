package ru.yandex.practicum.bliushtein.mod3.cash;

public class CashException extends RuntimeException {
    public static final String INCORRECT_REQUEST_ERROR_MESSAGE
            = "Incorrect CashRequest parameters";
    public static final String BLOCKER_VALIDATION_FAILED_ERROR_MESSAGE
            = "Blocker validation failed";
    public static final String USER_NOT_FOUND_ERROR_MESSAGE = "Bank user '%s' is not found";

    public CashException(String message) {
        super(message);
    }

    public static CashException incorrectRequest() {
        return new CashException(INCORRECT_REQUEST_ERROR_MESSAGE);
    }

    public static CashException userNotFound(String user) {
        return new CashException(USER_NOT_FOUND_ERROR_MESSAGE.formatted(user));
    }

    public static CashException blockerValidationFailed() {
        return new CashException(BLOCKER_VALIDATION_FAILED_ERROR_MESSAGE);
    }
}
