package bg.softuni.booknestadvanced.mapper;

import bg.softuni.booknestadvanced.model.dto.UserDto;
import bg.softuni.booknestadvanced.model.entity.User;
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