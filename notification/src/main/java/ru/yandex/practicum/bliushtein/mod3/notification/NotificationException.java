package ru.yandex.practicum.bliushtein.mod3.notification;

public class NotificationException extends RuntimeException {

    private static final String NOTIFICATION_NOT_FOUND_ERROR_MESSAGE = "Notification with id ='%d' is not found";

    public NotificationException(String message) {
        super(message);
    }

    public NotificationException(String message, Throwable cause) {
        super(message, cause);
    }

    public static NotificationException notificationIsNotFound(Long id) {
        return new NotificationException(NOTIFICATION_NOT_FOUND_ERROR_MESSAGE.formatted(id));
    }
}
