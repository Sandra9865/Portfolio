package com.portfolio.backend.profile;

import com.portfolio.backend.model.Profile;
import com.portfolio.backend.user.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ProfileRepository extends JpaRepository<Profile, Long> {

    Optional<Profile> findByUserId(Long userId);
    Optional<Profile> findByUser(User user);
}
