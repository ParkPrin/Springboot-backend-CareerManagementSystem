package me.parkprin.careermanagementsystem.domain.rolemappinguser;

import me.parkprin.careermanagementsystem.domain.rolemappinguser.RoleMappingUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface RoleMappingUserRepository extends JpaRepository<RoleMappingUser, Long> {

    @Query(value = "SELECT ump.* FROM role_mapping_user ump WHERE ump.user_id = :userId", nativeQuery = true)
    List<RoleMappingUser> selectByUserId(@Param("userId") Long userId);

    @Query(value = "SELECT ump.* FROM role_mapping_user ump WHERE ump.role_id = :roleId", nativeQuery = true)
    List<RoleMappingUser> selectByRoleId(@Param("roleId") Long roleId);
}
