package io.github.kimukenyuu.asobou.group.application;

import io.github.kimukenyuu.asobou.group.domain.Group;
import io.github.kimukenyuu.asobou.group.domain.GroupMembership;
import io.github.kimukenyuu.asobou.group.domain.GroupMembershipRepository;
import io.github.kimukenyuu.asobou.group.domain.GroupRepository;
import io.github.kimukenyuu.asobou.user.domain.UserId;
import io.github.kimukenyuu.asobou.user.domain.UserNotFoundException;
import io.github.kimukenyuu.asobou.user.domain.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Clock;
import java.time.Instant;

@Service
public class CreateGroupService {

    private final GroupRepository groupRepository;
    private final GroupMembershipRepository membershipRepository;
    private final UserRepository userRepository;
    private final Clock clock;

    public CreateGroupService(
            GroupRepository groupRepository,
            GroupMembershipRepository membershipRepository,
            UserRepository userRepository,
            Clock clock
    ) {
        this.groupRepository = groupRepository;
        this.membershipRepository = membershipRepository;
        this.userRepository = userRepository;
        this.clock = clock;
    }

    @Transactional
    public Group create(
            Long creatorId,
            String name,
            String description
    ) {
        UserId userId = new UserId(creatorId);
        if (userRepository.findById(userId).isEmpty()) {
            throw new UserNotFoundException(userId);
        }

        Instant now = clock.instant();
        Group savedGroup = groupRepository.save(
                Group.create(name, description, now)
        );
        membershipRepository.save(
                GroupMembership.owner(savedGroup.id(), userId, now)
        );

        return savedGroup;
    }
}
