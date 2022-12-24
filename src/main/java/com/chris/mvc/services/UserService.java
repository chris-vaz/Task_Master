package com.chris.mvc.services;

import java.util.List;
import java.util.Optional;

import org.mindrot.jbcrypt.BCrypt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;

import com.chris.mvc.models.LoginUser;
import com.chris.mvc.models.Project;
import com.chris.mvc.models.User;
import com.chris.mvc.repository.UserRepository;

@Service
public class UserService {

	@Autowired
	UserRepository userRepo;
	
    // TO-DO: Write register and login methods!
    public User register(User newUser, BindingResult result) {
    	// TO-DO - Reject values or register if no errors:
        Optional<User> potentialUser = userRepo.findByEmail(newUser.getEmail());
        
        // Reject if email is taken (present in database)
    	if (potentialUser.isPresent()) {
    		result.rejectValue("email", "Matches", "An account with this email already exists!");
    	}
        // Reject if password doesn't match confirmation
        if (!newUser.getPassword().equals(newUser.getConfirm())) {
        	result.rejectValue("confirm", "Mathces", "The confirm password must match password");
        }
        // Return null if result has errors
        if (result.hasErrors()) {
        	return null;
        }
        // Hash and set password, save user to database
        String hashed = BCrypt.hashpw(newUser.getPassword(), BCrypt.gensalt());
        newUser.setPassword(hashed);
        return userRepo.save(newUser);
    }
    public User login(LoginUser newLogin, BindingResult result) {
        // TO-DO - Reject values:
    	// Find user in the DB by email
    	Optional<User> potentialUser = userRepo.findByEmail(newLogin.getEmail());
        // Reject if NOT present
        if (!potentialUser.isPresent()) {
        	result.rejectValue("email", "Matches", "User not found");
        	return null;
        }
        User user = potentialUser.get();
        // Reject if BCrypt password match fails
        if(!BCrypt.checkpw(newLogin.getPassword(), user.getPassword())) {
            result.rejectValue("password", "Matches", "Invalid Password!");
        }

        // Return null if result has errors
        if (result.hasErrors()) {
        	return null;
        }
        // Otherwise, return the user object 
        return user;
    }
    
	public List<User> allUsers(){
		return userRepo.findAll();
	}
	
	public User updateUser(User user) {
		return userRepo.save(user);
	}
    
    public User findById(Long Id) {
    	Optional<User> potentialUser = userRepo.findById(Id);
    	if (potentialUser.isPresent()) {
    		return potentialUser.get();
    	}
    	return null;
    }
    
	public List<User> getAssignedProjects(Project project){
		return userRepo.findAllByProjects(project);
	}
	
	public List<User> getunAssignedProjects(Project project){
		return userRepo.findByProjectsNotContains(project);
	}
	
	
}