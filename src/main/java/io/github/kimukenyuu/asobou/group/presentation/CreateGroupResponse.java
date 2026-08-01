package io.github.kimukenyuu.asobou.group.presentation;

import io.github.kimukenyuu.asobou.group.domain.Group;
import io.github.kimukenyuu.asobou.group.domain.GroupRole;

import java.time.Instant;

public record CreateGroupResponse(
        Long id,
        String name,
        String description,
        Long creatorId,
        GroupRole role,
        Instant createdAt
) {

    static CreateGroupResponse from(Group group, Long creatorId) {
        return new CreateGroupResponse(
                group.id().value(), group.name(), group.description(),
                creatorId, GroupRole.OWNER, group.createdAt()
        );
    }
}
