package com.portfolio.backend.controller;

import com.portfolio.backend.model.Project;
import com.portfolio.backend.repository.ProjectRepository;
import com.portfolio.backend.user.User;
import com.portfolio.backend.user.UserRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users/{userId}/projects")
public class ProjectController {

    private final ProjectRepository projectRepository;
    private final UserRepository userRepository;

    public ProjectController(ProjectRepository projectRepository,
                             UserRepository userRepository) {
        this.projectRepository = projectRepository;
        this.userRepository = userRepository;
    }

    // 👉 GET: récupérer les projets d'un utilisateur
    @GetMapping
    public ResponseEntity<List<Project>> getProjects(@PathVariable Long userId) {
        List<Project> projects = projectRepository.findByUserId(userId);
        return ResponseEntity.ok(projects);
    }

    // 👉 POST: créer un projet pour un utilisateur
    @PostMapping
    public ResponseEntity<?> createProject(
            @PathVariable Long userId,
            @RequestBody Project request
    ) {
        // vérifier que l'utilisateur existe
        User user = userRepository.findById(userId).orElse(null);
        if (user == null) {
            return ResponseEntity.badRequest().body("Utilisateur introuvable");
        }

        Project project = new Project();
        project.setTitle(request.getTitle());
        project.setDescription(request.getDescription());
        project.setUrl(request.getUrl());
        project.setImageUrl(request.getImageUrl());
        project.setUser(user);

        Project saved = projectRepository.save(project);

        return ResponseEntity.ok(saved);
    }
}
