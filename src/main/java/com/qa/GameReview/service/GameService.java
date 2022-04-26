package com.qa.GameReview.service;

import java.util.List;
import java.util.Optional;

import javax.persistence.EntityNotFoundException;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.qa.GameReview.dto.GameDTO;
import com.qa.GameReview.entity.Game;
import com.qa.GameReview.entity.Review;
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
	
	private GameDTO mapToDTO(Game game) {
        return modelMapper.map(game, GameDTO.class);
    }
	public List<Game> readAll() {
		return gameRepository.findAll();
	}

	public Game readById(int id) {
		Optional<Game> game = gameRepository.findById(id);

		if (game.isPresent()) {
			return game.get();
		}
		throw new EntityNotFoundException("Game with id " + id + " was not found");
	}

	public List<Review> readReviewByGameId(int id) {
		Game game = this.readById(id);
		return game.getReviews();
	}

	public Game create(Game game) {
		return gameRepository.save(game);
	}

}