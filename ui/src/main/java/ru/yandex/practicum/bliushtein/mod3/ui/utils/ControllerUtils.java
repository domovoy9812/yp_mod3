package ru.yandex.practicum.bliushtein.mod3.ui.utils;

import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.time.LocalDate;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Objects;
import java.util.TimeZone;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Component
public class ControllerUtils {
    public void validateUserParameters(String name, String password, String confirmPassword, String firstName,
                                       String lastName, ZonedDateTime birthdate, String email,
                                       List<String> errors) {
        if (!StringUtils.hasText(name)) {
            errors.add("Login shouldn't be empty");
        }
        validateSecondaryParameters(firstName, lastName, birthdate, email, errors);
        validatePassword(password, confirmPassword, errors);

    }

    public void validateSecondaryParameters(String firstName, String lastName, ZonedDateTime birthdate, String email,
                                            List<String> errors) {
        ZonedDateTime currentDate = ZonedDateTime.now();
        if (birthdate != null && currentDate.minusYears(18).isBefore(birthdate)) {
            errors.add("Age should be more than 18");
        }
        if (!StringUtils.hasText(firstName)) {
            errors.add("First name shouldn't be empty");
        }
        if (!StringUtils.hasText(lastName)) {
            errors.add("Last name shouldn't be empty");
        }
        validateEmail(email, errors);
    }

    public void validatePassword(String password, String confirmPassword, List<String> errors) {
        if (!StringUtils.hasText(password)) {
            errors.add("Password shouldn't be empty");
        }
        if (!password.equals(confirmPassword)) {
            errors.add("Password and confirm password should be equal");
        }
    }

    public void validateEmail(String email, List<String> errors) {
        if (!isEmailValid(email)) {
            errors.add("Email is incorrect");
        }
    }

    private boolean isEmailValid(String email) {
        String emailRegex = "^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,}$";
        Pattern pattern = Pattern.compile(emailRegex, Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(email);
        return matcher.find();
    }

    public ZonedDateTime parseBirthdate(String birthdate, List<String> errors) {
        try {
            return LocalDate.parse(birthdate, DateTimeFormatter.ofPattern("yyyy-MM-dd"))
                    .atStartOfDay(TimeZone.getDefault().toZoneId());
        } catch (Exception exception) {
            errors.add(exception.getMessage());
            return null;
        }
    }

    public void validateChangeBalanceParameters(String currency, int value, String action, List<String> errors) {
        if (!StringUtils.hasText(currency)) {
            errors.add("Currency shouldn't be empty");
        }
        if (value <= 0) {
            errors.add("Value should be positive");
        }
        if (!"PUT".equals(action) && !"GET".equals(action)) {
            errors.add("Incorrect action value");
        }
    }

    public void validateTransferParameters(String sourceUser, String sourceCurrency, String targetUser,
                                           String targetCurrency, int value, List<String> errors) {
        if (!StringUtils.hasText(sourceCurrency)) {
            errors.add("Source currency shouldn't be empty");
        }
        if (!StringUtils.hasText(targetCurrency)) {
            errors.add("Target currency shouldn't be empty");
        }
        if (!StringUtils.hasText(targetUser)) {
            errors.add("Target currency shouldn't be empty");
        }
        if (Objects.equals(sourceUser, targetUser) && Objects.equals(sourceCurrency, targetCurrency)) {
            errors.add("User or currency should be different");
        }
        if (value <= 0) {
            errors.add("Value should be positive");
        }
    }
}
