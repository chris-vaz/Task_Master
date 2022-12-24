package com.chris.mvc.controllers;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;

import com.chris.mvc.models.Project;
import com.chris.mvc.models.Task;
import com.chris.mvc.models.User;
import com.chris.mvc.services.ProjectService;
import com.chris.mvc.services.TaskService;
import com.chris.mvc.services.UserService;

@Controller
public class ProjectController {

	@Autowired
	ProjectService projectService;
	
	@Autowired
	UserService userService;
	
	@Autowired
	TaskService taskService;
	
	@GetMapping("/projects/new")
	public String projectForm(@ModelAttribute("newProject") Project newProject, HttpSession session ) {
		if(session.getAttribute("userId") == null) {
			return "redirect:/logout";
		}
		return "newProject.jsp";
	}
	
	@PostMapping("/projects/new")
	public String newProject(@Valid @ModelAttribute("newProject") Project newProject, BindingResult result, Model model, 
			HttpSession session) {
		if(session.getAttribute("userId") == null) {
			return "redirect:/logout";
		}
		
		Long userId = (Long) session.getAttribute("userId");
		
		if (result.hasErrors()) {
			return "newProject.jsp";
		}
		else {
			User user = userService.findById(userId);
			newProject.setLead(user);
			projectService.createProject(newProject);
			user.getProjects().add(newProject);
			userService.updateUser(user);
			return "redirect:/dashboard";
		}
	}
	
	@GetMapping("/projects/{id}")
	public String oneProject(@PathVariable("id") Long id, Model model, HttpSession session) {
		if(session.getAttribute("userId") == null) {
			return "redirect:/logout";
		}
		model.addAttribute("project", projectService.oneProject(id));
		return "oneProject.jsp";
	}
	
	@DeleteMapping("/projects/delete/{id}")
	public String deleteProject(@PathVariable("id") Long id, HttpSession session, Model model) {
		Long userId = (Long) session.getAttribute("userId");
		User user = userService.findById(userId);
		
//		Project project = projectService.oneProject(id);
		
		// Makes sure all tasks are deleted first
		for(Task t: projectService.oneProject(id).getTasks() ) {
			taskService.deleteTask(t.getId());
		}
		
		// Logic on deleting after tasks are gone
		projectService.deleteProject(id);
		model.addAttribute("unassignedProjects", projectService.getUnassignedUsers(user));
		model.addAttribute("assignedProjects", projectService.getAssignedUsers(user));
		return "redirect:/dashboard";
	}
	
	@GetMapping("/projects/edit/{id}")
	public String editForm(@PathVariable("id") Long id, HttpSession session, Model model) {
		if(session.getAttribute("userId") == null) {
			return "redirect:/logout";
		}
		Project project = projectService.oneProject(id);
		model.addAttribute("editProject", project);
		if (!project.getLead().getId().equals((Long) session.getAttribute("userId"))) {
			return "redirect:/dashboard";
		}
		
		return "editProject.jsp";
	}
	
	@PutMapping("projects/edit/{id}")
	public String editProject(@Valid @ModelAttribute("editProject") Project editProject, @PathVariable("id") Long id,
			BindingResult result, HttpSession session, Model model) {
		
		Long userId = (Long) session.getAttribute("userId");
		
		User user = userService.findById(userId);
		
		if (result.hasErrors()) {
			return "editProject.jsp";
		}
		else {
			Project project = projectService.oneProject(id);
			editProject.setUsers(project.getUsers());
			editProject.setLead(user);
			projectService.updateProject(editProject);
			return "redirect:/dashboard";
		}
	}
	
	@PutMapping("/dashboard/join/{id}")
	public String join(@PathVariable("id") Long id, HttpSession session, Model model) {
		if(session.getAttribute("userId") == null) {
			return "redirect:/logout";
		}
		
		Long userId = (Long) session.getAttribute("userId");
		
		Project project = projectService.oneProject(id);
		User user = userService.findById(userId);
		
		user.getProjects().add(project);
		userService.updateUser(user);
		
		model.addAttribute("user", userService.findById(userId));
		model.addAttribute("unassignedProjects", projectService.getUnassignedUsers(user));
		model.addAttribute("assignedProjects", projectService.getAssignedUsers(user));
		
		return "redirect:/dashboard";
	}
	
	@PutMapping("/dashboard/leave/{id}")
	public String leave(@PathVariable("id") Long id, HttpSession session, Model model) {
		if(session.getAttribute("userId") == null) {
			return "redirect:/logout";
		}
		
		Long userId = (Long) session.getAttribute("userId");
		
		Project project = projectService.oneProject(id);
		User user = userService.findById(userId);
		
		user.getProjects().remove(project);
		userService.updateUser(user);
		
		model.addAttribute("user", userService.findById(userId));
		model.addAttribute("unassignedProjects", projectService.getUnassignedUsers(user));
		model.addAttribute("assignedProjects", projectService.getAssignedUsers(user));
		
		return "redirect:/dashboard";
	}
	
	@GetMapping("/projects/{id}/tasks")
	public String tasksForm(@PathVariable("id") Long id, HttpSession session, Model model, @ModelAttribute("task") Task task) {
		if(session.getAttribute("userId") == null) {
			return "redirect:/logout";
		}
		
		Project project = projectService.oneProject(id);
		model.addAttribute("project", project);
		model.addAttribute("tasks", taskService.projectTasks(id));
		
		return "task.jsp";
	}
	
	@PostMapping("/projects/{id}/tasks")
	public String addTask(@Valid @ModelAttribute("task") Task task, BindingResult result, HttpSession session, Model model,
			@PathVariable("id") Long id) {
		if(session.getAttribute("userId") == null) {
			return "redirect:/logout";
		}
		
		Long userId = (Long) session.getAttribute("userId");
		Project project = projectService.oneProject(id);
		
		if (result.hasErrors()) {
			model.addAttribute("project", project);
			model.addAttribute("tasks", taskService.projectTasks(id));
			return "task.jsp";
		}
		else {
			Task newTask = new Task(task.getName());
			newTask.setProject(project);
			newTask.setCreator(userService.findById(userId));
			taskService.addTask(newTask);
			return "redirect:/projects/" + id + "/tasks";
		}
	}
}