package me.parkprin.careermanagementsystem.domain;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@NoArgsConstructor
@Entity
public class Role {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false, length = 100)
    private String roleId;

    @Column(nullable = false, length = 100)
    private String roleName;

    @Column(columnDefinition = "boolean default false")
    private boolean isAdmin;

    @Builder
    public Role(String roleId, String roleName, boolean isAdmin){
        this.roleId = roleId;
        this.roleName = roleName;
        this.isAdmin = isAdmin;
    }
}
