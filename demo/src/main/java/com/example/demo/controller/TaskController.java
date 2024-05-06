package com.example.demo.controller;

import com.example.demo.dto.TaskRequest;
import com.example.demo.model.Project;
import com.example.demo.model.Task;
import com.example.demo.model.User;
import com.example.demo.repository.ProjectRepository;
import com.example.demo.repository.TaskRepository;
import com.example.demo.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.Map;


import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/tasks")
public class TaskController {

    @Autowired
    private TaskRepository taskRepository;

    @Autowired
    private UserRepository userRepository; // Assuming you have a UserRepository

    @Autowired
    private ProjectRepository projectRepository; // Assuming you have a ProjectRepository

    @PostMapping
    public ResponseEntity<String> addTask(@RequestBody TaskRequest taskRequest) {
        try {
            // Fetch user and project entities based on their IDs
            User user = userRepository.findById(taskRequest.getUserId())
                    .orElseThrow(() -> new IllegalArgumentException("User not found with ID: " + taskRequest.getUserId()));

            Project project = projectRepository.findById(taskRequest.getProjectId())
                    .orElseThrow(() -> new IllegalArgumentException("Project not found with ID: " + taskRequest.getProjectId()));

            Task task = new Task();
            task.setUser(user);
            task.setProject(project);
            task.setTitle(taskRequest.getTitle());
            task.setDescription(taskRequest.getDescription());
            task.setCompleted(false); // Assuming new tasks are not completed by default
            task.setCreatedDate(LocalDateTime.now());
            task.setUpdatedDate(LocalDateTime.now());

            taskRepository.save(task);

            return ResponseEntity.ok("Task added successfully");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error adding task: " + e.getMessage());
        }
    }
    @GetMapping("/project")
    public ResponseEntity<List<Task>> getAllTasksByProjectId(@RequestParam int projectId) {
        try {
            // Retrieve all tasks
            List<Task> tasks = taskRepository.findAll();

            // Filter tasks based on projectId
            List<Task> filteredTasks = tasks.stream()
                    .filter(task -> task.getProject() != null && task.getProject().getProjectId() == projectId)
                    .collect(Collectors.toList());

            // Print all projectIds of filtered tasks (optional for debugging)
            for (Task task : filteredTasks) {
                System.out.println("Task projectId: " + task.getProject().getProjectId());
            }

            return ResponseEntity.ok(filteredTasks);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }
    @PutMapping("/{taskId}")
    public ResponseEntity<String> updateTaskCompletion(@PathVariable int taskId, @RequestBody Map<String, Boolean> requestBody) {
        try {
            Optional<Task> optionalTask = taskRepository.findById(taskId);
            if (optionalTask.isPresent()) {
                Task task = optionalTask.get();
                boolean completed = requestBody.get("completed");
                task.setCompleted(completed);
                task.setUpdatedDate(LocalDateTime.now());
                taskRepository.save(task);
                return ResponseEntity.ok("Task completion status updated successfully");
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Task not found with ID: " + taskId);
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error updating task completion status: " + e.getMessage());
        }
    }
    @DeleteMapping("/{taskId}")
    public ResponseEntity<String> deleteTask(@PathVariable int taskId) {
        try {
            Optional<Task> optionalTask = taskRepository.findById(taskId);
            if (optionalTask.isPresent()) {
                taskRepository.deleteById(taskId);
                return ResponseEntity.ok("Task deleted successfully");
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Task not found with ID: " + taskId);
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error deleting task: " + e.getMessage());
        }
    }
    @PutMapping("/{taskId}/details")
    public ResponseEntity<String> updateTaskDetails(@PathVariable int taskId, @RequestBody Map<String, String> request) {
        try {
            Optional<Task> optionalTask = taskRepository.findById(taskId);
            if (optionalTask.isPresent()) {
                Task task = optionalTask.get();
                String title = request.get("title");
                String description = request.get("description");

                // Update task details if title and description are not null
                if (title != null) {
                    task.setTitle(title);
                }
                if (description != null) {
                    task.setDescription(description);
                }

                taskRepository.save(task);
                return ResponseEntity.ok("Task details updated successfully");
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Task not found with ID: " + taskId);
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error updating task details: " + e.getMessage());
        }
    }




}
