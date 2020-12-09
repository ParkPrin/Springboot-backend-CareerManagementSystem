package me.parkprin.careermanagementsystem.initbatch;

import me.parkprin.careermanagementsystem.domain.menu.Menu;
import me.parkprin.careermanagementsystem.domain.menu.MenuRepository;
import me.parkprin.careermanagementsystem.domain.role.Role;
import me.parkprin.careermanagementsystem.domain.role.RoleRepository;
import me.parkprin.careermanagementsystem.domain.rolemappingmenu.RoleMappingMenu;
import me.parkprin.careermanagementsystem.domain.rolemappingmenu.RoleMappingMenuRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

/**
 * 초기 데이터 세팅하는 클래스
 */

@Component
public class InitDataBatch {

    @Autowired
    RoleRepository roleRepository;

    @Autowired
    MenuRepository menuRepository;

    @Autowired
    RoleMappingMenuRepository roleMappingMenuRepository;

    @Bean
    public CommandLineRunner loadData(){
        return (args -> {

            /**
             * Role 데이터 초기세팅
             */
            if (roleRepository.findAll().size() == 0){
                roleRepository.save(
                        Role.builder()
                                .roleId("user")
                                .roleName("사용자")
                                .isAdmin(false)
                                .build());

                roleRepository.save(
                        Role.builder()
                                .roleId("admin")
                                .roleName("관리자")
                                .isAdmin(true)
                                .build());
            }

            /**
             *
             *  Menu 데이터 초기세팅
             *
             */

            if (menuRepository.findAll().size() == 0) {
                menuRepository.save(Menu.builder().
                        menuId("main")
                        .menuName("메인")
                        .iconName("Home")
                        .url("/")
                        .isAdminCheck(false)
                        .isLoginCheck(false)
                        .isBasicMenu(true)
                        .build());

                menuRepository.save(Menu.builder().
                        menuId("career management")
                        .menuName("이력 관리")
                        .iconName("Profile")
                        .url("/career")
                        .isAdminCheck(false)
                        .isLoginCheck(false)
                        .isBasicMenu(false)
                        .build());

                menuRepository.save(Menu.builder().
                        menuId("career portal")
                        .menuName("이력 포탈")
                        .iconName("Portal")
                        .url("/portal")
                        .isAdminCheck(false)
                        .isLoginCheck(false)
                        .isBasicMenu(true)
                        .build());
            }
            /**
             * RoleMappingMenu에 대한 데이터 초기 세팅
             */

            Role userRole = roleRepository.selectByRoleId("user");
            Role adminRole = roleRepository.selectByRoleId("admin");
            Menu menuMain = menuRepository.selectByMenuId("main");
            Menu careerManagement = menuRepository.selectByMenuId("career management");
            Menu careerPortal = menuRepository.selectByMenuId("career portal");

            if (roleMappingMenuRepository.findAll().size() == 0){
                roleMappingMenuRepository.save(RoleMappingMenu.builder().role(userRole).menu(menuMain).build());
                roleMappingMenuRepository.save(RoleMappingMenu.builder().role(userRole).menu(careerManagement).build());
                roleMappingMenuRepository.save(RoleMappingMenu.builder().role(userRole).menu(careerPortal).build());

                roleMappingMenuRepository.save(RoleMappingMenu.builder().role(adminRole).menu(menuMain).build());
                roleMappingMenuRepository.save(RoleMappingMenu.builder().role(adminRole).menu(careerManagement).build());
                roleMappingMenuRepository.save(RoleMappingMenu.builder().role(adminRole).menu(careerPortal).build());
            }
        });
    }
}
