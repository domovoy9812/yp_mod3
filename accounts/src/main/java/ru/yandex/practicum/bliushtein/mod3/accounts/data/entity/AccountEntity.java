package ru.yandex.practicum.bliushtein.mod3.accounts.data.entity;

import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@RequiredArgsConstructor
@Entity
@Table(name = "accounts", uniqueConstraints = @UniqueConstraint(columnNames = {"user_id", "currency"}))
public class AccountEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NonNull
    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private BankUserEntity user;

    @NonNull
    @Column(nullable = false)
    private String currency;

    @Column(nullable = false)
    private int balance;
}
