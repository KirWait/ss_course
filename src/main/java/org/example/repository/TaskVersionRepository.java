package org.example.repository;

import org.example.entity.TaskEntity;
import org.example.entity.TaskVersionEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TaskVersionRepository extends JpaRepository<TaskVersionEntity, Long> {
    List<TaskVersionEntity> findAllByTaskOrderById(TaskEntity task);

}
