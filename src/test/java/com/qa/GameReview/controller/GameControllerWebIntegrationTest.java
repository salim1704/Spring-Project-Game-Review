package com.qa.GameReview.controller;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.net.URI;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;

import com.qa.GameReview.dto.GameDTO;
import com.qa.GameReview.dto.NewGameDTO;
import com.qa.GameReview.service.GameService;

import static org.junit.jupiter.api.Assertions.*;

@WebMvcTest(GameController.class)
@ActiveProfiles({ "test" })
public class GameControllerWebIntegrationTest {

	@MockBean
	private GameService gameService;

	@Autowired
	private GameController gameController;
	private List<GameDTO> gameDTOs;

	@BeforeEach
	public void init() {
		List.of(new GameDTO(1, "God Of War", "Adventure", 18, 2018), new GameDTO(2, "Elden Ring", "RPG", 18, 2022));
	}

	@Test
	public void getAllGamesTest() {
		// arrange
		ResponseEntity<?> expected = ResponseEntity.ok(gameDTOs);
		when(gameService.getGames()).thenReturn(gameDTOs);

		// act
		ResponseEntity<?> actual = gameController.getGames();

		// assert
		assertEquals(expected, actual);
		verify(gameService).getGames();
	}

	@Test
	public void createGameTest() {
		// arrange
		NewGameDTO newGame = new NewGameDTO();
		newGame.setTitle("Fifa 22");
		newGame.setGenre("Sports");
		newGame.setAgeRating(3);
		newGame.setReleaseYear(2021);
		GameDTO expectedGame = new GameDTO(1, newGame.getTitle(), newGame.getGenre(), newGame.getAgeRating(),
				newGame.getReleaseYear());
		ResponseEntity<?> expected = ResponseEntity
				.created(URI.create("http://localhost:8080/game/" + expectedGame.getId())).body(expectedGame);

		when(gameService.createGame(newGame)).thenReturn(expectedGame);

		// act
		ResponseEntity<?> actual = gameController.createGame(newGame);

		// assert
		assertEquals(expected, actual);
		verify(gameService).createGame(newGame);
	}
}
