package ru.yandex.practicum.bliushtein.mod3.ui.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.yandex.practicum.bliushtein.mod3.shared.dto.accounts.BankUserResponse;
import ru.yandex.practicum.bliushtein.mod3.ui.service.BankUserService;
import ru.yandex.practicum.bliushtein.mod3.ui.utils.ControllerUtils;

import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Controller
@RequestMapping("/signup")
public class SignupController {
    private final BankUserService userService;
    private final ControllerUtils controllerUtils;
    public SignupController(@Autowired BankUserService userService,
                            @Autowired ControllerUtils controllerUtils) {
        this.userService = userService;
        this.controllerUtils = controllerUtils;
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
        List<String> errors = new ArrayList<>();
        ZonedDateTime birthdate = controllerUtils.parseBirthdate(birthdateString, errors);
        controllerUtils.validateUserParameters(name, password, confirmPassword, firstName, lastName, birthdate, email,
                errors);
        if (errors.isEmpty()) {
            BankUserResponse response = userService.createUser(name, password, firstName, lastName,
                    birthdate, email);
            if (!response.isSuccessful()) {
                errors.add(response.getErrorMessage());
            }
        }
        if (errors.isEmpty()) {
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

}
