package ru.yandex.practicum.bliushtein.mod3.transfer;

public class TransferValidationException extends RuntimeException {
    public static final String SOURCE_USER_IS_NOT_FOUND_ERROR_MESSAGE = "Source user is not found. user='%s'";
    public static final String BLOCKER_VALIDATION_FAILED_ERROR_MESSAGE = "Blocker validation failed. Error message='%s'";

    public TransferValidationException(String message, Throwable cause) {
        super(message, cause);
    }

    public TransferValidationException(String message) {
        super(message);
    }

    public static TransferValidationException sourceUserNotFound(String source) {
        return new TransferValidationException(SOURCE_USER_IS_NOT_FOUND_ERROR_MESSAGE.formatted(source));
    }

    public static TransferValidationException blockerValidationFailed(String message) {
        return new TransferValidationException(BLOCKER_VALIDATION_FAILED_ERROR_MESSAGE.formatted(message));
    }
}
