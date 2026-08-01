package io.github.kimukenyuu.asobou.group.infrastructure.persistence;

import io.github.kimukenyuu.asobou.group.domain.Group;
import io.github.kimukenyuu.asobou.group.domain.GroupRepository;
import org.springframework.stereotype.Repository;

@Repository
class GroupRepositoryAdapter implements GroupRepository {

    private final GroupJpaRepository jpaRepository;

    GroupRepositoryAdapter(GroupJpaRepository jpaRepository) {
        this.jpaRepository = jpaRepository;
    }

    @Override
    public Group save(Group group) {
        return jpaRepository.save(GroupJpaEntity.fromDomain(group)).toDomain();
    }

}
