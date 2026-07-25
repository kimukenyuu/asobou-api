package io.github.kimukenyuu.asobou.user.domain;

public record UserId(long value) {

    public UserId {
        if (value <= 0) {
            throw new IllegalArgumentException("UserId must be positive");
        }
    }
}