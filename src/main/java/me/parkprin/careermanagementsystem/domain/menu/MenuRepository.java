package me.parkprin.careermanagementsystem.domain.menu;

import me.parkprin.careermanagementsystem.domain.rolemappingmenu.RoleMappingMenu;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface MenuRepository extends JpaRepository<Menu, Long> {

    @Query(value = "SELECT m.* FROM menu m WHERE m.is_basic_menu = 1", nativeQuery = true)
    List<Menu> selectByIsBasicMenu();
}
