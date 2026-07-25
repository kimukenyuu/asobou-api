package io.github.kimukenyuu.asobou.user.domain;

import java.util.Optional;

public interface UserRepository {

    User save(User user);

    Optional<User> findById(UserId userId);

    boolean existsByEmail(String email);

    boolean existsByUsername(String username);
}