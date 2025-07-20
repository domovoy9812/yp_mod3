package ru.yandex.practicum.bliushtein.mod3.ui.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.bliushtein.mod3.shared.dto.accounts.AccountResponse;
import ru.yandex.practicum.bliushtein.mod3.shared.dto.accounts.AccountsTransferResponse;
import ru.yandex.practicum.bliushtein.mod3.ui.client.CashClient;
import ru.yandex.practicum.bliushtein.mod3.ui.client.ExchangeClient;
import ru.yandex.practicum.bliushtein.mod3.ui.client.TransferClient;
import ru.yandex.practicum.bliushtein.mod3.ui.dto.ExchangeRate;

import java.util.List;

@Service
public class OperationsService {
    private final CashClient cashClient;
    private final TransferClient transferClient;
    private final ExchangeClient exchangeClient;
    public OperationsService(@Autowired CashClient cashClient,
                             @Autowired TransferClient transferClient,
                             @Autowired ExchangeClient exchangeClient) {
        this.cashClient = cashClient;
        this.transferClient = transferClient;
        this.exchangeClient = exchangeClient;
    }

    public void getCash(String name, String currency, int amount, List<String> errors) {
        AccountResponse response = cashClient.getCash(name, currency, amount);
        if (!response.isSuccessful()) {
            errors.add(response.getErrorMessage());
        }
    }

    public void addMoney(String name, String currency, int amount, List<String> errors) {
        AccountResponse response = cashClient.addMoney(name, currency, amount);
        if (!response.isSuccessful()) {
            errors.add(response.getErrorMessage());
        }
    }

    public void internalTransfer(String user, String sourceCurrency, String targetCurrency, int sourceAmount,
                                 List<String> errors) {
        AccountsTransferResponse response = transferClient.transfer(user, sourceCurrency, user,
                targetCurrency, sourceAmount);
        if (!response.isSuccessful()) {
            errors.add(response.getErrorMessage());
        }
    }

    public void externalTransfer(String sourceUser, String sourceCurrency, String targetUser, String targetCurrency,
                                 int sourceAmount, List<String> errors) {
        AccountsTransferResponse response = transferClient.transfer(sourceUser, sourceCurrency, targetUser,
                targetCurrency, sourceAmount);
        if (!response.isSuccessful()) {
            errors.add(response.getErrorMessage());
        }
    }

    public List<ExchangeRate> getExchangeRates() {
        return exchangeClient.getExchangeRates().entrySet().stream()
                .map(entry -> new ExchangeRate(entry.getKey(), entry.getValue()))
                .toList();
    }
}
