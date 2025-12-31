package com.nikunj.codenex.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.nikunj.codenex.entity.ProjectMember;
import com.nikunj.codenex.entity.ProjectMemberId;

@Repository
public interface ProjectMemberRepository extends JpaRepository<ProjectMember, ProjectMemberId> {

    List<ProjectMember> findByProjectId(Long projectId);

    List<ProjectMember> findByUserIdAndAcceptedAtIsNull(Long userId);

    @Query("""
            SELECT pm FROM ProjectMember pm
            WHERE pm.id.projectId = :projectId
            AND pm.projectRole = 'OWNER'
            """)
    Optional<ProjectMember> findOwnerByProjectId(@Param("projectId") Long projectId);

    @Query("""
            SELECT pm FROM ProjectMember pm
            JOIN FETCH pm.user
            WHERE pm.project.id IN :projectIds
            AND pm.projectRole = 'OWNER'
            """)
    List<ProjectMember> findOwnersByProjectIds(@Param("projectIds") List<Long> projectIds);

    @Query("""
            SELECT pm FROM ProjectMember pm
            WHERE pm.id.projectId = :projectId
            AND pm.id.userId = :userId
            AND pm.projectRole = 'OWNER'
            AND pm.acceptedAt IS NOT NULL
            """)
    Optional<ProjectMember> findOwnershipByProjectAndUser(@Param("projectId") Long projectId, @Param("userId") Long userId);

}
