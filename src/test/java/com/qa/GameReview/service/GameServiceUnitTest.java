package com.qa.GameReview.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.List;
import java.util.Optional;

import javax.persistence.EntityNotFoundException;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;

import com.qa.GameReview.dto.GameDTO;
import com.qa.GameReview.dto.NewGameDTO;
import com.qa.GameReview.entity.Game;
import com.qa.GameReview.repository.GameRepository;

@ExtendWith(MockitoExtension.class)
public class GameServiceUnitTest {

	@Mock
	private GameRepository gameRepository;

	@Mock
	private ModelMapper modelMapper;
	
	@InjectMocks
	private GameService gameService;

	private List<Game> games;
	private List<GameDTO> gameDTOs;

	@BeforeEach
	public void init() {
		games = List.of(new Game(1, "God Of War", "Adventure", 18, 2018), new Game(2, "Elden Ring", "RPG", 18, 2022));
		gameDTOs = List.of(new GameDTO(1, "God Of War", "Adventure", 18, 2018), new GameDTO(2, "Elden Ring", "RPG", 18, 2022));
	}

	@Test
	public void getAllTest() {
		// Arrange
		when(gameRepository.findAll()).thenReturn(games);
		when(modelMapper.map(games.get(0), GameDTO.class)).thenReturn(gameDTOs.get(0));
		when(modelMapper.map(games.get(1), GameDTO.class)).thenReturn(gameDTOs.get(1));

		// Act
		List<GameDTO> actual = gameService.getGames();

		// Assert
		assertEquals(gameDTOs, actual);
		verify(gameRepository).findAll();
		verify(modelMapper).map(games.get(0), GameDTO.class);
		verify(modelMapper).map(games.get(1), GameDTO.class);
	}

	@Test
	public void getByIdTest() {
		// Arrange
		Game game = games.get(0);
		GameDTO gameDTO = gameDTOs.get(0);
		int id = game.getId();

		when(gameRepository.findById(id)).thenReturn(Optional.of(game));
		when(modelMapper.map(game, GameDTO.class)).thenReturn(gameDTO);

		// Act
		GameDTO actual = gameService.getGame(id);

		// Assert
		assertEquals(gameDTO, actual);
		verify(gameRepository).findById(id);
		verify(modelMapper).map(game, GameDTO.class);

	}

	@Test
	public void createTest() {
		// Arrange
		Game game = games.get(0);

		NewGameDTO gameDTO = new NewGameDTO();
		gameDTO.setTitle(game.getTitle());
		gameDTO.setGenre(game.getGenre());
		gameDTO.setAgeRating(game.getAgeRating());
		gameDTO.setReleaseYear(game.getReleaseYear());

		GameDTO newGame = new GameDTO(game.getId(), game.getTitle(), game.getGenre(), game.getAgeRating(),
				game.getReleaseYear());

		when(modelMapper.map(gameDTO, Game.class)).thenReturn(game);
		when(gameRepository.save(game)).thenReturn(game);
		when(modelMapper.map(game, GameDTO.class)).thenReturn(newGame);

		// Act
		GameDTO actual = gameService.createGame(gameDTO);

		// Assert
		assertEquals(newGame, actual);
		verify(modelMapper).map(gameDTO, Game.class);
		verify(gameRepository).save(game);
		verify(modelMapper).map(game, GameDTO.class);
	}

	@Test
	public void getByInvalidIdTest() {
		// Arrange
		int id = 1658;
		when(gameRepository.findById(id)).thenReturn(Optional.empty());

		// Act
		EntityNotFoundException exception = Assertions.assertThrows(EntityNotFoundException.class, () -> {
			gameService.getGame(id);
		});

		// Assert
		String expectedMessage = "Game with id " + id + " not found";
		assertEquals(expectedMessage, exception.getMessage());
		verify(gameRepository).findById(id);
	}
	
	@Test
	public void updateTest() {

		Game game = games.get(0);
		int id = game.getId();
		NewGameDTO newGameDTO = new NewGameDTO(game.getTitle(), game.getGenre(),game.getAgeRating(),game.getReleaseYear());
		GameDTO expected = new GameDTO(game.getTitle(), game.getGenre(),game.getAgeRating(),game.getReleaseYear());

		when(gameRepository.existsById(id)).thenReturn(true);
		when(gameRepository.getById(id)).thenReturn(game);
		when(gameRepository.save(game)).thenReturn(game);
		when(modelMapper.map(game, GameDTO.class)).thenReturn(expected);

		GameDTO updated = gameService.updateGame(newGameDTO, id);

		assertEquals(expected, updated);
		verify(gameRepository).existsById(id);
		verify(gameRepository).getById(id);
		verify(gameRepository).save(game);
		verify(modelMapper).map(game, GameDTO.class);

	}

}
