package org.example.repository;

import org.example.entity.ReleaseEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ReleaseRepository extends JpaRepository<ReleaseEntity, Long> {
    Optional<ReleaseEntity> findByVersionAndProjectId(String version, Long projectId);
    Optional<List<ReleaseEntity>> findAllByProjectIdOrderByCreationTime(Long projectId);

}
