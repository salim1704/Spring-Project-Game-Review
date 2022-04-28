package com.qa.GameReview.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.qa.GameReview.dto.GameDTO;
import com.qa.GameReview.dto.NewGameDTO;
import com.qa.GameReview.dto.ReviewDTO;
import com.qa.GameReview.service.GameService;

@RestController
@RequestMapping(path = "/game")
@CrossOrigin("*")
public class GameController {

	private GameService gameService;

	@Autowired
	public GameController(GameService gameService) {
		this.gameService = gameService;
	}

	@GetMapping
	public ResponseEntity<List<GameDTO>> getGames() {
		return ResponseEntity.ok(gameService.getGames());
	}

	@GetMapping(path = "/{id}")
	public ResponseEntity<GameDTO> getGame(@PathVariable(name = "id") int id) {
		GameDTO game = gameService.getGame(id);
		return new ResponseEntity<>(game, HttpStatus.OK);
	}

	@GetMapping(path = "/{id}/reviews")
	public ResponseEntity<List<ReviewDTO>> getGameReviews(@PathVariable(name = "id") int gameId) {
		return ResponseEntity.ok(gameService.getGameReviews(gameId));
	}

	@PostMapping
	public ResponseEntity<GameDTO> createGame(@Valid @RequestBody NewGameDTO game) {
		GameDTO newGame = gameService.createGame(game);

		HttpHeaders headers = new HttpHeaders();
		headers.add("Location", "http://localhost:8080/game/" + newGame.getId());

		return new ResponseEntity<>(newGame, headers, HttpStatus.CREATED);
	}

	@PutMapping(path = "/{id}")
	public ResponseEntity<GameDTO> updateGame(@RequestBody NewGameDTO newGameDTO, @PathVariable(name = "id") int id) {
		return ResponseEntity.ok(gameService.updateGame(newGameDTO, id));
	}

	@DeleteMapping(path = "/{id}")
	public ResponseEntity<?> deleteGame(@PathVariable(name = "id") int id) {
		GameDTO deletedGame = gameService.getGame(id);
		gameService.deleteGame(id);
		return ResponseEntity.ok(deletedGame);
	}
}