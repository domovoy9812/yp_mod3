package ru.yandex.practicum.bliushtein.mod3.ui.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.bliushtein.mod3.shared.dto.accounts.BankUser;
import ru.yandex.practicum.bliushtein.mod3.ui.dto.ExchangeRate;
import ru.yandex.practicum.bliushtein.mod3.ui.service.BankUserService;
import ru.yandex.practicum.bliushtein.mod3.ui.service.OperationsService;
import ru.yandex.practicum.bliushtein.mod3.ui.utils.ControllerUtils;

import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Controller
@RequestMapping("/main")
public class MainController {

    private final BankUserService userService;
    private final ControllerUtils controllerUtils;
    private final OperationsService operationsService;

    public MainController(@Autowired BankUserService userService,
                          @Autowired ControllerUtils controllerUtils,
                          @Autowired OperationsService operationsService) {
        this.userService = userService;
        this.controllerUtils = controllerUtils;
        this.operationsService = operationsService;
    }

    @GetMapping
    @PostMapping
    public String showMainPage(Authentication authentication, Model model) {
        fillUserInfo(model, authentication.getName());
        return "main";
    }

    @GetMapping("/rates")
    @ResponseBody
    public List<ExchangeRate> getRates() {
        return operationsService.getExchangeRates();
        //return List.of(new ExchangeRate("USD", 0.1f), new ExchangeRate("CNY", 5f));
    }

    @PostMapping("/change-password")
    public String changePassword(Authentication authentication,
                                 Model model,
                                 @RequestParam(required = false) String password,
                                 @RequestParam(value = "confirm_password", required = false) String confirmPassword) {
        String name = authentication.getName();
        List<String> errors = new ArrayList<>();
        controllerUtils.validatePassword(password, confirmPassword, errors);
        if (errors.isEmpty()) {
            userService.changePassword(name, password, errors);
        }
        if (!errors.isEmpty()) {
            model.addAttribute("changePassErrors", errors);
        }
        fillUserInfo(model, name);
        return "main";
    }

    @PostMapping("/update")
    public String updateBankUser(Authentication authentication,
                                 Model model,
                                 @RequestParam(required = false) String firstName,
                                 @RequestParam(required = false) String lastName,
                                 @RequestParam(value = "birthdate", required = false) String birthdateString,
                                 @RequestParam(required = false) String email) {
        String name = authentication.getName();
        List<String> errors = new ArrayList<>();
        ZonedDateTime birthdate = controllerUtils.parseBirthdate(birthdateString, errors);
        BankUser bankUser = null;
        controllerUtils.validateSecondaryParameters(firstName, lastName, birthdate, email, errors);
        if (errors.isEmpty()) {
            bankUser = userService.updateBankUser(name, firstName, lastName, birthdate, email, errors);
        }
        if (!errors.isEmpty()) {
            model.addAttribute("updateUserErrors", errors);
        }
        fillUserInfo(model, name, bankUser);
        return "main";
    }

    @PostMapping("/change-balance")
    public String changeBalance(Authentication authentication,
                                Model model,
                                @RequestParam(required = false) String currency,
                                @RequestParam(required = false) int value,
                                @RequestParam(required = false) String action) {
        String name = authentication.getName();
        List<String> errors = new ArrayList<>();
        controllerUtils.validateChangeBalanceParameters(currency, value, action, errors);
        if (errors.isEmpty()) {
            if ("GET".equals(action)) {
                operationsService.getCash(name, currency, value, errors);
            } else {
                operationsService.addMoney(name, currency, value, errors);
            }
        }
        if (!errors.isEmpty()) {
            model.addAttribute("changeBalanceErrors", errors);
        }
        fillUserInfo(model, name);
        return "main";
    }

    @PostMapping("/internal-transfer")
    public String internalTransfer(Authentication authentication,
                                Model model,
                                @RequestParam(value = "from_currency", required = false) String fromCurrency,
                                @RequestParam(value = "to_currency", required = false) String toCurrency,
                                @RequestParam(required = false) int value) {
        String name = authentication.getName();
        List<String> errors = new ArrayList<>();
        controllerUtils.validateTransferParameters(name, fromCurrency, name, toCurrency, value, errors);
        if (errors.isEmpty()) {
            operationsService.internalTransfer(name, fromCurrency, toCurrency, value, errors);
        }
        if (!errors.isEmpty()) {
            model.addAttribute("intTransferErrors", errors);
        }
        fillUserInfo(model, name);
        return "main";
    }

    @PostMapping("/external-transfer")
    public String externalTransfer(Authentication authentication,
                                   Model model,
                                   @RequestParam(value = "from_currency", required = false) String fromCurrency,
                                   @RequestParam(value = "to_currency", required = false) String toCurrency,
                                   @RequestParam(required = false) int value,
                                   @RequestParam(value = "to_login", required = false) String targetUser) {
        String name = authentication.getName();
        List<String> errors = new ArrayList<>();
        controllerUtils.validateTransferParameters(name, fromCurrency, targetUser, toCurrency, value, errors);
        if (errors.isEmpty()) {
            operationsService.externalTransfer(name, fromCurrency, targetUser, toCurrency, value, errors);
        }
        if (!errors.isEmpty()) {
            model.addAttribute("extTransferErrors", errors);
        }
        fillUserInfo(model, name);
        return "main";
    }

    private void fillUserInfo(Model model, String user) {
        fillUserInfo(model, user, null);
    }

    private void fillUserInfo(Model model, String user, BankUser bankUser) {
        if (bankUser == null) {
            bankUser = userService.getUser(user);
        }
        model.addAttribute("user", bankUser);
        model.addAttribute("accounts", userService.getUserAccounts(user));

    }
}
