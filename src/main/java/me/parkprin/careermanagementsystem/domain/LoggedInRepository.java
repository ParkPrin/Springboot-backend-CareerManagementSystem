package me.parkprin.careermanagementsystem.domain;

import org.springframework.data.jpa.repository.JpaRepository;

public interface LoggedInRepository extends JpaRepository<LoggedIn, Long> {
}
