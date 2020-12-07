package me.parkprin.careermanagementsystem.domain.menu;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@NoArgsConstructor
@Entity
public class Menu {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false, length = 100)
    private String menuId;

    @Column(length = 100)
    private String menuName;

    @Column(length = 100)
    private String iconName;

    @Column(length = 255)
    private String url;

    @Column(columnDefinition = "boolean default false")
    private boolean isLoginCheck;

    @Column(columnDefinition = "boolean default false")
    private boolean isAdminCheck;

    @Column(columnDefinition = "boolean default false")
    private boolean isBasicMenu;

    @Builder
    public Menu (String menuId, String menuName, String iconName,
                String url, boolean isLoginCheck, boolean isAdminCheck,
                boolean isBasicMenu) {
        this.menuId = menuId;
        this.menuName = menuName;
        this.iconName = iconName;
        this.url = url;
        this.isLoginCheck = isLoginCheck;
        this.isAdminCheck = isAdminCheck;
        this.isBasicMenu = isBasicMenu;
    }
}
