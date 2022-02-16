package org.example.repository;

import org.example.entity.ProjectEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.util.List;
import java.util.Optional;

@Repository
public interface ProjectRepository extends JpaRepository<ProjectEntity, Long> {
    Optional<ProjectEntity> findByNameAndDeleted(String name, boolean isDeleted);
    List<ProjectEntity> findAllByCustomerIdAndDeleted(Long customerId, boolean isDeleted);

    @Query(value = "UPDATE projects SET deleted = true WHERE id = :id ; " +
            "UPDATE releases SET deleted = true WHERE project_id = :id ; " +
            "UPDATE tasks SET deleted = true WHERE project_id = :id ;",
            nativeQuery = true)
    @Modifying
    void deleteById(@Param("id")Long id);

    @Query(value = "SELECT COUNT(*) as row_count FROM projects WHERE deleted = false",
    nativeQuery = true)
    Integer getTotalRows();



    @Query(value = "SELECT * FROM projects WHERE deleted = :isDeleted ORDER BY id " +
            "OFFSET :pageStartIndex ROWS FETCH NEXT :pageSize ROWS ONLY;",
            nativeQuery = true)
    List<ProjectEntity> findAllByDeleted(@Param("isDeleted")boolean isDeleted, @Param("pageStartIndex")int pageStartIndex,
                                         @Param("pageSize")int pageSize);
}
