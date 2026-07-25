package io.github.kimukenyuu.asobou.user.domain;

import java.time.Instant;
import java.util.Objects;

public class User {

    private final UserId id;
    private final String email;
    private final String username;
    private String displayName;
    private String profileImageUrl;
    private final AuthProvider authProvider;
    private final Instant createdAt;

    private User(
            UserId id,
            String email,
            String username,
            String displayName,
            String profileImageUrl,
            AuthProvider authProvider,
            Instant createdAt
    ) {
        this.id = id;
        this.email = requireText(email, "email");
        this.username = requireText(username, "username");
        this.displayName = requireText(displayName, "displayName");
        this.profileImageUrl = profileImageUrl;
        this.authProvider = Objects.requireNonNull(
                authProvider,
                "authProvider must not be null"
        );
        this.createdAt = Objects.requireNonNull(
                createdAt,
                "createdAt must not be null"
        );
    }

    public static User create(
            String email,
            String username,
            String displayName,
            AuthProvider authProvider,
            Instant createdAt
    ) {
        return new User(
                null,
                email,
                username,
                displayName,
                null,
                authProvider,
                createdAt
        );
    }

    public static User restore(
            UserId id,
            String email,
            String username,
            String displayName,
            String profileImageUrl,
            AuthProvider authProvider,
            Instant createdAt
    ) {
        return new User(
                Objects.requireNonNull(id, "id must not be null"),
                email,
                username,
                displayName,
                profileImageUrl,
                authProvider,
                createdAt
        );
    }

    public void changeDisplayName(String displayName) {
        this.displayName = requireText(displayName, "displayName");
    }

    public void changeProfileImageUrl(String profileImageUrl) {
        this.profileImageUrl = profileImageUrl;
    }

    public UserId id() {
        return id;
    }

    public String email() {
        return email;
    }

    public String username() {
        return username;
    }

    public String displayName() {
        return displayName;
    }

    public String profileImageUrl() {
        return profileImageUrl;
    }

    public AuthProvider authProvider() {
        return authProvider;
    }

    public Instant createdAt() {
        return createdAt;
    }

    private static String requireText(String value, String fieldName) {
        if (value == null || value.isBlank()) {
            throw new IllegalArgumentException(
                    fieldName + " must not be blank"
            );
        }

        return value;
    }
}