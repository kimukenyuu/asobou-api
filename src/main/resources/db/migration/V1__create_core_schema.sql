CREATE TABLE users (
    id BIGINT NOT NULL AUTO_INCREMENT,
    email VARCHAR(255) NOT NULL,
    username VARCHAR(50) NOT NULL,
    display_name VARCHAR(100) NOT NULL,
    profile_image_url VARCHAR(2048) NULL,
    auth_provider VARCHAR(30) NOT NULL,
    created_at TIMESTAMP(6) NOT NULL DEFAULT CURRENT_TIMESTAMP(6),
    PRIMARY KEY (id),
    CONSTRAINT uk_users_email UNIQUE (email),
    CONSTRAINT uk_users_username UNIQUE (username)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- `groups` is a MySQL reserved keyword, so the physical table uses a domain-specific name.
CREATE TABLE asobou_groups (
    id BIGINT NOT NULL AUTO_INCREMENT,
    name VARCHAR(100) NOT NULL,
    tag_name VARCHAR(50) NOT NULL,
    description TEXT NULL,
    owner_id BIGINT NOT NULL,
    created_at TIMESTAMP(6) NOT NULL DEFAULT CURRENT_TIMESTAMP(6),
    PRIMARY KEY (id),
    CONSTRAINT fk_groups_owner FOREIGN KEY (owner_id) REFERENCES users (id),
    INDEX idx_groups_tag_name (tag_name)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

CREATE TABLE group_members (
    id BIGINT NOT NULL AUTO_INCREMENT,
    group_id BIGINT NOT NULL,
    user_id BIGINT NOT NULL,
    role VARCHAR(20) NOT NULL,
    joined_at TIMESTAMP(6) NOT NULL DEFAULT CURRENT_TIMESTAMP(6),
    PRIMARY KEY (id),
    CONSTRAINT fk_group_members_group FOREIGN KEY (group_id) REFERENCES asobou_groups (id),
    CONSTRAINT fk_group_members_user FOREIGN KEY (user_id) REFERENCES users (id),
    CONSTRAINT uk_group_members_group_user UNIQUE (group_id, user_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

CREATE TABLE asobis (
    id BIGINT NOT NULL AUTO_INCREMENT,
    group_id BIGINT NOT NULL,
    creator_id BIGINT NOT NULL,
    title VARCHAR(200) NOT NULL,
    description TEXT NULL,
    category VARCHAR(30) NOT NULL,
    starts_at TIMESTAMP(6) NOT NULL,
    ends_at TIMESTAMP(6) NULL,
    all_day BOOLEAN NOT NULL DEFAULT FALSE,
    time_zone VARCHAR(50) NOT NULL,
    location_type VARCHAR(20) NOT NULL,
    location_name VARCHAR(200) NULL,
    location_detail TEXT NULL,
    online_url VARCHAR(2048) NULL,
    status VARCHAR(20) NOT NULL,
    created_at TIMESTAMP(6) NOT NULL DEFAULT CURRENT_TIMESTAMP(6),
    updated_at TIMESTAMP(6) NOT NULL DEFAULT CURRENT_TIMESTAMP(6) ON UPDATE CURRENT_TIMESTAMP(6),
    PRIMARY KEY (id),
    CONSTRAINT fk_asobis_group FOREIGN KEY (group_id) REFERENCES asobou_groups (id),
    CONSTRAINT fk_asobis_creator FOREIGN KEY (creator_id) REFERENCES users (id),
    INDEX idx_asobis_group_starts_at (group_id, starts_at),
    INDEX idx_asobis_group_category_starts_at (group_id, category, starts_at)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

CREATE TABLE asobi_participants (
    id BIGINT NOT NULL AUTO_INCREMENT,
    asobi_id BIGINT NOT NULL,
    user_id BIGINT NOT NULL,
    source_type VARCHAR(20) NOT NULL,
    response VARCHAR(20) NOT NULL,
    responded_at TIMESTAMP(6) NULL,
    PRIMARY KEY (id),
    CONSTRAINT fk_asobi_participants_asobi FOREIGN KEY (asobi_id) REFERENCES asobis (id),
    CONSTRAINT fk_asobi_participants_user FOREIGN KEY (user_id) REFERENCES users (id),
    CONSTRAINT uk_asobi_participants_asobi_user UNIQUE (asobi_id, user_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

CREATE TABLE asobi_tags (
    id BIGINT NOT NULL AUTO_INCREMENT,
    asobi_id BIGINT NOT NULL,
    tag_type VARCHAR(20) NOT NULL,
    tagged_user_id BIGINT NULL,
    tagged_group_id BIGINT NULL,
    created_at TIMESTAMP(6) NOT NULL DEFAULT CURRENT_TIMESTAMP(6),
    PRIMARY KEY (id),
    CONSTRAINT fk_asobi_tags_asobi FOREIGN KEY (asobi_id) REFERENCES asobis (id),
    CONSTRAINT fk_asobi_tags_user FOREIGN KEY (tagged_user_id) REFERENCES users (id),
    CONSTRAINT fk_asobi_tags_group FOREIGN KEY (tagged_group_id) REFERENCES asobou_groups (id),
    CONSTRAINT chk_asobi_tags_target CHECK (
        (tag_type = 'USER' AND tagged_user_id IS NOT NULL AND tagged_group_id IS NULL)
        OR
        (tag_type = 'GROUP' AND tagged_user_id IS NULL AND tagged_group_id IS NOT NULL)
    )
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
