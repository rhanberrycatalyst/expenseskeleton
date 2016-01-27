package com.expensereport.repository;

import com.expensereport.domain.Project;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the Project entity.
 */
public interface ProjectRepository extends JpaRepository<Project,Long> {

    @Query("select project from Project project where project.userid.login = ?#{principal.username}")
    List<Project> findByUseridIsCurrentUser();

}
