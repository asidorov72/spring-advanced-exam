package bg.softuni.booknest.model.dto;

import bg.softuni.booknest.model.enums.UserRole;
import jakarta.validation.constraints.*;

public class UserEditRequest {

    @Size(min = 6, max = 50)
    private String firstName;

    @Size(min = 6, max = 50)
    private String lastName;

    @NotBlank(message = "Email is required")
    @Email(message = "Please enter a valid email address")
    @Pattern(
            regexp = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$",
            message = "Email must contain a valid domain, for example user@gmail.com"
    )
    private String email;

    @NotNull(message = "Role is required")
    private UserRole role;

    public String getFirstName() {
        return firstName;
    }

    public UserEditRequest setFirstName(String firstName) {
        this.firstName = firstName;
        return this;
    }

    public String getLastName() {
        return lastName;
    }

    public UserEditRequest setLastName(String lastName) {
        this.lastName = lastName;
        return this;
    }

    public String getEmail() {
        return email;
    }

    public UserEditRequest setEmail(String email) {
        this.email = email;
        return this;
    }

    public UserRole getRole() {
        return role;
    }

    public UserEditRequest setRole(UserRole role) {
        this.role = role;
        return this;
    }
}