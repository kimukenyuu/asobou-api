package io.github.kimukenyuu.asobou.group.infrastructure.persistence;

import org.springframework.data.jpa.repository.JpaRepository;

interface GroupJpaRepository extends JpaRepository<GroupJpaEntity, Long> {
}
