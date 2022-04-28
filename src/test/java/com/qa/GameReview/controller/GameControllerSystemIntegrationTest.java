package com.qa.GameReview.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.Sql.ExecutionPhase;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.ResultMatcher;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.qa.GameReview.dto.GameDTO;
import com.qa.GameReview.dto.NewGameDTO;
import com.qa.GameReview.entity.Game;
import com.qa.GameReview.repository.GameRepository;

@SpringBootTest
@AutoConfigureMockMvc // configures a bean for sending requests to our API
@ActiveProfiles({ "test" })
@Sql(scripts = { "classpath:schema.sql",
		"classpath:game-data.sql" }, executionPhase = ExecutionPhase.BEFORE_TEST_METHOD)
public class GameControllerSystemIntegrationTest {

	@Autowired
	private MockMvc mockMvc; // we use this for sending the HTTP requests in our test

	@Autowired
	private ObjectMapper objectMapper; // using for serialising and deserialising

	@Autowired
	private ModelMapper modelMapper;

	@Autowired
	private GameRepository gameRepository; // for retrieving test data

	private List<Game> savedGames;
	private List<GameDTO> savedGameDTOs = new ArrayList<>();
	private int nextId;
	private String uri;

	@BeforeEach
	public void init() {
		savedGames = gameRepository.findAll(); // get the test data that user-data.sql initialised
		savedGames.forEach(game -> savedGameDTOs.add(modelMapper.map(game, GameDTO.class))); // map each user to a dto
		nextId = savedGames.get(savedGames.size() - 1).getId() + 1;
		uri = "/game";
	}

	@Test
	public void getAllUsersTest() throws Exception {
		// create a http request builder
		// - when sending a request with mockmvc, we use the path only ("/user") for
		// example. This
		// is because the tests are already aware of the servers URL and port
		MockHttpServletRequestBuilder request = MockMvcRequestBuilders.request(HttpMethod.GET, uri);

		// specify accept header (content type accepted)
		request.accept(MediaType.APPLICATION_JSON);

		// create json string of users for the expected response body
		String games = objectMapper.writeValueAsString(savedGameDTOs);

		// create result matchers for the expected response
		ResultMatcher statusMatcher = MockMvcResultMatchers.status().isOk();
		ResultMatcher contentMatcher = MockMvcResultMatchers.content().json(games);

		// send request and assert results
		mockMvc.perform(request).andExpectAll(statusMatcher, contentMatcher);
	}

	@Test
	public void createUserTest() throws Exception {
		NewGameDTO newGame = new NewGameDTO();
		newGame.setTitle("Fifa 22");
		newGame.setGenre("Sports");
		newGame.setAgeRating(3);
		newGame.setReleaseYear(2021);
		GameDTO expectedGame = new GameDTO(nextId, newGame.getTitle(), newGame.getGenre(), newGame.getAgeRating(),
				newGame.getReleaseYear());

		// create request builder
		var request = MockMvcRequestBuilders.request(HttpMethod.POST, uri);

		// specify the headers
		request.accept(MediaType.APPLICATION_JSON);

		// specify the request body
		request.content(objectMapper.writeValueAsString(newGame));
		request.contentType(MediaType.APPLICATION_JSON);

		// create result matchers
		String expectedBody = objectMapper.writeValueAsString(expectedGame);
		ResultMatcher statusMatcher = MockMvcResultMatchers.status().isCreated();
		ResultMatcher locationMatcher = MockMvcResultMatchers.header().string("Location",
				"http://localhost:8080/game/" + expectedGame.getId());
		ResultMatcher contentMatcher = MockMvcResultMatchers.content().json(expectedBody);

		// perform the request
		mockMvc.perform(request).andExpectAll(statusMatcher, locationMatcher, contentMatcher);
	}

	@Test
	public void deleteGameTest() throws Exception {
		this.mockMvc.perform(MockMvcRequestBuilders.delete("/game/1").contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON)).andExpect(status().isOk());
	}
}