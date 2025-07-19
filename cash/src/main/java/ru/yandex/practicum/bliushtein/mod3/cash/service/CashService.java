package ru.yandex.practicum.bliushtein.mod3.cash.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import ru.yandex.practicum.bliushtein.mod3.cash.CashException;
import ru.yandex.practicum.bliushtein.mod3.cash.client.AccountsClient;
import ru.yandex.practicum.bliushtein.mod3.cash.client.BlockerClient;
import ru.yandex.practicum.bliushtein.mod3.cash.client.NotificationClient;
import ru.yandex.practicum.bliushtein.mod3.shared.dto.GenericResponse;
import ru.yandex.practicum.bliushtein.mod3.shared.dto.accounts.AccountResponse;
import ru.yandex.practicum.bliushtein.mod3.shared.dto.accounts.BankUserResponse;
import ru.yandex.practicum.bliushtein.mod3.shared.dto.cash.CashRequest;

@Slf4j
@Service
public class CashService {
    private final AccountsClient accountsClient;
    private final NotificationClient notificationClient;
    private final BlockerClient blockerClient;
    public CashService(@Autowired AccountsClient accountsClient,
                       @Autowired NotificationClient notificationClient,
                       @Autowired BlockerClient blockerClient) {
        this.accountsClient = accountsClient;
        this.notificationClient = notificationClient;
        this.blockerClient = blockerClient;
    }

    public AccountResponse getCash(CashRequest request) {
        return changeBalance(request, true, "Get cash");
    }

    public AccountResponse addMoney(CashRequest request) {
        return changeBalance(request, false, "Add money");
    }

    public AccountResponse changeBalance(CashRequest request, boolean isGetCash, String operationName) {
        validateRequest(request);
        String email = findUserEmail(request.user());
        GenericResponse validationResponse = blockerClient.validate(request.user(), request.currency(), request.amount(), isGetCash, email);
        if (!validationResponse.isSuccessful()) {
            throw CashException.blockerValidationFailed();
        }
        AccountResponse response = accountsClient.changeAccountBalance(request.user(), request.currency(),
                isGetCash ? -request.amount() : request.amount());
        if (email != null) {
            if (!response.isSuccessful()) {
                notificationClient.sendNotification(email, operationName + " failed",
                        response.getErrorMessage());
            } else {
                notificationClient.sendNotification(email, operationName + " successful", operationName
                        + " operation is successful. Currency: '%s', amount: '%d'"
                                .formatted(request.currency(), request.amount()));
            }
        }
        return response;
    }

    private void validateRequest(CashRequest request) {
        if (request.amount() <= 0 || !StringUtils.hasText(request.user()) || !StringUtils.hasText(request.currency())) {
            throw CashException.incorrectRequest();
        }
    }

    private String findUserEmail(String user) {
        BankUserResponse userResponse = accountsClient.findBankUser(user);
        if (userResponse.isSuccessful()) {
            return userResponse.getBankUser().email();
        } else {
            throw CashException.userNotFound(user);
        }
    }
}
