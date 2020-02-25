package com.corelogic.cockroachdb.demo;

import org.junit.jupiter.api.Test;
import org.springframework.transaction.annotation.Transactional;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrlPattern;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Transactional
class DemoApplicationTests extends IntegrationTest {

	@Test
	void getUsers_returnsListOfUsers() throws Exception {
		mockMvc.perform(get("/users"))
			.andExpect(status().isOk())
			.andExpect(jsonPath("$._embedded.users[*].username", hasItem("testuser")))
			.andExpect(jsonPath("$._embedded.users[*].firstName", hasItem("William")))
			.andExpect(jsonPath("$._embedded.users[*].lastName", hasItem("Wallace")))
			.andExpect(jsonPath("$._embedded.users[*].active", hasItem(false)))
			.andExpect(jsonPath("$._embedded.users[*].userRole", hasItem("USER")));
	}

	@Test
	public void createUser_returnsStatusCreatedAndRedirectedToUser() throws Exception {
		mockMvc.perform(post("/users")
			.contentType(APPLICATION_JSON)
			.content("{\n"
				+ "  \"username\": \"cockroachdbuser\",\n"
				+ "  \"firstName\": \"CockroachDB\",\n"
				+ "  \"lastName\": \"User\"\n"
				+ "}"))
			.andExpect(status().isCreated())
			.andExpect(redirectedUrlPattern("http://localhost/users/*"))
			.andExpect(jsonPath("$.username", equalTo("cockroachdbuser")))
			.andExpect(jsonPath("$.firstName", equalTo("CockroachDB")))
			.andExpect(jsonPath("$.lastName", equalTo("User")))
			.andExpect(jsonPath("$.active", equalTo(false)))
			.andExpect(jsonPath("$.userRole", equalTo("USER")));
	}
}
