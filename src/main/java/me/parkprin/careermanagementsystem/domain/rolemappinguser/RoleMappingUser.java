package me.parkprin.careermanagementsystem.domain.rolemappinguser;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import me.parkprin.careermanagementsystem.domain.user.User;
import me.parkprin.careermanagementsystem.domain.role.Role;

import javax.persistence.*;

@Getter
@NoArgsConstructor
@Entity
public class RoleMappingUser {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name="userId")
    private User user;

    @ManyToOne
    @JoinColumn(name="roleId")
    private Role role;

    @Builder
    public RoleMappingUser(User user, Role role) {
        this.user = user;
        this.role = role;
    }
}
