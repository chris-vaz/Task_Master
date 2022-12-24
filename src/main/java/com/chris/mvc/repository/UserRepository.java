package com.chris.mvc.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.chris.mvc.models.Project;
import com.chris.mvc.models.User;

@Repository
public interface UserRepository extends CrudRepository<User, Long> {

	Optional<User> findByEmail(String email);
	User findByIdIs(Long id);
	List<User> findAll();
	List<User> findAllByProjects(Project project);
	List<User> findByProjectsNotContains(Project project);
}