package com.example.demo;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.web.server.LocalServerPort;

import com.example.demo.models.User;
import com.example.demo.repositories.FileRepository;
import com.example.demo.repositories.RpgRepository;
import com.example.demo.repositories.ScenarioRepository;
import com.example.demo.repositories.UserRepository;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ScenarioTests {
	
	@LocalServerPort
	private int port;

	@Autowired
	private RpgRepository rpgRepository;

	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private ScenarioRepository scenarioRepository;
	
	@Autowired
	private FileRepository fileRepository;
	
	private static User user = null;

}
