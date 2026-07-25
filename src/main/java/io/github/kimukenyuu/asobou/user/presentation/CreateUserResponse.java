package io.github.kimukenyuu.asobou.user.presentation;

import io.github.kimukenyuu.asobou.user.domain.AuthProvider;
import io.github.kimukenyuu.asobou.user.domain.User;
import io.swagger.v3.oas.annotations.media.Schema;

import java.time.Instant;

public record CreateUserResponse(

        @Schema(
                description = "User ID",
                example = "1"
        )
        long id,

        @Schema(
                description = "User email address",
                example = "keonwoo@example.com"
        )
        String email,

        @Schema(
                description = "Unique username",
                example = "keonwoo"
        )
        String username,

        @Schema(
                description = "User display name",
                example = "Keonwoo"
        )
        String displayName,

        @Schema(
                description = "Authentication provider",
                example = "LOCAL"
        )
        AuthProvider authProvider,

        @Schema(
                description = "User creation time in UTC",
                example = "2026-07-25T09:00:00Z"
        )
        Instant createdAt
) {

    public static CreateUserResponse from(User user) {
        return new CreateUserResponse(
                user.id().value(),
                user.email(),
                user.username(),
                user.displayName(),
                user.authProvider(),
                user.createdAt()
        );
    }
}
