package ru.yandex.practicum.bliushtein.mod3.accounts.data.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.yandex.practicum.bliushtein.mod3.accounts.data.entity.AccountEntity;
import ru.yandex.practicum.bliushtein.mod3.accounts.data.entity.BankUserEntity;

import java.util.List;

@Repository
public interface AccountRepository extends JpaRepository<AccountEntity, Long> {
    List<AccountEntity> findAllByUser(BankUserEntity user);
}
