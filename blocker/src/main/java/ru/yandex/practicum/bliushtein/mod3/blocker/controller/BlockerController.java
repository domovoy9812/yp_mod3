package ru.yandex.practicum.bliushtein.mod3.blocker.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.bliushtein.mod3.blocker.ValidationException;
import ru.yandex.practicum.bliushtein.mod3.blocker.service.ValidationService;
import ru.yandex.practicum.bliushtein.mod3.shared.dto.GenericResponse;
import ru.yandex.practicum.bliushtein.mod3.shared.dto.blocker.BlockerValidationError;
import ru.yandex.practicum.bliushtein.mod3.shared.dto.blocker.BlockerValidationRequest;

import java.util.List;

@Slf4j
@RequestMapping("/blocker")
@RestController
public class BlockerController {
    private final ValidationService validationService;

    public BlockerController(@Autowired ValidationService validationService) {
        this.validationService = validationService;
    }
    @PostMapping("/validate")
    @PreAuthorize("hasAuthority('SCOPE_blocker.write')")
    public GenericResponse validate(@RequestBody BlockerValidationRequest request) {
        try {
            switch (request.operation()) {
                case TRANSFER -> validationService.validateTransfer(request.source(), request.target(),
                        request.amount(), request.email());
                case ADD_MONEY -> validationService.validateAddMoney(request.target(), request.amount(),
                        request.email());
                case GET_CACHE -> validationService.validateGetCache(request.source(), request.amount(),
                        request.email());
                default -> GenericResponse.fail("operation shouldn't be empty");
            }
        } catch (ValidationException exception) {
            return GenericResponse.fail(exception.getMessage());
        }
        return GenericResponse.ok();
    }

    @GetMapping("/errors")
    @PreAuthorize("hasAuthority('SCOPE_blocker.read')")
    public List<BlockerValidationError> getValidationErrors() {
        return validationService.getAllErrors();
    }
}