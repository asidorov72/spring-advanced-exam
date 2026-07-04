package bg.softuni.booknest.model.dto;

import java.time.LocalDateTime;
import java.util.UUID;
import bg.softuni.booknest.model.enums.UserRole;

public class UserDto {

    private UUID id;

    private String firstName;

    private String lastName;

    private String username;

    private String email;

    private String profileImage;

    private UserRole role;

    private LocalDateTime createdOn;

    private LocalDateTime updatedOn;

    public UUID getId() {
        return id;
    }

    public UserDto setId(UUID id) {
        this.id = id;
        return this;
    }

    public String getFirstName() {
        return firstName;
    }

    public UserDto setFirstName(String firstName) {
        this.firstName = firstName;
        return this;
    }

    public String getLastName() {
        return lastName;
    }

    public UserDto setLastName(String lastName) {
        this.lastName = lastName;
        return this;
    }

    public String getUsername() {
        return username;
    }

    public UserDto setUsername(String username) {
        this.username = username;
        return this;
    }

    public String getEmail() {
        return email;
    }

    public UserDto setEmail(String email) {
        this.email = email;
        return this;
    }

    public String getProfileImage() {
        return profileImage;
    }

    public UserDto setProfileImage(String profileImage) {
        this.profileImage = profileImage;
        return this;
    }

    public String getFullName() {
        String first = firstName == null ? "" : firstName;
        String last = lastName == null ? "" : lastName;

        return (first + " " + last).trim();
    }

    public UserRole getRole() {
        return role;
    }

    public UserDto setRole(UserRole role) {
        this.role = role;
        return this;
    }

    public LocalDateTime getCreatedOn() {
        return createdOn;
    }

    public UserDto setCreatedOn(LocalDateTime createdOn) {
        this.createdOn = createdOn;
        return this;
    }

    public LocalDateTime getUpdatedOn() {
        return updatedOn;
    }

    public UserDto setUpdatedOn(LocalDateTime updatedOn) {
        this.updatedOn = updatedOn;
        return this;
    }
}