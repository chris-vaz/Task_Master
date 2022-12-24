package com.chris.mvc.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.chris.mvc.models.Project;
import com.chris.mvc.models.User;

@Repository
public interface ProjectRepository extends CrudRepository<Project, Long> {

	List<Project> findAll();
	Project findByIdIs(Long id);
	List<Project> findAllByUsers(User user);
	List<Project> findByUsersNotContains(User user);
}