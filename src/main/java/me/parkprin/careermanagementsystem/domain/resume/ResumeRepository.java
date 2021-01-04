package me.parkprin.careermanagementsystem.domain.resume;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ResumeRepository extends JpaRepository<Resume, Long> {

    @Query(value = "SELECT r.* FROM resume r WHERE r.user_id = :userId", nativeQuery = true)
    List<Resume> selectByUserId(@Param("userId") Long userId);

    @Query(value = "SELECT r.* FROM resume r WHERE r.image_id = :imageId", nativeQuery = true)
    List<Resume> selectByImageId(@Param("imageId") Long imageId);
}
