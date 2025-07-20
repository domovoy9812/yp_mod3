package ru.yandex.practicum.bliushtein.mod3.accounts.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.yandex.practicum.bliushtein.mod3.accounts.AccountServiceException;
import ru.yandex.practicum.bliushtein.mod3.accounts.client.NotificationClient;
import ru.yandex.practicum.bliushtein.mod3.accounts.data.entity.AccountEntity;
import ru.yandex.practicum.bliushtein.mod3.accounts.data.entity.BankUserEntity;
import ru.yandex.practicum.bliushtein.mod3.accounts.data.repository.AccountRepository;
import ru.yandex.practicum.bliushtein.mod3.accounts.data.repository.BankUserRepository;
import ru.yandex.practicum.bliushtein.mod3.accounts.mapper.BankUserMapper;
import ru.yandex.practicum.bliushtein.mod3.shared.dto.accounts.BankUser;
import ru.yandex.practicum.bliushtein.mod3.shared.dto.accounts.BankUserWithPassword;

import java.time.ZonedDateTime;
import java.util.List;

@Slf4j
@Service
public class BankUserService {
    private final BankUserRepository userRepository;
    private final AccountRepository accountRepository;
    private final BankUserMapper userMapper;
    private final NotificationClient notificationClient;
    public BankUserService(@Autowired BankUserRepository userRepository,
                           @Autowired AccountRepository accountRepository,
                           @Autowired BankUserMapper userMapper,
                           @Autowired NotificationClient notificationClient) {
        this.userRepository = userRepository;
        this.accountRepository = accountRepository;
        this.userMapper = userMapper;
        this.notificationClient = notificationClient;
    }

    public BankUser findBankUser(String name) {
        BankUserEntity userEntity = userRepository.findByName(name);
        return userMapper.toDto(userEntity);
    }

    public BankUserWithPassword findBankUserToAuthenticate(String name) {
        BankUserEntity userEntity = userRepository.findByName(name);
        notificationClient.sendNotification(userEntity.getEmail(), "Log in Notification",
                "Someone getting your credentials!");
        return userMapper.toBankUserWithPasswordDto(userEntity);
    }

    public BankUser createBankUser(String name, String password, String firstName, String lastName,
                                   ZonedDateTime birthdate, String email) {
        BankUserEntity userEntity = new BankUserEntity(name, password, firstName, lastName, birthdate, email);
        BankUserEntity savedUserEntity = userRepository.save(userEntity);
        return userMapper.toDto(savedUserEntity);
    }

    @Transactional
    public void deleteBankUser(String user) throws AccountServiceException {
        BankUserEntity userEntity = userRepository.findByName(user);
        List<AccountEntity> accounts = accountRepository.findAllByUser(userEntity);
        if (accounts.isEmpty()) {
            userRepository.delete(userEntity);
        } else {
            throw AccountServiceException.accountsExist(user);
        }
    }

    @Transactional
    public void changeBankUserPassword(String user, String newPassword) {
        BankUserEntity userEntity = userRepository.findByName(user);
        userEntity.setPassword(newPassword);
        userRepository.save(userEntity);
    }

    @Transactional
    public BankUser updateBankUser(String user, String firstName, String lastName, ZonedDateTime birthdate, String email) {
        BankUserEntity userEntity = userRepository.findByName(user);
        userEntity.setFirstName(firstName);
        userEntity.setLastName(lastName);
        userEntity.setBirthdate(birthdate);
        userEntity.setEmail(email);
        return userMapper.toDto(userRepository.save(userEntity));
    }
}
