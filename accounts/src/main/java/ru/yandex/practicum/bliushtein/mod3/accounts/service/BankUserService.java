package ru.yandex.practicum.bliushtein.mod3.accounts.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.bliushtein.mod3.accounts.AccountServiceException;
import ru.yandex.practicum.bliushtein.mod3.accounts.data.entity.AccountEntity;
import ru.yandex.practicum.bliushtein.mod3.accounts.data.entity.BankUserEntity;
import ru.yandex.practicum.bliushtein.mod3.accounts.data.repository.AccountRepository;
import ru.yandex.practicum.bliushtein.mod3.accounts.data.repository.BankUserRepository;
import ru.yandex.practicum.bliushtein.mod3.accounts.mapper.BankUserMapper;
import ru.yandex.practicum.bliushtein.mod3.shared.dto.BankUser;
import ru.yandex.practicum.bliushtein.mod3.shared.dto.BankUserWithPassword;

import java.util.List;

@Slf4j
@Service
public class BankUserService {
    private final BankUserRepository userRepository;
    private final AccountRepository accountRepository;
    private final BankUserMapper userMapper;

    public BankUserService(@Autowired BankUserRepository userRepository,
                           @Autowired AccountRepository accountRepository,
                           @Autowired BankUserMapper userMapper) {
        this.userRepository = userRepository;
        this.accountRepository = accountRepository;
        this.userMapper = userMapper;
    }

    public BankUser findBankUser(String name) {
        log.info("AccountService called for {}", name);
        BankUserEntity userEntity = userRepository.findByName(name);
        return userMapper.toDto(userEntity);
    }

    public BankUserWithPassword findBankUserToAuthenticate(String name) {
        log.info("AccountService called for {}", name);
        BankUserEntity userEntity = userRepository.findByName(name);
        return userMapper.toBankUserWithPasswordDto(userEntity);
    }

    public BankUser createBankUser(String name, String password, String firstName, String lastName, String email) {
        BankUserEntity userEntity = new BankUserEntity(name, password, firstName, lastName, email);
        BankUserEntity savedUserEntity = userRepository.save(userEntity);
        return userMapper.toDto(savedUserEntity);
    }

    public void deleteBankUser(String user) throws AccountServiceException {
        BankUserEntity userEntity = userRepository.findByName(user);
        List<AccountEntity> accounts = accountRepository.findAllByUser(userEntity);
        if (accounts.isEmpty()) {
            userRepository.delete(userEntity);
        } else {
            throw AccountServiceException.accountsExist(user);
        }
    }
}
