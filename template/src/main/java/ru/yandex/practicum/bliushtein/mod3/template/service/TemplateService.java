package ru.yandex.practicum.bliushtein.mod3.template.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.yandex.practicum.bliushtein.mod3.template.data.repository.TemplateRepository;
import ru.yandex.practicum.bliushtein.mod3.template.mapper.TemplateMapper;

@Slf4j
@Service
public class TemplateService {
    private final TemplateRepository templateRepository;
    private final TemplateMapper templateMapper;

    public TemplateService(@Autowired TemplateRepository templateRepository,
                           @Autowired TemplateMapper templateMapper) {
        this.templateRepository = templateRepository;
        this.templateMapper = templateMapper;
    }

    @Transactional
    public void sayHello() {
        log.info("Hello world!");
    }

}
