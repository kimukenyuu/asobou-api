package io.github.kimukenyuu.asobou.group.presentation;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;

public record CreateGroupRequest(
        @NotNull @Positive
        @Schema(description = "User creating the group", example = "1")
        Long creatorId,

        @NotBlank @Size(max = 100)
        @Pattern(regexp = "^[\\p{L}\\p{M}\\p{N}_-]*$")
        @Schema(
                description = "Group display name",
                example = "Playtown"
        )
        String name,

        @Size(max = 2000)
        @Schema(example = "A group for having fun with friends")
        String description
) {
}
