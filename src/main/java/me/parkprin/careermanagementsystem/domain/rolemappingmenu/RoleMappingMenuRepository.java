package me.parkprin.careermanagementsystem.domain.rolemappingmenu;

import me.parkprin.careermanagementsystem.domain.rolemappinguser.RoleMappingUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface RoleMappingMenuRepository extends JpaRepository<RoleMappingMenu, Long> {

    @Query(value = "SELECT umm.* FROM role_mapping_menu umm WHERE umm.role_id = :roleId", nativeQuery = true)
    List<RoleMappingMenu> selectByRoleId(@Param("roleId") Long roleId);

    @Query(value = "SELECT umm.* FROM role_mapping_menu umm WHERE umm.menuId = :menuId", nativeQuery = true)
    List<RoleMappingMenu> selectByMenuId(@Param("menuId") Long menuId);
}
