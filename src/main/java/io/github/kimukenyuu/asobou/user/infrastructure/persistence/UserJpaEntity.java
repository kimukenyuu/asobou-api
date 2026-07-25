package io.github.kimukenyuu.asobou.user.infrastructure.persistence;

import io.github.kimukenyuu.asobou.user.domain.AuthProvider;
import io.github.kimukenyuu.asobou.user.domain.User;
import io.github.kimukenyuu.asobou.user.domain.UserId;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import java.time.Instant;

@Entity
@Table(name = "users")
class UserJpaEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 255)
    private String email;

    @Column(nullable = false, length = 50)
    private String username;

    @Column(name = "display_name", nullable = false, length = 100)
    private String displayName;

    @Column(name = "profile_image_url", length = 2048)
    private String profileImageUrl;

    @Enumerated(EnumType.STRING)
    @Column(name = "auth_provider", nullable = false, length = 30)
    private AuthProvider authProvider;

    @Column(name = "created_at", nullable = false)
    private Instant createdAt;

    protected UserJpaEntity() {
    }

    private UserJpaEntity(
            Long id,
            String email,
            String username,
            String displayName,
            String profileImageUrl,
            AuthProvider authProvider,
            Instant createdAt
    ) {
        this.id = id;
        this.email = email;
        this.username = username;
        this.displayName = displayName;
        this.profileImageUrl = profileImageUrl;
        this.authProvider = authProvider;
        this.createdAt = createdAt;
    }

    static UserJpaEntity fromDomain(User user) {
        Long id = user.id() == null
                ? null
                : user.id().value();

        return new UserJpaEntity(
                id,
                user.email(),
                user.username(),
                user.displayName(),
                user.profileImageUrl(),
                user.authProvider(),
                user.createdAt()
        );
    }

    User toDomain() {
        return User.restore(
                new UserId(id),
                email,
                username,
                displayName,
                profileImageUrl,
                authProvider,
                createdAt
        );
    }
}
