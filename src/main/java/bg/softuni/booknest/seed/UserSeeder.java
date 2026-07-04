package bg.softuni.booknest.seed;

import bg.softuni.booknest.model.entity.User;
import bg.softuni.booknest.model.enums.UserRole;
import bg.softuni.booknest.repository.UserRepository;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Component
@Order(1)
public class UserSeeder implements Seeder {

    private final UserRepository userRepository;
    private static final PasswordEncoder PASSWORD_ENCODER = new BCryptPasswordEncoder();

    public UserSeeder(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public void seed() {
        if (userRepository.count() > 0) {
            return;
        }

        userRepository.save(
                new User()
                        .setUsername("admin")
                        .setPassword(PASSWORD_ENCODER.encode("admin123"))
                        .setFirstName("Alexander")
                        .setLastName("Sidorov")
                        .setEmail("admin@booknest.com")
                        .setProfileImage("https://avatars.githubusercontent.com/u/7371906?s=400&v=4")
                        .setRole(UserRole.ADMIN)
        );

        userRepository.save(
                new User()
                        .setUsername("user")
                        .setPassword(PASSWORD_ENCODER.encode("user123"))
                        .setFirstName("Johnny")
                        .setLastName("English")
                        .setEmail("user@booknest.com")
                        .setProfileImage("https://i.pravatar.cc/300?img=15")
                        .setRole(UserRole.USER)
        );
    }

}