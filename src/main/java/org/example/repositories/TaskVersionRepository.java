package org.example.repositories;

import org.example.entities.TaskEntity;
import org.example.entities.TaskVersionEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TaskVersionRepository extends JpaRepository<TaskVersionEntity, Long> {
    List<TaskVersionEntity> findAllByTaskOrderById(TaskEntity task);

}
