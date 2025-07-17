package ru.yandex.practicum.bliushtein.mod3.blocker.data.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.yandex.practicum.bliushtein.mod3.blocker.data.entity.ValidationErrorEntity;

@Repository
public interface BlockerRepository extends JpaRepository<ValidationErrorEntity, Long> {
}
