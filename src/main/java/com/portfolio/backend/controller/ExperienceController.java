package com.portfolio.backend.controller;

import com.portfolio.backend.model.Experience;
import com.portfolio.backend.repository.ExperienceRepository;
import com.portfolio.backend.user.User;
import com.portfolio.backend.user.UserRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users/{userId}/experiences")
public class ExperienceController {

    private final ExperienceRepository experienceRepository;
    private final UserRepository userRepository;

    public ExperienceController(ExperienceRepository experienceRepository,
                                UserRepository userRepository) {
        this.experienceRepository = experienceRepository;
        this.userRepository = userRepository;
    }

    @GetMapping
    public ResponseEntity<List<Experience>> getExperiences(@PathVariable Long userId) {
        return ResponseEntity.ok(experienceRepository.findByUserId(userId));
    }

    @PostMapping
    public ResponseEntity<?> createExperience(
            @PathVariable Long userId,
            @RequestBody Experience request
    ) {
        User user = userRepository.findById(userId).orElse(null);
        if (user == null) return ResponseEntity.badRequest().body("User not found");

        Experience exp = new Experience();
        exp.setTitle(request.getTitle());
        exp.setCompany(request.getCompany());
        exp.setStartDate(request.getStartDate());
        exp.setEndDate(request.getEndDate());
        exp.setDescription(request.getDescription());
        exp.setUser(user);

        return ResponseEntity.ok(experienceRepository.save(exp));
    }
}
