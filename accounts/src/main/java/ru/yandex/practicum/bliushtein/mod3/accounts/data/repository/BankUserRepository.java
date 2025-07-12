package ru.yandex.practicum.bliushtein.mod3.accounts.data.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.yandex.practicum.bliushtein.mod3.accounts.data.entity.BankUserEntity;

@Repository
public interface BankUserRepository extends JpaRepository<BankUserEntity, Long> {
    BankUserEntity findByName(String name);
}
