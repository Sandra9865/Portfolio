package com.portfolio.backend.controller;

import com.portfolio.backend.model.Skill;
import com.portfolio.backend.repository.SkillRepository;
import com.portfolio.backend.user.User;
import com.portfolio.backend.user.UserRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users/{userId}/skills")
public class SkillController {

    private final SkillRepository skillRepository;
    private final UserRepository userRepository;

    public SkillController(SkillRepository skillRepository,
                           UserRepository userRepository) {
        this.skillRepository = skillRepository;
        this.userRepository = userRepository;
    }

    @GetMapping
    public ResponseEntity<List<Skill>> getSkills(@PathVariable Long userId) {
        return ResponseEntity.ok(skillRepository.findByUserId(userId));
    }

    @PostMapping
    public ResponseEntity<?> createSkill(@PathVariable Long userId, @RequestBody Skill request) {
        User user = userRepository.findById(userId).orElse(null);
        if (user == null) return ResponseEntity.badRequest().body("User not found");

        request.setUser(user);
        return ResponseEntity.ok(skillRepository.save(request));
    }
}
