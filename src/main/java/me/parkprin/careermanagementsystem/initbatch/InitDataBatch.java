package me.parkprin.careermanagementsystem.initbatch;

import me.parkprin.careermanagementsystem.domain.menu.Menu;
import me.parkprin.careermanagementsystem.domain.menu.MenuRepository;
import me.parkprin.careermanagementsystem.domain.role.Role;
import me.parkprin.careermanagementsystem.domain.role.RoleRepository;
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
        });
    }
}
