package io.github.kimukenyuu.asobou.user.domain;

public class UserNotFoundException extends RuntimeException {

    public UserNotFoundException(UserId userId) {
        super("User not found: " + userId.value());
    }
}
