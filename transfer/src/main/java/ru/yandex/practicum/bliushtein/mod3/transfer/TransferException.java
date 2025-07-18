package ru.yandex.practicum.bliushtein.mod3.transfer;

public class TransferException extends RuntimeException {
    public static final String SOURCE_USER_IS_NOT_FOUND_ERROR_MESSAGE = "Source user is not found. user='%s'";
    public static final String BLOCKER_VALIDATION_FAILED_ERROR_MESSAGE = "Blocker validation failed. Error message='%s'";

    public TransferException(String message) {
        super(message);
    }

    public static TransferException sourceUserNotFound(String source) {
        return new TransferException(SOURCE_USER_IS_NOT_FOUND_ERROR_MESSAGE.formatted(source));
    }

    public static TransferException blockerValidationFailed(String message) {
        return new TransferException(BLOCKER_VALIDATION_FAILED_ERROR_MESSAGE.formatted(message));
    }
}
