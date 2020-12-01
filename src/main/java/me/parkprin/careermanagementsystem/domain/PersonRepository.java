package me.parkprin.careermanagementsystem.domain;

import org.springframework.data.jpa.repository.JpaRepository;

public interface PersonRepository extends JpaRepository<Person, User> {
}
