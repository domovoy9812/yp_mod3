package ru.yandex.practicum.bliushtein.mod3.blocker.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import ru.yandex.practicum.bliushtein.mod3.blocker.data.entity.ValidationErrorEntity;
import ru.yandex.practicum.bliushtein.mod3.shared.dto.blocker.BlockerValidationError;

import java.util.List;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface ValidationErrorMapper {
    List<BlockerValidationError> toDto(List<ValidationErrorEntity> entity);
}
