package com.thoughtworks.sample;

import com.thoughtworks.sample.user.repository.User;
import com.thoughtworks.sample.user.repository.UserRepository;
import com.thoughtworks.sample.version.repository.Version;
import com.thoughtworks.sample.version.repository.VersionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class DataSeeder {

    @Autowired
    private PasswordEncoder passwordEncoder;
    @Bean
    CommandLineRunner initDatabase(VersionRepository versionRepository, UserRepository userRepository) {
        return args -> {
            if (versionRepository.getVersionDetails().isEmpty()) {
                versionRepository.save(new Version(1, "v1"));
            }
            if (userRepository.findByUsername("vijay_admin").isEmpty()) {
                String password = passwordEncoder.encode("Prashanth123");
                userRepository.save(new User("vijay_admin", password));
            }

        };
    }

}
