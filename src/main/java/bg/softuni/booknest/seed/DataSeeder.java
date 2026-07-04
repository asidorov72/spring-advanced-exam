package bg.softuni.booknest.seed;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import java.util.List;

@Component
public class DataSeeder implements CommandLineRunner {

    private final List<Seeder> seeders;

    public DataSeeder(List<Seeder> seeders) {
        this.seeders = seeders;
    }

    @Override
    public void run(String... args) {
        seeders.forEach(Seeder::seed);
    }
}