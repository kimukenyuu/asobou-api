RENAME TABLE group_members TO group_memberships;

ALTER TABLE group_memberships
    DROP FOREIGN KEY fk_group_members_group,
    DROP FOREIGN KEY fk_group_members_user,
    DROP CHECK chk_group_members_role,
    RENAME INDEX uk_group_members_group_user TO uk_group_memberships_group_user,
    ADD CONSTRAINT fk_group_memberships_group
        FOREIGN KEY (group_id) REFERENCES asobou_groups (id),
    ADD CONSTRAINT fk_group_memberships_user
        FOREIGN KEY (user_id) REFERENCES users (id),
    ADD CONSTRAINT chk_group_memberships_role
        CHECK (role IN ('OWNER', 'ADMIN', 'MEMBER'));
