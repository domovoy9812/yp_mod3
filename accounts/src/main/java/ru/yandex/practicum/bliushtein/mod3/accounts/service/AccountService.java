package ru.yandex.practicum.bliushtein.mod3.accounts.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.yandex.practicum.bliushtein.mod3.accounts.AccountServiceException;
import ru.yandex.practicum.bliushtein.mod3.accounts.data.entity.AccountEntity;
import ru.yandex.practicum.bliushtein.mod3.accounts.data.entity.BankUserEntity;
import ru.yandex.practicum.bliushtein.mod3.accounts.data.repository.AccountRepository;
import ru.yandex.practicum.bliushtein.mod3.accounts.data.repository.BankUserRepository;
import ru.yandex.practicum.bliushtein.mod3.accounts.mapper.AccountMapper;
import ru.yandex.practicum.bliushtein.mod3.shared.dto.accounts.Account;
import ru.yandex.practicum.bliushtein.mod3.shared.dto.accounts.AccountsTransferRequest;
import ru.yandex.practicum.bliushtein.mod3.shared.dto.accounts.AccountsTransferResponse;

import java.util.List;
import java.util.Objects;

@Slf4j
@Service
public class AccountService {
    private final AccountRepository accountRepository;
    private final BankUserRepository userRepository;
    private final AccountMapper accountMapper;

    public AccountService(@Autowired AccountRepository accountRepository,
                          @Autowired BankUserRepository userRepository,
                          @Autowired AccountMapper accountMapper) {
        this.accountRepository = accountRepository;
        this.userRepository = userRepository;
        this.accountMapper = accountMapper;
    }

    @Transactional
    public Account findAccount(String user, String currency) throws AccountServiceException {
        BankUserEntity userEntity = userRepository.findByName(user);
        AccountEntity accountEntity = accountRepository.findByUserAndCurrency(userEntity, currency);
        if (accountEntity == null) {
            throw AccountServiceException.accountNotFound(user, currency);
        }
        return accountMapper.toDto(accountEntity);
    }

    @Transactional
    public List<Account> findAllAccounts(String user) {
        BankUserEntity userEntity = userRepository.findByName(user);
        List<AccountEntity> accountEntities = accountRepository.findAllByUser(userEntity);
        return accountMapper.toDtoList(accountEntities);
    }

    @Transactional
    public void deleteAccount(String user, String currency) throws AccountServiceException {
        BankUserEntity userEntity = userRepository.findByName(user);
        AccountEntity accountEntity = accountRepository.findByUserIdAndCurrency(userEntity.getId(), currency);
        if (accountEntity == null) {
            log.warn("Account for user='{}' and currency='{}' was not found", user, currency);
            return;
        }
        if (accountEntity.getBalance() > 0) {
            throw AccountServiceException.cantDeleteAccountWithNonZeroBalance(user, currency);
        }
        accountRepository.delete(accountEntity);
    }

    @Transactional
    public Account createAccount(String user, String currency) throws AccountServiceException {
        BankUserEntity userEntity = userRepository.findByName(user);
        AccountEntity existingAccountEntity = accountRepository.findByUserAndCurrency(userEntity, currency);
        if (existingAccountEntity != null) {
            throw AccountServiceException.accountAlreadyExists(user, currency);
        }
        AccountEntity newAccountEntity = new AccountEntity(userEntity, currency);
        AccountEntity savedAccountEntity = accountRepository.save(newAccountEntity);
        return accountMapper.toDto(savedAccountEntity);
    }

    @Transactional
    public Account updateBalance(String user, String currency, int change) throws AccountServiceException {
        BankUserEntity userEntity = userRepository.findByName(user);
        AccountEntity accountEntity = accountRepository.findByUserAndCurrency(userEntity, currency);
        if (accountEntity == null) {
            throw AccountServiceException.accountNotFound(user, currency);
        }
        int targetBalance = accountEntity.getBalance() + change;
        if (targetBalance >= 0) {
            accountEntity.setBalance(targetBalance);
            AccountEntity savedAccountEntity = accountRepository.save(accountEntity);
            return accountMapper.toDto(savedAccountEntity);
        } else {
            throw AccountServiceException.notEnoughMoney(user, currency, change);
        }
    }

    @Transactional
    public AccountsTransferResponse transfer(String user, String currency, AccountsTransferRequest request)
            throws AccountServiceException {
        if (Objects.equals(user, request.targetUser()) && Objects.equals(currency, request.targetCurrency())) {
            throw AccountServiceException.accountsShouldBeDifferent(user, currency);
        }
        AccountEntity sourceAccountEntity = getAccountForTransfer(user, currency, -request.amount());
        AccountEntity targetAccountEntity = getAccountForTransfer(request.targetUser(), request.targetCurrency(),
                request.targetAmount());
        sourceAccountEntity.setBalance(sourceAccountEntity.getBalance() - request.amount());
        targetAccountEntity.setBalance(targetAccountEntity.getBalance() + request.targetAmount());
        sourceAccountEntity = accountRepository.save(sourceAccountEntity);
        targetAccountEntity = accountRepository.save(targetAccountEntity);
        return AccountsTransferResponse.ok(accountMapper.toDto(sourceAccountEntity), accountMapper.toDto(targetAccountEntity));
    }

    private AccountEntity getAccountForTransfer(String user, String currency, int change)
            throws AccountServiceException {
        BankUserEntity userEntity = userRepository.findByName(user);
        AccountEntity accountEntity = accountRepository.findByUserAndCurrency(userEntity, currency);
        if (accountEntity == null) {
            throw AccountServiceException.accountNotFound(user, currency);
        }
        if (accountEntity.getBalance() + change < 0) {
            throw AccountServiceException.notEnoughMoney(user, currency, change);
        }
        return accountEntity;
    }
}
