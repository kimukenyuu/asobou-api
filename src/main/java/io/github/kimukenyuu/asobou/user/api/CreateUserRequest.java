package io.github.kimukenyuu.asobou.user.api;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record CreateUserRequest(

        @Schema(
                description = "User email address",
                example = "keonwoo@example.com"
        )
        @NotBlank
        @Email
        @Size(max = 255)
        String email,

        @Schema(
                description = "Unique username",
                example = "keonwoo"
        )
        @NotBlank
        @Size(max = 50)
        String username,

        @Schema(
                description = "User display name",
                example = "Keonwoo"
        )
        @NotBlank
        @Size(max = 100)
        String displayName
) {
}
