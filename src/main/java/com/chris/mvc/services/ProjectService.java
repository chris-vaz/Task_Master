package com.chris.mvc.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.chris.mvc.models.Project;
import com.chris.mvc.models.User;
import com.chris.mvc.repository.ProjectRepository;

@Service
public class ProjectService {
	
	@Autowired
	ProjectRepository projectRepo;
	
//	ALL
	public List<Project> allProjects() {
		return projectRepo.findAll();
	}
	
//	FIND ONE
	public Project oneProject(Long id) {
		Optional<Project> optionalProject = projectRepo.findById(id);
		if (optionalProject.isPresent()) {
			return optionalProject.get();
		}
		else {
			return null;
		}
	}
	
//	CREATE
	public Project createProject(Project project) {
		return projectRepo.save(project);
	}
	
//	UPDATE
	public Project updateProject(Project project) {
		return projectRepo.save(project);
	}
	
//	DELETE
	public void deleteProject(Long id) {
		projectRepo.deleteById(id);
	}
	
//	ASSIGNED
	public List<Project> getAssignedUsers(User user) {
		return projectRepo.findAllByUsers(user);
	}
	
//  UNASSIGNED
	public List<Project> getUnassignedUsers(User user) {
		return projectRepo.findByUsersNotContains(user);
	}
}