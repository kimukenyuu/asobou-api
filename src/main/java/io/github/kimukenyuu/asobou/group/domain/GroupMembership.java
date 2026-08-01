package io.github.kimukenyuu.asobou.group.domain;

import io.github.kimukenyuu.asobou.user.domain.UserId;

import java.time.Instant;
import java.util.Objects;

public record GroupMembership(
        Long id,
        GroupId groupId,
        UserId userId,
        GroupRole role,
        Instant joinedAt
) {

    public GroupMembership {
        Objects.requireNonNull(groupId, "groupId must not be null");
        Objects.requireNonNull(userId, "userId must not be null");
        Objects.requireNonNull(role, "role must not be null");
        Objects.requireNonNull(joinedAt, "joinedAt must not be null");
    }

    public static GroupMembership owner(
            GroupId groupId,
            UserId userId,
            Instant joinedAt
    ) {
        return new GroupMembership(null, groupId, userId, GroupRole.OWNER, joinedAt);
    }
}
