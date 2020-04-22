package com.example.demo;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.example.demo.models.Rpg;
import com.example.demo.models.Scenario;
import com.example.demo.models.User;
import com.example.demo.repositories.RpgRepository;
import com.example.demo.repositories.ScenarioRepository;
import com.example.demo.repositories.UserRepository;
import com.example.demo.utils.Routes;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ApplicationTests {

	@LocalServerPort
	private int port;

	@MockBean
	private RpgRepository rpgRepository;

	@MockBean
	private UserRepository userRepository;
	
	@MockBean
	private ScenarioRepository scenarioRepository;

	@Autowired
	private TestRestTemplate restTemplate;

	@BeforeEach
	public void checkRestTemplateNotNull() {
		assertThat(restTemplate).isNotNull();
	}

	@Test
	@DisplayName("Show signin page")
	public void signinShowTest() {
		ResponseEntity<String> entity = this.restTemplate.getForEntity(Routes.SIGNIN, String.class);
		assertThat(entity.getStatusCode()).isEqualTo(HttpStatus.OK);
		assertThat(entity.getBody()).contains("Signin");
	}

	@Test
	@DisplayName("Show signup page")
	public void signupShowTest() {
		ResponseEntity<String> entity = this.restTemplate.getForEntity(Routes.SIGNUP, String.class);
		assertThat(entity.getStatusCode()).isEqualTo(HttpStatus.OK);
		assertThat(entity.getBody()).contains("Signup");
	}

	@Test
	@DisplayName("Show signin page instead of RPG creation page when not authenticated")
	public void createGameShowNotSignedInTest() {
		ResponseEntity<String> entity = this.restTemplate.getForEntity(Routes.RPG_CREATE, String.class);
		assertThat(entity.getStatusCode()).isEqualTo(HttpStatus.OK);
		assertThat(entity.getBody()).contains("Signin");
	}

	@Test
	@DisplayName("Show RPG details page")
	public void rpgDetailsShowTest() {
		assertThat(rpgRepository).isNotNull();

		if (rpgRepository.count() > 0) {
			List<Rpg> listRpg = rpgRepository.findAll();
			assertThat(listRpg).isNotEmpty();

			for (Rpg rpg : listRpg) {
				assertThat(rpg).isNotNull();

				ResponseEntity<String> entity = this.restTemplate.getForEntity(Routes.RPG_DETAILS + rpg.getId(),
						String.class);
				assertThat(entity.getStatusCode()).isEqualTo(HttpStatus.OK);
				assertThat(entity.getBody()).contains(rpg.getName());
				assertThat(entity.getBody()).contains(rpg.getCreator().getUsername());
				assertThat(entity.getBody()).contains(rpg.getDescription());
				assertThat(entity.getBody()).contains(rpg.getRules());
			}
		}
	}

	@Test
	@DisplayName("Show scenario creation page")
	public void scenarioCreateShowTest() {
		assertThat(rpgRepository).isNotNull();
		
		if (rpgRepository.count() > 0) {
			List<Rpg> listRpg = rpgRepository.findAll();
			assertThat(listRpg).isNotEmpty();
			
			for (Rpg rpg : listRpg) {
				ResponseEntity<String> entity = this.restTemplate.getForEntity(Routes.SCENARIO_CREATE + "?rpgId=" + rpg.getId(), String.class);
				assertThat(entity.getStatusCode()).isEqualTo(HttpStatus.OK);
				assertThat(entity.getBody()).contains("Create scenario");
			}
		}
	}

	@Test
	@DisplayName("Show scenario details page")
	public void scenarioDetailsShowTest() {
		assertThat(scenarioRepository).isNotNull();
		
		if (scenarioRepository.count() > 0) {
			List<Scenario> listScenario = scenarioRepository.findAll();
			
			for (Scenario scenario : listScenario) {
				ResponseEntity<String> entity = this.restTemplate.getForEntity(Routes.SCENARIO_DETAILS + scenario.getId(), String.class);
				assertThat(entity.getStatusCode()).isEqualTo(HttpStatus.OK);
				assertThat(entity.getBody()).contains(scenario.getName());
				assertThat(entity.getBody()).contains(scenario.getDescription());
				assertThat(entity.getBody()).contains(scenario.getCreator().getUsername());
				assertThat(entity.getBody()).contains(scenario.getDifficulty());
			}
		}
	}

	@Test
	@DisplayName("Show user details page")
	public void userDetailsShowTest() {
		assertThat(userRepository).isNotNull();

		if (userRepository.count() > 0) {
			List<User> listUser = userRepository.findAll();
			assertThat(listUser).isNotNull();

			for (User user : listUser) {
				assertThat(user).isNotNull();

				ResponseEntity<String> entity = this.restTemplate.getForEntity(Routes.USER_DETAILS + user.getId(),
						String.class);
				assertThat(entity.getStatusCode()).isEqualTo(HttpStatus.OK);
				assertThat(entity.getBody()).contains(user.getUsername());
				assertThat(entity.getBody()).contains(user.getEmail());
			}
		}
	}
}
