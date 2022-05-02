package com.qa.GameReview.configuration;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import com.qa.GameReview.entity.Game;
import com.qa.GameReview.entity.Review;
import com.qa.GameReview.repository.GameRepository;
import com.qa.GameReview.repository.ReviewRepository;



@Profile("dev")
@Configuration
public class DevStartupListener implements ApplicationListener<ApplicationReadyEvent> {

	private GameRepository gameRepository;
	private ReviewRepository reviewRepository;

	@Autowired
	public DevStartupListener(GameRepository gameRepository, ReviewRepository reviewRepository) {
		this.gameRepository = gameRepository;
		this.reviewRepository = reviewRepository;
	}

	@Override
	public void onApplicationEvent(ApplicationReadyEvent event) {
		List<Game> games = gameRepository.saveAll(
				List.of(new Game("God Of War", "Adventure", 18, 2018), new Game ("Elden Ring","RPG", 18 , 2022)));

		Game game =  games.stream().filter(g -> g.getTitle().equals("God Of War")).findFirst().orElse(null);
		Game game2 =  games.stream().filter(g -> g.getTitle().equals("Elden Ring")).findFirst().orElse(null);
		List<Review> reviews = reviewRepository.saveAll(List.of(new Review(10,"Best game ive ever played" , game),
				new Review(10, "Outstanding game, so much detail", game2)));

	}

}