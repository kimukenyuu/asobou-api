package io.github.kimukenyuu.asobou.group.infrastructure.persistence;

import org.springframework.data.jpa.repository.JpaRepository;

interface GroupMembershipJpaRepository extends JpaRepository<GroupMembershipJpaEntity, Long> {
}
