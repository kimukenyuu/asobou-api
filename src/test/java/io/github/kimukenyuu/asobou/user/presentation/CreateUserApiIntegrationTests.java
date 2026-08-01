package io.github.kimukenyuu.asobou.user.presentation;

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
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;
import org.testcontainers.junit.jupiter.Testcontainers;

@Testcontainers
@Import(TestcontainersConfiguration.class)
@AutoConfigureMockMvc
@SpringBootTest
@Transactional
class CreateUserApiIntegrationTests {

	@Autowired
	private MockMvc mockMvc;

	@Test
	void createsUser() throws Exception {
		mockMvc.perform(post("/api/users")
				.contentType(MediaType.APPLICATION_JSON)
				.content("""
					{
					  "email": "keonwoo@example.com",
					  "username": "keonwoo",
					  "displayName": "Keonwoo"
					}
					"""))
			.andExpect(status().isCreated())
			.andExpect(jsonPath("$.id").isNumber())
			.andExpect(jsonPath("$.email").value("keonwoo@example.com"))
			.andExpect(jsonPath("$.username").value("keonwoo"))
			.andExpect(jsonPath("$.displayName").value("Keonwoo"))
			.andExpect(jsonPath("$.authProvider").value("LOCAL"))
			.andExpect(jsonPath("$.createdAt").exists());
	}

	@Test
	void rejectsDuplicateEmail() throws Exception {
		String request = """
			{
			  "email": "keonwoo@example.com",
			  "username": "keonwoo",
			  "displayName": "Keonwoo"
			}
			""";

		mockMvc.perform(post("/api/users")
				.contentType(MediaType.APPLICATION_JSON)
				.content(request))
			.andExpect(status().isCreated());

		mockMvc.perform(post("/api/users")
				.contentType(MediaType.APPLICATION_JSON)
				.content(request))
			.andExpect(status().isConflict())
			.andExpect(jsonPath("$.code").value("USER_ALREADY_EXISTS"))
			.andExpect(jsonPath("$.detail")
				.value("User with the same email already exists"));
	}

	@Test
	void rejectsInvalidRequest() throws Exception {
		mockMvc.perform(post("/api/users")
				.contentType(MediaType.APPLICATION_JSON)
				.content("""
					{
					  "email": "invalid-email",
					  "username": "",
					  "displayName": ""
					}
					"""))
			.andExpect(status().isBadRequest())
			.andExpect(jsonPath("$.code").value("VALIDATION_FAILED"))
			.andExpect(jsonPath("$.errors.length()").value(3));
	}

	@Test
	void acceptsJapaneseKoreanAndEnglishCharactersInUsername() throws Exception {
		mockMvc.perform(post("/api/users")
				.contentType(MediaType.APPLICATION_JSON)
				.content("""
					{
					  "email": "multilingual@example.com",
					  "username": "遊ぼうPlay같이",
					  "displayName": "Multilingual User"
					}
					"""))
			.andExpect(status().isCreated())
			.andExpect(jsonPath("$.username").value("遊ぼうPlay같이"));
	}

	@Test
	void rejectsAllAsReservedUsername() throws Exception {
		mockMvc.perform(post("/api/users")
				.contentType(MediaType.APPLICATION_JSON)
				.content("""
					{
					  "email": "all@example.com",
					  "username": "ALL",
					  "displayName": "All"
					}
					"""))
			.andExpect(status().isBadRequest())
			.andExpect(jsonPath("$.code").value("VALIDATION_FAILED"))
			.andExpect(jsonPath("$.errors[0].field").value("username"));
	}
}
