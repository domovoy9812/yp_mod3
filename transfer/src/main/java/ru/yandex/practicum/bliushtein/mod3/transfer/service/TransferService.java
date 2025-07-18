package ru.yandex.practicum.bliushtein.mod3.transfer.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.bliushtein.mod3.shared.dto.GenericResponse;
import ru.yandex.practicum.bliushtein.mod3.shared.dto.accounts.AccountsTransferRequest;
import ru.yandex.practicum.bliushtein.mod3.shared.dto.accounts.AccountsTransferResponse;
import ru.yandex.practicum.bliushtein.mod3.shared.dto.accounts.BankUserResponse;
import ru.yandex.practicum.bliushtein.mod3.shared.dto.transfer.TransferRequest;
import ru.yandex.practicum.bliushtein.mod3.transfer.client.AccountsClient;
import ru.yandex.practicum.bliushtein.mod3.transfer.client.BlockerClient;
import ru.yandex.practicum.bliushtein.mod3.transfer.client.ExchangeClient;
import ru.yandex.practicum.bliushtein.mod3.transfer.client.NotificationClient;
import ru.yandex.practicum.bliushtein.mod3.transfer.TransferException;

import java.util.Objects;

@Slf4j
@Service
public class TransferService {
    private final NotificationClient notificationClient;
    private final BlockerClient blockerClient;
    private final AccountsClient accountsClient;
    private final ExchangeClient exchangeClient;
    public TransferService(@Autowired NotificationClient notificationClient,
                           @Autowired BlockerClient blockerClient,
                           @Autowired AccountsClient accountsClient,
                           @Autowired ExchangeClient exchangeClient) {
        this.notificationClient = notificationClient;
        this.blockerClient = blockerClient;
        this.accountsClient = accountsClient;
        this.exchangeClient = exchangeClient;
    }

    public AccountsTransferResponse transfer(TransferRequest request) {
        int targetAmount;
        if (Objects.equals(request.sourceCurrency(), request.targetCurrency())) {
            targetAmount = request.sourceAmount();
        } else {
            targetAmount = exchangeClient.exchange(request.sourceCurrency(), request.targetCurrency(),
                    request.sourceAmount());
        }
        BankUserResponse response = accountsClient.findBankUser(request.source());
        if (!response.isSuccessful()) {
            throw TransferException.sourceUserNotFound(request.source());
        }
        String email = response.getBankUser().email();
        GenericResponse validationResult = blockerClient.validate(request.source(), request.target(),
                request.sourceAmount(), email);
        if (!validationResult.isSuccessful()) {
            notificationClient.sendFailNotification(email, request, validationResult.getErrorMessage());
            throw TransferException.blockerValidationFailed(request.source());
        }
        notificationClient.sendSuccessNotification(email, request);
        return accountsClient.transfer(request.source(), request.sourceCurrency(),
                new AccountsTransferRequest(request.sourceAmount(), request.target(), request.targetCurrency(),
                        targetAmount));
    }
}
