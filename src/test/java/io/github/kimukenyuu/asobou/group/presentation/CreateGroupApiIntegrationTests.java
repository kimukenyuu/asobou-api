package io.github.kimukenyuu.asobou.group.presentation;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import io.github.kimukenyuu.asobou.TestcontainersConfiguration;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;
import org.testcontainers.junit.jupiter.Testcontainers;

@Testcontainers
@Import(TestcontainersConfiguration.class)
@AutoConfigureMockMvc
@SpringBootTest
@Transactional
class CreateGroupApiIntegrationTests {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Test
    void createsGroupAndRegistersCreatorAsOwner() throws Exception {
        long creatorId = createUser("owner@example.com", "プレイヤー");

        mockMvc.perform(post("/api/groups")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                  "creatorId": %d,
                                  "name": "Playtown",
                                  "description": "A group for having fun with friends"
                                }
                                """.formatted(creatorId)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").isNumber())
                .andExpect(jsonPath("$.name").value("Playtown"))
                .andExpect(jsonPath("$.creatorId").value(creatorId))
                .andExpect(jsonPath("$.role").value("OWNER"));

        Integer ownerMemberships = jdbcTemplate.queryForObject(
                "SELECT COUNT(*) FROM group_memberships WHERE user_id = ? AND role = 'OWNER'",
                Integer.class,
                creatorId
        );
        org.assertj.core.api.Assertions.assertThat(ownerMemberships).isEqualTo(1);
    }

    @Test
    void allowsDuplicateGroupNames() throws Exception {
        long creatorId = createUser("owner@example.com", "owner");
        String request = """
                {
                  "creatorId": %d,
                  "name": "Playtown"
                }
                """.formatted(creatorId);

        mockMvc.perform(post("/api/groups").contentType(MediaType.APPLICATION_JSON).content(request))
                .andExpect(status().isCreated());

        mockMvc.perform(post("/api/groups").contentType(MediaType.APPLICATION_JSON).content(request))
                .andExpect(status().isCreated());
    }

    @Test
    void rejectsUnknownOwner() throws Exception {
        mockMvc.perform(post("/api/groups")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                  "creatorId": 999999,
                                  "name": "Playtown"
                                }
                                """))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.code").value("USER_NOT_FOUND"));
    }

    @Test
    void rejectsInvalidRequest() throws Exception {
        mockMvc.perform(post("/api/groups")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                  "creatorId": 0,
                                  "name": "invalid group name"
                                }
                                """))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.code").value("VALIDATION_FAILED"))
                .andExpect(jsonPath("$.errors.length()").value(2));
    }

    private long createUser(String email, String username) throws Exception {
        String response = mockMvc.perform(post("/api/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                  "email": "%s",
                                  "username": "%s",
                                  "displayName": "Owner"
                                }
                                """.formatted(email, username)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString();

        return new com.fasterxml.jackson.databind.ObjectMapper()
                .readTree(response)
                .get("id")
                .asLong();
    }
}
