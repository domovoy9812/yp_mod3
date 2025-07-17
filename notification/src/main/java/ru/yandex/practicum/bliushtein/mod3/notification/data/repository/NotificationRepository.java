package ru.yandex.practicum.bliushtein.mod3.notification.data.repository;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.yandex.practicum.bliushtein.mod3.notification.data.entity.NotificationEntity;

import java.util.List;

@Repository
public interface NotificationRepository extends JpaRepository<NotificationEntity, Long> {
    List<NotificationEntity> findBySentAndRetryCountLessThan(boolean sent, int maxTryCount, Pageable pageable);
}
