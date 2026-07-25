package io.github.kimukenyuu.asobou.user.domain;

public class UserAlreadyExistsException extends RuntimeException {

    public UserAlreadyExistsException(String fieldName) {
        super("User with the same " + fieldName + " already exists");
    }
}