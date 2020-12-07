package me.parkprin.careermanagementsystem.domain.user;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface UserRepository extends JpaRepository<User, Long> {

    @Query(value = "SELECT u.* FROM user u WHERE u.user_id = :userId ", nativeQuery = true)
    User selectByUserId(@Param("userId") String userId);
}
