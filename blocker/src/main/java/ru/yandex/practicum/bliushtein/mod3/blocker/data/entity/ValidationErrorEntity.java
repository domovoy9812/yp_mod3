package ru.yandex.practicum.bliushtein.mod3.blocker.data.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.ZonedDateTime;

@Getter
@Setter
@NoArgsConstructor
@RequiredArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "blocked_operations")
public class ValidationErrorEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NonNull
    @Column(nullable = false)
    private ZonedDateTime date;

    @NonNull
    @Column(nullable = false)
    private String operation;

    @NonNull
    @Column(nullable = false)
    private String message;

    private String source;
    private String target;
    private int amount;
}
