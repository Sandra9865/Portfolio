package com.portfolio.backend.profile;

import com.portfolio.backend.model.Profile;
import com.portfolio.backend.user.User;
import com.portfolio.backend.user.UserRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/me")
public class ProfileController {

    private final UserRepository userRepository;
    private final ProfileRepository profileRepository;

    public ProfileController(UserRepository userRepository,
                             ProfileRepository profileRepository) {
        this.userRepository = userRepository;
        this.profileRepository = profileRepository;
    }

    @GetMapping("/profile")
    public ResponseEntity<ProfileDTO> getMyProfile() {
        User currentUser = getCurrentUser();
        if (currentUser == null) {
            return ResponseEntity.status(401).build();
        }

        Optional<Profile> profileOpt = profileRepository.findByUser(currentUser);

        ProfileDTO dto = new ProfileDTO();

        if (profileOpt.isPresent()) {
            Profile profile = profileOpt.get();
            dto.setFirstName(profile.getFirstName());
            dto.setLastName(profile.getLastName());
            dto.setTitle(profile.getTitle());
            dto.setLocation(profile.getLocation());
            dto.setSummary(profile.getSummary());
            dto.setGithub(profile.getGithub());
            dto.setLinkedin(profile.getLinkedin());
        } else {
            if (currentUser.getFullName() != null) {
                String[] parts = currentUser.getFullName().split(" ", 2);
                dto.setFirstName(parts[0]);
                if (parts.length > 1) {
                    dto.setLastName(parts[1]);
                }
            }
        }

        return ResponseEntity.ok(dto);
    }

    @PutMapping("/profile")
    public ResponseEntity<ProfileDTO> updateMyProfile(@RequestBody ProfileDTO dto) {
        User currentUser = getCurrentUser();
        if (currentUser == null) {
            return ResponseEntity.status(401).build();
        }

        Profile profile = profileRepository
                .findByUser(currentUser)
                .orElseGet(() -> {
                    Profile p = new Profile();
                    p.setUser(currentUser);
                    return p;
                });

        profile.setFirstName(dto.getFirstName());
        profile.setLastName(dto.getLastName());
        profile.setTitle(dto.getTitle());
        profile.setLocation(dto.getLocation());
        profile.setSummary(dto.getSummary());
        profile.setGithub(dto.getGithub());
        profile.setLinkedin(dto.getLinkedin());

        Profile saved = profileRepository.save(profile);

        ProfileDTO result = new ProfileDTO();
        result.setFirstName(saved.getFirstName());
        result.setLastName(saved.getLastName());
        result.setTitle(saved.getTitle());
        result.setLocation(saved.getLocation());
        result.setSummary(saved.getSummary());
        result.setGithub(saved.getGithub());
        result.setLinkedin(saved.getLinkedin());

        return ResponseEntity.ok(result);
    }

    private User getCurrentUser() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth == null || auth.getName() == null) {
            return null;
        }
        String email = auth.getName();
        return userRepository.findByEmail(email).orElse(null);
    }
}
