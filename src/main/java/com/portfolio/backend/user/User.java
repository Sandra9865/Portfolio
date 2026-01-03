package com.portfolio.backend.user;

import jakarta.persistence.*;

@Entity
@Table(name = "users") // pour éviter le mot réservé "user" en SQL
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String email;   // identifiant de connexion

    @Column(nullable = false)
    private String password; // sera stocké encodé (hashé)

    private String fullName; // affichage simple
    private String role = "USER"; // plus tard: ADMIN, etc.

    public User() {}

    public void setId(Long id) {
        this.id = id;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public Long getId() {
        return id;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public String getFullName() {
        return fullName;
    }

    public String getRole() {
        return role;
    }
// Getters & setters (génère-les avec Alt+Insert / Click droit -> Generate)
}
