package org.example.repositories;

import org.example.entities.TaskVersionEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TaskVersionRepository extends JpaRepository<TaskVersionEntity, Long> {

}
