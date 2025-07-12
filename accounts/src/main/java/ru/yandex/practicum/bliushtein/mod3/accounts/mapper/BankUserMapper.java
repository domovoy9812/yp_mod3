package ru.yandex.practicum.bliushtein.mod3.accounts.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import ru.yandex.practicum.bliushtein.mod3.accounts.data.entity.BankUserEntity;
import ru.yandex.practicum.bliushtein.mod3.shared.dto.accounts.BankUser;
import ru.yandex.practicum.bliushtein.mod3.shared.dto.accounts.BankUserWithPassword;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface BankUserMapper {
    BankUser toDto(BankUserEntity entity);
    BankUserWithPassword toBankUserWithPasswordDto(BankUserEntity entity);
    BankUserEntity toEntity(BankUser entity);
}
