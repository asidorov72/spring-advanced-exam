package bg.softuni.booknest.model.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class UserLoginRequest {

    @NotBlank
    @Size(min = 3, max = 20)
    private String username;

    @NotBlank
    @Size(min = 6, max = 50)
    private String password;

    public String getUsername() {
        return username;
    }

    public UserLoginRequest setUsername(String username) {
        this.username = username;
        return this;
    }

    public String getPassword() {
        return password;
    }

    public UserLoginRequest setPassword(String password) {
        this.password = password;
        return this;
    }
}