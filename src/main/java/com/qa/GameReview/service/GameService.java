package com.qa.GameReview.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.persistence.EntityNotFoundException;
import javax.transaction.Transactional;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.qa.GameReview.dto.GameDTO;
import com.qa.GameReview.dto.NewGameDTO;
import com.qa.GameReview.dto.ReviewDTO;
import com.qa.GameReview.entity.Game;
import com.qa.GameReview.repository.GameRepository;

@Service
public class GameService {

	private GameRepository gameRepository;
	private ReviewService reviewService;
	private ModelMapper modelMapper;

	@Autowired
	public GameService(GameRepository gameRepository, ReviewService reviewService, ModelMapper modelMapper) {
		super();
		this.gameRepository = gameRepository;
		this.reviewService = reviewService;
		this.modelMapper = modelMapper;
	}
	
	public GameDTO mapToDTO(Game game) {
		return this.modelMapper.map(game, GameDTO.class);
	}
	
	public List<GameDTO> getGames() {
		List<Game> games = gameRepository.findAll();
		List<GameDTO> DTOS = new ArrayList<>();
		
		for (Game game : games) {
			DTOS.add(this.mapToDTO(game));
		}
		return DTOS;
	}

	public GameDTO getGame(int id) {
		Optional<Game> game = gameRepository.findById(id);

		if (game.isPresent()) {
			return this.mapToDTO(game.get());
		}
		throw new EntityNotFoundException("Game with id " + id + " not found");
	}

	public GameDTO createGame(NewGameDTO game) {
		Game toSave = this.modelMapper.map(game, Game.class);
		Game newGame = gameRepository.save(toSave);
		return this.mapToDTO(newGame);
	}

	@Transactional
	public GameDTO updateGame(NewGameDTO game, int id) {
		if (gameRepository.existsById(id)) {
			Game savedGame = gameRepository.getById(id);
			savedGame.setTitle(game.getTitle());
			savedGame.setGenre(game.getGenre());
			savedGame.setAgeRating(game.getAgeRating());
			savedGame.setReleaseYear(game.getReleaseYear());
			gameRepository.save(savedGame);
			return this.mapToDTO(gameRepository.save(savedGame));
		}
		throw new EntityNotFoundException("Game with id " + id + " not found");
	}

	public void deleteGame(int id) {
		if (gameRepository.existsById(id)) {
			gameRepository.deleteById(id);
			return;
		}
		throw new EntityNotFoundException("Game with id " + id + " not found");
	}
	
	public List<ReviewDTO> getGameReviews(int gameId) {
		GameDTO game = this.getGame(gameId);
		List<ReviewDTO> reviews = reviewService.getReviewsByGameId(gameId);
		reviews.forEach(review -> review.setGameDTO(game));
		return reviews;
	}
}
