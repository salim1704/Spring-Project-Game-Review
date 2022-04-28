package com.qa.GameReview.service;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.Sql.ExecutionPhase;

import com.qa.GameReview.dto.GameDTO;
import com.qa.GameReview.dto.NewGameDTO;
import com.qa.GameReview.entity.Game;
import com.qa.GameReview.repository.GameRepository;

@Sql(scripts = { "classpath:schema.sql", "classpath:game-data.sql" }, executionPhase = ExecutionPhase.BEFORE_TEST_METHOD)
@ActiveProfiles({ "test" }) // use the application-test.properties file
public class GameServiceIntergrationTest {

	@Autowired
	private GameService gameService;

	@Autowired
	private GameRepository gameRepository; // required to get the test data out of the database

	@Autowired
	private ModelMapper modelMapper;

	private List<Game> savedGames;
	private List<GameDTO> savedGameDTOs = new ArrayList<>();
	private int nextId;

	@BeforeEach
	public void init() {
		savedGames = gameRepository.findAll(); // get the test data that game-data.sql initialised
		savedGames.forEach(game -> savedGameDTOs.add(modelMapper.map(game, GameDTO.class))); // map each Game to a dto
		// for is equivalent to line above
//			for (Game Game : savedGames) {
//				savedGameDTOs.add(modelMapper.map(Game, GameDTO.class));
//			}

		nextId = savedGames.get(savedGames.size() - 1).getId() + 1;
	}

	@Test
	public void getAllGames() {
		assertEquals(savedGameDTOs, gameService.getGames());
	}

	@Test
	public void getGameById() {
		GameDTO expected = savedGameDTOs.get(0);
		GameDTO actual = gameService.getGame(expected.getId());
		assertEquals(expected, actual);
	}

	@Test
	public void createGameTest() {
		NewGameDTO game = new NewGameDTO();
		game.setTitle("Splinter Cell");
		game.setGenre("Stealth Action-Adventure");
		game.setAgeRating(12);
		game.setReleaseYear(2002);

		GameDTO expected = new GameDTO(nextId, game.getTitle(), game.getGenre(), game.getAgeRating(),
				game.getReleaseYear());
		GameDTO actual = gameService.createGame(game);

		assertEquals(expected, actual);
	}

	@Test
	public void updateGameTest() {
		int id = savedGames.get(0).getId();
		NewGameDTO game = new NewGameDTO();
		game.setTitle("Splinter Cell");
		game.setGenre("Stealth Action-Adventure");
		game.setAgeRating(12);
		game.setReleaseYear(2002);

		GameDTO expected = new GameDTO(id, game.getTitle(), game.getGenre(), game.getAgeRating(),
				game.getReleaseYear());
		GameDTO actual = gameService.updateGame(game, id);

		assertEquals(expected, actual);
	}

	@Test
	public void deleteGameTest() {
		int id = savedGames.get(0).getId();
		gameService.deleteGame(id);
		assertEquals(Optional.empty(), gameRepository.findById(id));
	}
}