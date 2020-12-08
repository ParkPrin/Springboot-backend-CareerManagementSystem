package me.parkprin.careermanagementsystem.domain.rolemappingmenu;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import me.parkprin.careermanagementsystem.domain.menu.Menu;
import me.parkprin.careermanagementsystem.domain.role.Role;

import javax.persistence.*;

@Getter
@NoArgsConstructor
@Entity
public class RoleMappingMenu {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name="menuId")
    private Menu menu;

    @ManyToOne
    @JoinColumn(name="roleId")
    private Role role;

    @Builder
    public RoleMappingMenu(Menu menu, Role role){
        this.menu = menu;
        this.role = role;
    }
}
