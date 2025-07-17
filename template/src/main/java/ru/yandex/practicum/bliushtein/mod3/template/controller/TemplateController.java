package ru.yandex.practicum.bliushtein.mod3.template.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.bliushtein.mod3.template.service.TemplateService;

@Slf4j
@RequestMapping("/template")
@RestController
public class TemplateController {
    private final TemplateService templateService;

    public TemplateController(@Autowired TemplateService templateService) {
        this.templateService = templateService;
    }
    @GetMapping("/say-hello")
    @PreAuthorize("hasAuthority('SCOPE_${scope}')")
    public void sayHello() {
        templateService.sayHello();
    }
}