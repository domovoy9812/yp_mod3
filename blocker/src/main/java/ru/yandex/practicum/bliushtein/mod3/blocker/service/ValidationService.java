package ru.yandex.practicum.bliushtein.mod3.blocker.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.yandex.practicum.bliushtein.mod3.blocker.ValidationException;
import ru.yandex.practicum.bliushtein.mod3.blocker.client.NotificationClient;
import ru.yandex.practicum.bliushtein.mod3.blocker.data.entity.ValidationErrorEntity;
import ru.yandex.practicum.bliushtein.mod3.blocker.data.repository.BlockerRepository;
import ru.yandex.practicum.bliushtein.mod3.blocker.mapper.ValidationErrorMapper;
import ru.yandex.practicum.bliushtein.mod3.shared.dto.blocker.BlockerValidationError;

import java.time.ZonedDateTime;
import java.util.List;
import java.util.Objects;

@Slf4j
@Service
public class ValidationService {
    public static final String GET_CACHE_VALIDATION_MESSAGE = "Get cache operation blocked. Limit exceeded.";
    public static final String ADD_MONEY_VALIDATION_MESSAGE = "Add money operation blocked. Limit exceeded.";
    public static final String TRANSFER_VALIDATION_MESSAGE = "Transfer operation blocked. Limit exceeded.";

    private final BlockerRepository blockerRepository;
    private final ValidationErrorMapper validationErrorMapper;
    private final NotificationClient notificationClient;
    public ValidationService(@Autowired BlockerRepository blockerRepository,
                             @Autowired ValidationErrorMapper validationErrorMapper,
                             @Autowired NotificationClient notificationClient) {
        this.blockerRepository = blockerRepository;
        this.validationErrorMapper = validationErrorMapper;
        this.notificationClient = notificationClient;
    }

    @Transactional
    public void validateGetCache(String source, int amount, String email) throws ValidationException {
        if (amount > 200) {
            blockerRepository.save(new ValidationErrorEntity(null, ZonedDateTime.now(), "get cache",
                    GET_CACHE_VALIDATION_MESSAGE, source, null, amount));
            notificationClient.sendNotification(email, "Operation Blocked!", GET_CACHE_VALIDATION_MESSAGE);
            throw new ValidationException(GET_CACHE_VALIDATION_MESSAGE);
        }
    }

    @Transactional
    public void validateAddMoney(String target, int amount, String email) throws ValidationException {
        if (amount > 200) {
            blockerRepository.save(new ValidationErrorEntity(null, ZonedDateTime.now(), "add money",
                    ADD_MONEY_VALIDATION_MESSAGE, null, target, amount));
            notificationClient.sendNotification(email, "Operation Blocked!", ADD_MONEY_VALIDATION_MESSAGE);
            throw new ValidationException(ADD_MONEY_VALIDATION_MESSAGE);
        }
    }

    @Transactional
    public void validateTransfer(String source, String target, int amount, String email) throws ValidationException {
        if (!Objects.equals(source, target) && amount > 200) {
            blockerRepository.save(new ValidationErrorEntity(null, ZonedDateTime.now(), "transfer",
                    TRANSFER_VALIDATION_MESSAGE, source, target, amount));
            notificationClient.sendNotification(email, "Operation Blocked!", TRANSFER_VALIDATION_MESSAGE);
            throw new ValidationException(TRANSFER_VALIDATION_MESSAGE);
        }
    }

    public List<BlockerValidationError> getAllErrors() {
        return validationErrorMapper.toDto(blockerRepository.findAll());
    }
}
