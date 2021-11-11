package org.example.repository;

import org.example.entity.TaskEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TaskRepository extends JpaRepository<TaskEntity, Long>, JpaSpecificationExecutor<TaskEntity> {
    Optional<List<TaskEntity>> findAllByProjectId(Long project_id);
    Optional<TaskEntity> findByName(String name);
}
