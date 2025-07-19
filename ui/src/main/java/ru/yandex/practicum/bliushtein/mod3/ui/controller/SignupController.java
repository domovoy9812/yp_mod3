package ru.yandex.practicum.bliushtein.mod3.ui.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.yandex.practicum.bliushtein.mod3.ui.service.AuthorizationService;

import java.time.LocalDate;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.TimeZone;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Slf4j
@Controller
@RequestMapping("/signup")
public class SignupController {
    private final AuthorizationService authorizationService;

    public SignupController(@Autowired AuthorizationService authorizationService) {
        this.authorizationService = authorizationService;
    }

    @GetMapping
    public String showCreateUserPage(Model model) {
        model.addAttribute("errors", new ArrayList<>());
        return "signup";
    }
    @PostMapping
    public String createUser(Model model,
                             @RequestParam("login") String name,
                             @RequestParam String password,
                             @RequestParam("confirm_password") String confirmPassword,
                             @RequestParam String firstName,
                             @RequestParam String lastName,
                             @RequestParam("birthdate") String birthdateString,
                             @RequestParam String email) {
        log.debug("calling bankUserClient.createUser with name='{}', password='{}', confirmPassword='{}', firstName='{}', lastName='{}', birthdate='{}', email='{}'",
                name, password, confirmPassword, firstName, lastName, birthdateString, email);
        ZonedDateTime birthdate = LocalDate.parse(birthdateString, DateTimeFormatter.ofPattern("yyyy-MM-dd"))
                .atStartOfDay(TimeZone.getDefault().toZoneId());
        List<String> errors = validateParameters(name, password, confirmPassword, firstName, lastName, birthdate, email);
        if (errors.isEmpty()) {
            authorizationService.createUser(name, password, firstName, lastName, birthdate, email);
            return "redirect:/main";
        } else {
            model.addAttribute("errors", errors);
            model.addAttribute("password", password);
            model.addAttribute("firstName", firstName);
            model.addAttribute("lastName", lastName);
            model.addAttribute("birthdate", birthdateString);
            model.addAttribute("email", email);
            return "signup";
        }
    }

    private List<String> validateParameters(String name, String password, String confirmPassword, String firstName,
                                            String lastName, ZonedDateTime birthdate, String email) {
        List<String> errors = new ArrayList<>();
        if (!StringUtils.hasText(name)) {
            errors.add("Login shouldn't be empty");
        }
        if (!StringUtils.hasText(password)) {
            errors.add("Password shouldn't be empty");
        }
        if (!password.equals(confirmPassword)) {
            errors.add("Password and confirm password should be equal");
        }
        ZonedDateTime currentDate = ZonedDateTime.now();
        if (currentDate.minusYears(18).isBefore(birthdate)) {
            errors.add("Age should be more than 18");
        }
        if (!StringUtils.hasText(firstName)) {
            errors.add("First name shouldn't be empty");
        }
        if (!StringUtils.hasText(lastName)) {
            errors.add("Last name shouldn't be empty");
        }
        if (!isEmailValid(email)) {
            errors.add("Email is incorrect");
        }
        return errors;
    }

    private boolean isEmailValid(String email) {
        String emailRegex = "^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,}$";
        Pattern pattern = Pattern.compile(emailRegex, Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(email);
        return matcher.find();
    }
}
