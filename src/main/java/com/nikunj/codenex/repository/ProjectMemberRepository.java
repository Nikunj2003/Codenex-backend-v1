package com.nikunj.codenex.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.nikunj.codenex.entity.ProjectMember;
import com.nikunj.codenex.entity.ProjectMemberId;
import com.nikunj.codenex.enums.ProjectRole;

@Repository
public interface ProjectMemberRepository extends JpaRepository<ProjectMember, ProjectMemberId> {

    List<ProjectMember> findByProjectId(Long projectId);

    List<ProjectMember> findByUserIdAndAcceptedAtIsNull(Long userId);

    Optional<ProjectMember> findByProjectIdAndProjectRole(Long projectId, ProjectRole projectRole);

    @Query("""
            SELECT pm FROM ProjectMember pm
            JOIN FETCH pm.user
            WHERE pm.project.id = :projectId
            AND pm.projectRole = 'OWNER'
            """)
    Optional<ProjectMember> findOwnerByProjectId(@Param("projectId") Long projectId);

    @Query("""
            SELECT CASE WHEN COUNT(pm) > 0 THEN true ELSE false END
            FROM ProjectMember pm
            WHERE pm.project.id = :projectId
            AND pm.user.id = :userId
            AND pm.projectRole = 'OWNER'
            """)
    boolean isOwner(@Param("projectId") Long projectId, @Param("userId") Long userId);

}
