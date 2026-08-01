package io.github.kimukenyuu.asobou.group.domain;

import java.text.Normalizer;
import java.time.Instant;
import java.util.Objects;

public record Group(
        GroupId id,
        String name,
        String description,
        Instant createdAt
) {

    public Group {
        if (name == null || name.isBlank()) {
            throw new IllegalArgumentException("name must not be blank");
        }
        name = Normalizer.normalize(name, Normalizer.Form.NFC);
        Objects.requireNonNull(createdAt, "createdAt must not be null");
    }

    public static Group create(
            String name,
            String description,
            Instant createdAt
    ) {
        return new Group(null, name, description, createdAt);
    }
}
