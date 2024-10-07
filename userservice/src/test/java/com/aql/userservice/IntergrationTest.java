package com.aql.userservice;

import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.assertj.core.util.Arrays;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.client.RestTemplate;

import com.aql.userservice.data.User;
import com.aql.userservice.data.UserRepository;
import com.google.gson.Gson;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, properties = "application.properties")
public class IntergrationTest {
	@LocalServerPort
	private int port;
	
	private static RestTemplate restTemplate;
	
	@MockBean
	UserRepository userRepository;
	
	Gson gson = new Gson();
	private User user;
	
	private String baseUrl = "http://localhost";
	
	@BeforeAll
	public static void init() {
		restTemplate = new RestTemplate();
	}
	
	@BeforeEach
	void setUp() {
		user = new User(1L, "dev@gmail.com", "123456", "employeeID");
		baseUrl = baseUrl.concat(":").concat(port+"").concat("/api/v1/users");
	}
	
//	@Test
//	void shouldGetAllUser() {
//		List<User> users = new ArrayList<User>();
//		users.add(user);
//		when(userRepository.findAll()).thenReturn(users);
//		HttpHeaders headers = new HttpHeaders();
//		headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
//		HttpEntity<>
//		
//	}
}
