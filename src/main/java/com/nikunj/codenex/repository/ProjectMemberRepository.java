package com.nikunj.codenex.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.nikunj.codenex.entity.ProjectMember;
import com.nikunj.codenex.entity.ProjectMemberId;

@Repository
public interface ProjectMemberRepository extends JpaRepository<ProjectMember, ProjectMemberId> {

    List<ProjectMember> findByProjectId(Long projectId);

    List<ProjectMember> findByUserIdAndAcceptedAtIsNull(Long userId);

}
