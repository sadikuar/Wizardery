package com.example.demo;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
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
import com.example.demo.repositories.RpgRepository;
import com.example.demo.utils.Routes;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ApplicationTests {

	@LocalServerPort
	private int port;

	@MockBean
	private RpgRepository rpgRepository;

	@Autowired
	private TestRestTemplate restTemplate;
	
	@BeforeEach
	public void checkRestTemplateNotNull() {
		assertThat(restTemplate).isNotNull();
	}

	@Test
	public void signinShowTest() {
		ResponseEntity<String> entity = this.restTemplate.getForEntity(Routes.SIGNIN, String.class);
		assertThat(entity.getStatusCode()).isEqualTo(HttpStatus.OK);
		assertThat(entity.getBody()).contains("Signin");
	}

	@Test
	public void signupShowTest() {
		ResponseEntity<String> entity = this.restTemplate.getForEntity(Routes.SIGNUP, String.class);
		assertThat(entity.getStatusCode()).isEqualTo(HttpStatus.OK);
		assertThat(entity.getBody()).contains("Signup");
	}

	@Test
	public void createGameShowTest() {
		ResponseEntity<String> entity = this.restTemplate.getForEntity(Routes.RPG_CREATE, String.class);
		assertThat(entity.getStatusCode()).isEqualTo(HttpStatus.OK);
		assertThat(entity.getBody()).contains("Create game");
	}

	@Test
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
			}
		}
	}

	@Test
	public void scenarioCreateShowTest() {
		ResponseEntity<String> entity = this.restTemplate.getForEntity(Routes.SCENARIO_CREATE, String.class);
		assertThat(entity.getStatusCode()).isEqualTo(HttpStatus.OK);
		assertThat(entity.getBody()).contains("Create scenario");
	}

	@Test
	public void scenarioDetailsShowTest() {
		ResponseEntity<String> entity = this.restTemplate.getForEntity(Routes.SCENARIO, String.class);
		assertThat(entity.getStatusCode()).isEqualTo(HttpStatus.OK);
	}
}
