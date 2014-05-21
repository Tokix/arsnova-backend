package de.thm.arsnova.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import de.thm.arsnova.services.StubUserService;

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration(locations = {
		"file:src/main/webapp/WEB-INF/spring/arsnova-servlet.xml",
		"file:src/main/webapp/WEB-INF/spring/spring-main.xml",
		"file:src/main/webapp/WEB-INF/spring/spring-security.xml",
		"file:src/test/resources/test-config.xml",
		"file:src/test/resources/test-socketioconfig.xml"
})
public class SessionControllerTest {

	@Autowired
	private StubUserService userService;

	@Autowired
	private SessionController sessionController;

	private MockMvc mockMvc;

	@Autowired
	private WebApplicationContext webApplicationContext;

	@Before
	public void setup() {
		mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
	}

	private void setAuthenticated(boolean isAuthenticated) {
		if (isAuthenticated) {
			List<GrantedAuthority> ga = new ArrayList<GrantedAuthority>();
			UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken("ptsr00", "secret", ga);
			SecurityContextHolder.getContext().setAuthentication(token);
		} else {
			SecurityContextHolder.clearContext();
		}
		userService.setUserAuthenticated(isAuthenticated);
	}

	@Test
	public void testShouldNotGetUnknownSession() throws Exception {
		setAuthenticated(true);

		mockMvc.perform(get("/session/00000000"))
		.andExpect(status().isNotFound());
	}

	@Test
	public void testShouldNotGetUnknownSessionIfUnauthorized() throws Exception {
		setAuthenticated(false);

		mockMvc.perform(get("/session/00000000"))
		.andExpect(status().isUnauthorized());
	}

	@Test
	public void testShouldCreateSessionIfUnauthorized() throws Exception {
		setAuthenticated(false);

		mockMvc.perform(post("/session/").contentType(MediaType.APPLICATION_JSON).content("{\"keyword\":12345678}"))
		.andExpect(status().isUnauthorized());
	}

	@Test
	public void testShouldNotReturnMySessionsIfUnauthorized() throws Exception {
		setAuthenticated(false);

		mockMvc.perform(get("/session/").param("ownedonly", "true"))
		.andExpect(status().isUnauthorized());
	}

	@Test
	public void testShouldNotReturnMyVisitedSessionsIfUnauthorized() throws Exception {
		setAuthenticated(false);

		mockMvc.perform(get("/session/").param("visitedonly", "true"))
		.andExpect(status().isUnauthorized());
	}

	@Test
	public void testShouldShowUnimplementedIfNoFlagIsSet() throws Exception {
		setAuthenticated(false);

		mockMvc.perform(get("/session/"))
		.andExpect(status().isNotImplemented());
	}
}
