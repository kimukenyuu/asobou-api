package io.github.kimukenyuu.asobou.group.infrastructure.persistence;

import io.github.kimukenyuu.asobou.group.domain.GroupMembership;
import io.github.kimukenyuu.asobou.group.domain.GroupMembershipRepository;
import org.springframework.stereotype.Repository;

@Repository
class GroupMembershipRepositoryAdapter implements GroupMembershipRepository {

    private final GroupMembershipJpaRepository jpaRepository;

    GroupMembershipRepositoryAdapter(GroupMembershipJpaRepository jpaRepository) {
        this.jpaRepository = jpaRepository;
    }

    @Override
    public GroupMembership save(GroupMembership membership) {
        return jpaRepository.save(GroupMembershipJpaEntity.fromDomain(membership)).toDomain();
    }
}
