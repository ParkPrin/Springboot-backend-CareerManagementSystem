package me.parkprin.careermanagementsystem.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
@Entity
public class User extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Long version;

    @Column(columnDefinition = "boolean default false")
    private boolean accountExpired;

    @Column(columnDefinition = "boolean default false")
    private boolean accountLocked;

    private String createIp;

    private LocalDateTime dateWithdraw;

    @Column(nullable = false)
    private boolean enabled;

    private LocalDateTime lastPasswordChanged;

    private String lastUpdateIp;

    private String password;

    private boolean passwordExpired;

    private String userNickname;

    private boolean withdraw;
}
