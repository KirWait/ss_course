package org.example.repository;

import org.example.entity.ReleaseEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReleaseRepository extends JpaRepository<ReleaseEntity, Long> {
    ReleaseEntity findByVersionAndProjectId(String version, Long projectId);
    List<ReleaseEntity> findAllByProjectIdOrderByCreationTime(Long projectId);

}
