package com.nikunj.codenex.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.nikunj.codenex.entity.Project;

@Repository
public interface ProjectRepository extends JpaRepository<Project, Long> {

    @Query("""
            SELECT DISTINCT p FROM Project p
            JOIN ProjectMember pm ON pm.project = p
            WHERE p.deletedAt IS NULL
            AND pm.user.id = :userId
            AND pm.acceptedAt IS NOT NULL
            ORDER BY p.updatedAt DESC
            """)
    List<Project> findAllAccessibleByUser(@Param("userId") Long userId);

    @Query("""
            SELECT DISTINCT p FROM Project p
            JOIN ProjectMember pm ON pm.project = p
            WHERE p.id = :projectId
            AND p.deletedAt IS NULL
            AND pm.user.id = :userId
            AND pm.acceptedAt IS NOT NULL
            """)
    Optional<Project> findAccessibleProjectById(@Param("userId") Long userId, @Param("projectId") Long projectId);

}
