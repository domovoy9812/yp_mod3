package ru.yandex.practicum.bliushtein.mod3.transfer.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.bliushtein.mod3.shared.dto.GenericResponse;
import ru.yandex.practicum.bliushtein.mod3.shared.dto.accounts.AccountsTransferResponse;
import ru.yandex.practicum.bliushtein.mod3.shared.dto.transfer.TransferRequest;
import ru.yandex.practicum.bliushtein.mod3.transfer.service.TransferService;

@Slf4j
@RequestMapping("/transfer")
@RestController
public class TransferController {
    private final TransferService transferService;

    public TransferController(@Autowired TransferService transferService) {
        this.transferService = transferService;
    }
    @PostMapping
    @PreAuthorize("hasAuthority('SCOPE_transfer.write')")
    public AccountsTransferResponse transfer(@RequestBody TransferRequest request) {
        return transferService.transfer(request);
    }

    @ExceptionHandler
    public GenericResponse handleException(Throwable exception) {
        log.error("exception in TransferController.", exception);
        return GenericResponse.fail(exception.getMessage());
    }
}