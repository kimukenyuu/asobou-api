package io.github.kimukenyuu.asobou.group.domain;

import java.util.Objects;

public record GroupId(Long value) {

    public GroupId {
        Objects.requireNonNull(value, "value must not be null");
    }
}
