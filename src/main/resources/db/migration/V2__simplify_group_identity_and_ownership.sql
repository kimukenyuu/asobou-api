ALTER TABLE asobou_groups
    DROP FOREIGN KEY fk_groups_owner,
    DROP INDEX idx_groups_tag_name,
    DROP COLUMN tag_name,
    DROP COLUMN owner_id;

ALTER TABLE group_members
    ADD CONSTRAINT chk_group_members_role
        CHECK (role IN ('OWNER', 'ADMIN', 'MEMBER'));
