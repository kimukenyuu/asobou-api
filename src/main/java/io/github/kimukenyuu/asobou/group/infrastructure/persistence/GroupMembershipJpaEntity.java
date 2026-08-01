package io.github.kimukenyuu.asobou.group.infrastructure.persistence;

import io.github.kimukenyuu.asobou.group.domain.GroupId;
import io.github.kimukenyuu.asobou.group.domain.GroupMembership;
import io.github.kimukenyuu.asobou.group.domain.GroupRole;
import io.github.kimukenyuu.asobou.user.domain.UserId;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import java.time.Instant;

@Entity
@Table(name = "group_memberships")
class GroupMembershipJpaEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "group_id", nullable = false)
    private Long groupId;

    @Column(name = "user_id", nullable = false)
    private Long userId;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private GroupRole role;

    @Column(name = "joined_at", nullable = false)
    private Instant joinedAt;

    protected GroupMembershipJpaEntity() {
    }

    private GroupMembershipJpaEntity(Long id, Long groupId, Long userId,
                                     GroupRole role, Instant joinedAt) {
        this.id = id;
        this.groupId = groupId;
        this.userId = userId;
        this.role = role;
        this.joinedAt = joinedAt;
    }

    static GroupMembershipJpaEntity fromDomain(GroupMembership membership) {
        return new GroupMembershipJpaEntity(
                membership.id(), membership.groupId().value(), membership.userId().value(),
                membership.role(), membership.joinedAt()
        );
    }

    GroupMembership toDomain() {
        return new GroupMembership(id, new GroupId(groupId), new UserId(userId), role, joinedAt);
    }
}
