package ru.yandex.practicum.bliushtein.mod3.accounts.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import ru.yandex.practicum.bliushtein.mod3.accounts.data.entity.AccountEntity;
import ru.yandex.practicum.bliushtein.mod3.shared.dto.accounts.Account;

import java.util.List;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface AccountMapper {
    Account toDto(AccountEntity entity);
    List<Account> toDtoList(List<AccountEntity> entities);
}
