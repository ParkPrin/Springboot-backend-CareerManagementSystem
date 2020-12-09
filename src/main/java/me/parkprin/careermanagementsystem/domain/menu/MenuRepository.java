package me.parkprin.careermanagementsystem.domain.menu;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface MenuRepository extends JpaRepository<Menu, Long> {

    @Query(value = "SELECT m.* FROM menu m WHERE m.is_basic_menu = 1", nativeQuery = true)
    List<Menu> selectByIsBasicMenu();

    @Query(value = "SELECT m.* FROM menu m WHERE m.menu_id = :menuId ", nativeQuery = true)
    Menu selectByMenuId(@Param("menuId") String menuId);
}
