package io.github.kimukenyuu.asobou.group.infrastructure.persistence;

import io.github.kimukenyuu.asobou.group.domain.Group;
import io.github.kimukenyuu.asobou.group.domain.GroupId;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import java.time.Instant;

@Entity
@Table(name = "asobou_groups")
class GroupJpaEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 100)
    private String name;

    @Column(columnDefinition = "TEXT")
    private String description;

    @Column(name = "created_at", nullable = false)
    private Instant createdAt;

    protected GroupJpaEntity() {
    }

    private GroupJpaEntity(Long id, String name, String description, Instant createdAt) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.createdAt = createdAt;
    }

    static GroupJpaEntity fromDomain(Group group) {
        return new GroupJpaEntity(
                group.id() == null ? null : group.id().value(),
                group.name(), group.description(), group.createdAt()
        );
    }

    Group toDomain() {
        return new Group(new GroupId(id), name, description, createdAt);
    }
}
