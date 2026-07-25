package io.github.kimukenyuu.asobou.user.infrastructure.persistence;

import io.github.kimukenyuu.asobou.user.domain.User;
import io.github.kimukenyuu.asobou.user.domain.UserId;
import io.github.kimukenyuu.asobou.user.domain.UserRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
class UserRepositoryAdapter implements UserRepository {

    private final UserJpaRepository jpaRepository;

    UserRepositoryAdapter(UserJpaRepository jpaRepository) {
        this.jpaRepository = jpaRepository;
    }

    @Override
    public User save(User user) {
        UserJpaEntity entity = UserJpaEntity.fromDomain(user);
        UserJpaEntity savedEntity = jpaRepository.save(entity);

        return savedEntity.toDomain();
    }

    @Override
    public Optional<User> findById(UserId userId) {
        return jpaRepository.findById(userId.value())
                .map(UserJpaEntity::toDomain);
    }

    @Override
    public boolean existsByEmail(String email) {
        return jpaRepository.existsByEmail(email);
    }

    @Override
    public boolean existsByUsername(String username) {
        return jpaRepository.existsByUsername(username);
    }
}
