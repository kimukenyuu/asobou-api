package io.github.kimukenyuu.asobou.user.persistence;

import org.springframework.data.jpa.repository.JpaRepository;

interface UserJpaRepository
        extends JpaRepository<UserJpaEntity, Long> {

    boolean existsByEmail(String email);

    boolean existsByUsername(String username);
}