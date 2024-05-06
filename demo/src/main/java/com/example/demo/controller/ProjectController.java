package com.example.demo.controller;

import com.example.demo.dto.ProjectRequest;
import com.example.demo.model.Project;
import com.example.demo.model.Task;
import com.example.demo.model.User;
import com.example.demo.repository.ProjectRepository;
import com.example.demo.repository.UserRepository;
import com.example.demo.repository.TaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/projects")
public class ProjectController {

    @Autowired
    private ProjectRepository projectRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private TaskRepository taskRepository;

    @PostMapping
    public ResponseEntity<String> createProject(@RequestBody ProjectRequest projectRequest) {
        int userId = projectRequest.getUserId();
        String title = projectRequest.getTitle();
        String description = projectRequest.getDescription();

        User user = userRepository.findById(userId).orElse(null);
        if (user == null) {
            return ResponseEntity.notFound().build();
        }

        Project project = new Project();
        project.setUser(user);
        project.setTitle(title);
        project.setDescription(description);
        project.setCreatedDate(LocalDateTime.now());

        projectRepository.save(project);

        return ResponseEntity.ok("Project created successfully");
    }
    @GetMapping("/user/{userId}")
    public ResponseEntity<List<Project>> getProjectsByUserId(@PathVariable int userId) {
        try {
            List<Project> projects = projectRepository.findByUserId(userId);
            return ResponseEntity.ok(projects);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @GetMapping("/{projectId}") // Updated mapping for getProjectById
    public ResponseEntity<Project> getProjectById(@PathVariable int projectId) {
        Optional<Project> project = projectRepository.findById(projectId);
        return project.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PutMapping("/update/{projectId}") // Updated mapping for updateProjectDetails
    public ResponseEntity<String> updateProjectDetails(@PathVariable int projectId, @RequestBody ProjectRequest projectRequest) {
        Optional<Project> optionalProject = projectRepository.findById(projectId);
        if (optionalProject.isPresent()) {
            Project project = optionalProject.get();
            project.setTitle(projectRequest.getTitle());
            project.setDescription(projectRequest.getDescription());
            projectRepository.save(project);
            return ResponseEntity.ok("Project details updated successfully");
        } else {
            return ResponseEntity.notFound().build();
        }
    }
    @DeleteMapping("/delete/{projectId}")
    public ResponseEntity<String> deleteProjectById(@PathVariable int projectId) {
        try {
            // Fetch tasks associated with the project
            List<Task> tasksToDelete = taskRepository.findByProject_ProjectId(projectId);

            System.out.println("Tasks to delete: " + tasksToDelete);

            // Delete each task
            for (Task task : tasksToDelete) {
                taskRepository.delete(task);
                System.out.println("Deleted task with ID: " + task.getTaskId());
            }

            // Then delete the project
            projectRepository.deleteById(projectId);
            System.out.println("Deleted project with ID: " + projectId);

            return ResponseEntity.ok("Project and associated tasks deleted successfully");
        } catch (Exception e) {
            System.err.println("Error deleting project and associated tasks: " + e.getMessage());
            e.printStackTrace(); // Print the stack trace for detailed error information

            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error deleting project and associated tasks: " + e.getMessage());
        }
    }
    @PutMapping("/updateById/{projectId}")
    public ResponseEntity<String> updateProjectById(
            @PathVariable int projectId,
            @RequestBody Project updatedProject
    ) {
        // Check if the project exists
        if (!projectRepository.existsById(projectId)) {
            return ResponseEntity.notFound().build();
        }

        // Find the existing project by ID
        Project existingProject = projectRepository.findById(projectId).orElse(null);
        if (existingProject == null) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error updating project: Project not found with id: " + projectId);
        }

        // Log the existing project details before update
        System.out.println("Existing Project Details (Before Update):");
        System.out.println("Title: " + existingProject.getTitle());
        System.out.println("Description: " + existingProject.getDescription());

        // Update the project details
        existingProject.setTitle(updatedProject.getTitle());
        existingProject.setDescription(updatedProject.getDescription());

        // Save the updated project
        projectRepository.save(existingProject);

        // Log the updated project details after update
        System.out.println("Existing Project Details (After Update):");
        System.out.println("Title: " + existingProject.getTitle());
        System.out.println("Description: " + existingProject.getDescription());

        return ResponseEntity.ok("Project updated successfully");
    }





}
