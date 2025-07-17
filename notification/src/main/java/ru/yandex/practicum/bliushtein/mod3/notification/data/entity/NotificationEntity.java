package ru.yandex.practicum.bliushtein.mod3.notification.data.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.ZonedDateTime;

@Getter
@Setter
@NoArgsConstructor
@RequiredArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "notifications")
public class NotificationEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    @NonNull
    private String source;

    @Column(nullable = false)
    @NonNull
    private String email;

    @Column(nullable = false)
    @NonNull
    private String subject;

    @Column(nullable = false)
    @NonNull
    private String message;

    @Column(nullable = false)
    @NonNull
    private boolean sent;

    @Column(nullable = false)
    @NonNull
    private int retryCount;

    private ZonedDateTime sendDate;
}
