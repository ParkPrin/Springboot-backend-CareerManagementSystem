package me.parkprin.careermanagementsystem.domain;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface PersonRepository extends JpaRepository<Person, Long> {

    @Query(value = "SELECT p.* FROM person p WHERE p.user_id = :userId", nativeQuery = true)
    Person selectByUserId(@Param("userId") Long userId);
}
