package me.parkprin.careermanagementsystem.domain;

import lombok.Builder;
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

    @Column(unique = true, nullable = false)
    private String userId;

    @Column(nullable = false)
    private Long version;

    @Column(columnDefinition = "boolean default false")
    private boolean accountExpired;

    @Column(columnDefinition = "boolean default false")
    private boolean accountLocked;

    private String createIp;

    private LocalDateTime dateWithdraw;

    @Column(nullable = false, columnDefinition = "boolean default true")
    private boolean enabled;

    private LocalDateTime lastPasswordChanged;

    private String lastUpdateIp;

    private String password;

    @Column(nullable = false, columnDefinition = "boolean default false")
    private boolean passwordExpired;

    private String userNickname;

    @Column(columnDefinition = "boolean default false")
    private boolean withdraw;

    @Column
    private LocalDateTime dateCreated;

    @Column
    private LocalDateTime lastUpdated;

    @Builder
    public User(String userNickname, String userId, String password, Long version,
                boolean accountExpired, boolean accountLocked, String createIp,
                LocalDateTime dateWithdraw, boolean enabled, LocalDateTime lastPasswordChanged,
                String lastUpdateIp, boolean passwordExpired, boolean withdraw){
        this.userId = userId;
        this.userNickname = userNickname;
        this.password = password;
        this.version = version;
        this.accountExpired = accountExpired;
        this.accountLocked = accountLocked;
        this.createIp = createIp;
        this.dateWithdraw = dateWithdraw;
        this.enabled = enabled;
        this.lastPasswordChanged = LocalDateTime.now();
        this.lastUpdateIp = lastUpdateIp;
        this.passwordExpired = passwordExpired;
        this.withdraw = withdraw;
        this.dateCreated = LocalDateTime.now();
        this.lastUpdated = LocalDateTime.now();
    }
}
