package org.example.repository;

import org.example.entity.ReleaseEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ReleaseRepository extends JpaRepository<ReleaseEntity, Long> {
    Optional<ReleaseEntity> findByVersionAndProjectIdAndDeleted(String version, Long projectId, boolean isDeleted);
    Optional<List<ReleaseEntity>> findAllByProjectIdAndDeletedOrderByCreationTime(Long projectId, boolean isDeleted);

    @Query(value = "UPDATE releases SET deleted = true WHERE id = :id ; " +
            "UPDATE tasks SET deleted = true WHERE release_id = :id ;",
            nativeQuery = true)
    @Modifying
    void deleteById(@Param("id")Long id);
}
