package org.example.repository;

import org.example.entity.TaskEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TaskRepository extends JpaRepository<TaskEntity, Long>, JpaSpecificationExecutor<TaskEntity> {
    Optional<List<TaskEntity>> findAllByProjectIdAndDeleted(Long project_id, boolean isDeleted);
    Optional<TaskEntity> findByNameAndDeleted(String name, boolean isDeleted);

    @Query(value = "UPDATE tasks SET deleted=true WHERE id = :id ;",
            nativeQuery = true)
    @Modifying
    void deleteById(@Param("id")Long id);



}
