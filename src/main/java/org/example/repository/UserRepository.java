package org.example.repository;

import org.example.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, Long> {
    Optional<UserEntity> findByUsernameAndDeleted(String userName, boolean isDeleted);

    @Query(value ="UPDATE users SET deleted = true, status = 'NOT_ACTIVE' WHERE id = :id ; ",
            nativeQuery = true)
    @Modifying
    void deleteById(@Param("id") Long id);
}
