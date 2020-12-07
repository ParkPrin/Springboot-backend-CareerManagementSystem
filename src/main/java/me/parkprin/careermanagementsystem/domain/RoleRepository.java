package me.parkprin.careermanagementsystem.domain;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface RoleRepository extends JpaRepository<Role, Long> {

    @Query(value = "SELECT r.* FROM role r WHERE r.role_id = :roleId ", nativeQuery = true)
    Role selectByRoleId(@Param("roleId") String roleId);
}
