package bg.softuni.booknest.mapper;

import bg.softuni.booknest.model.dto.UserDto;
import bg.softuni.booknest.model.entity.User;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {

    public UserDto toDto(User user) {
        return new UserDto()
                .setId(user.getId())
                .setFirstName(user.getFirstName())
                .setLastName(user.getLastName())
                .setUsername(user.getUsername())
                .setEmail(user.getEmail())
                .setProfileImage(user.getProfileImage())
                .setRole(user.getRole())
                .setCreatedOn(user.getCreatedOn())
                .setUpdatedOn(user.getUpdatedOn());
    }
}