package io.github.kimukenyuu.asobou.user.application;

import io.github.kimukenyuu.asobou.user.domain.AuthProvider;
import io.github.kimukenyuu.asobou.user.domain.User;
import io.github.kimukenyuu.asobou.user.domain.UserAlreadyExistsException;
import io.github.kimukenyuu.asobou.user.domain.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Clock;

@Service
public class CreateUserService {

    private final UserRepository userRepository;
    private final Clock clock;

    public CreateUserService(
            UserRepository userRepository,
            Clock clock
    ) {
        this.userRepository = userRepository;
        this.clock = clock;
    }

    @Transactional
    public User create(
            String email,
            String username,
            String displayName
    ) {
        if (userRepository.existsByEmail(email)) {
            throw new UserAlreadyExistsException("email");
        }

        if (userRepository.existsByUsername(username)) {
            throw new UserAlreadyExistsException("username");
        }

        User user = User.create(
                email,
                username,
                displayName,
                AuthProvider.LOCAL,
                clock.instant()
        );

        return userRepository.save(user);
    }
}