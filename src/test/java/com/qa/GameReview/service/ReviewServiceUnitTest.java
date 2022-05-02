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
import com.qa.GameReview.dto.NewReviewDTO;
import com.qa.GameReview.dto.ReviewDTO;
import com.qa.GameReview.dto.UpdateReviewDTO;
import com.qa.GameReview.entity.Game;
import com.qa.GameReview.entity.Review;
import com.qa.GameReview.repository.ReviewRepository;

@ExtendWith(MockitoExtension.class)
public class ReviewServiceUnitTest {

	@Mock // Equivalent to @MockBean in Spring
	private ReviewRepository reviewRepository;

	@Mock
	private ModelMapper modelMapper;

	@InjectMocks // equivalent to using @Autowired
	private ReviewService reviewService;

	private List<Game> games;
	private List<GameDTO> gameDTOs;
	private List<Review> reviews;
	private List<ReviewDTO> reviewDTOs;

	@BeforeEach
	public void init() {
		games = List.of(new Game(1, "God Of War", "Adventure", 18, 2018), new Game(2, "Elden Ring", "RPG", 18, 2022));
		gameDTOs = List.of(new GameDTO(1, "God Of War", "Adventure", 18, 2018),
				new GameDTO(2, "Elden Ring", "RPG", 18, 2022));

		reviews = List.of(new Review(1, 10, "Great Game", games.get(0)), new Review(2, 7, "Decent", games.get(1)));
		reviewDTOs = List.of(new ReviewDTO(1, gameDTOs.get(0), 10, "Great Game"),
				new ReviewDTO(2, gameDTOs.get(1), 7, "Decent"));
	}

	@Test
	public void getAllTest() {
		// Arrange-Act-Assert
		// Arrange: setup the data and components under test
		when(reviewRepository.findAll()).thenReturn(reviews);
		when(modelMapper.map(reviews.get(0), ReviewDTO.class)).thenReturn(reviewDTOs.get(0));
		when(modelMapper.map(reviews.get(1), ReviewDTO.class)).thenReturn(reviewDTOs.get(1));

		// Act: performing the action under test
		List<ReviewDTO> actual = reviewService.getReviews();

		// Assert: validate the action was successful
		assertEquals(reviewDTOs, actual);
		verify(reviewRepository).findAll();
		verify(modelMapper).map(reviews.get(0), ReviewDTO.class);
		verify(modelMapper).map(reviews.get(1), ReviewDTO.class);
	}

	@Test
	public void getByIdTest() {
		// Arrange
		Review review = reviews.get(0);
		ReviewDTO reviewDTO = reviewDTOs.get(0);
		int id = review.getId();

		when(reviewRepository.findById(id)).thenReturn(Optional.of(review));
		when(modelMapper.map(review, ReviewDTO.class)).thenReturn(reviewDTO);

		// Act
		ReviewDTO actual = reviewService.getReview(id);

		// Assert
		assertEquals(reviewDTO, actual);
		verify(reviewRepository).findById(id);
		verify(modelMapper).map(review, ReviewDTO.class);
	}

	@Test
	public void getByInvalidIdTest() {
		// Arrange
		int id = 328993;
		when(reviewRepository.findById(id)).thenReturn(Optional.empty());

		// Act
		EntityNotFoundException exception = Assertions.assertThrows(EntityNotFoundException.class, () -> {
			reviewService.getReview(id);
		});

		// Assert
		String expectedMessage = "Review with id " + id + " not found";
		assertEquals(expectedMessage, exception.getMessage());
		verify(reviewRepository).findById(id);
	}

	@Test
	public void createTest() {
		// Arrange
		GameDTO gameDTO = gameDTOs.get(0);
		Review review = reviews.get(0);
		Review newReview = new Review(review.getRating(), review.getReview());
		ReviewDTO expected = reviewDTOs.get(0);

		NewReviewDTO reviewDTO = new NewReviewDTO(review.getRating(), review.getReview());
		ReviewDTO createdReviewDTO = new ReviewDTO(review.getId(), gameDTO, review.getRating(), review.getReview());
				

		when(modelMapper.map(reviewDTO, Review.class)).thenReturn(newReview);
		when(reviewRepository.save(newReview)).thenReturn(review);
		when(modelMapper.map(review, ReviewDTO.class)).thenReturn(createdReviewDTO);

		// Act
		ReviewDTO actual = reviewService.createReview(reviewDTO);

		// Assert
		assertEquals(expected, actual);
		verify(modelMapper).map(reviewDTO, Review.class);
		verify(reviewRepository).save(newReview);
		verify(modelMapper).map(review, ReviewDTO.class);
	}
	
	@Test
	public void updateTest() {
		GameDTO gameDTO = gameDTOs.get(0);
		Review review = reviews.get(0);
		
		int id = review.getId();
		UpdateReviewDTO updateReviewDTO = new UpdateReviewDTO(review.getRating(), review.getReview());
		ReviewDTO expected = new ReviewDTO(review.getId(),gameDTO,review.getRating(),review.getReview());

		when(reviewRepository.existsById(id)).thenReturn(true);
		when(reviewRepository.getById(id)).thenReturn(review);
		when(reviewRepository.save(review)).thenReturn(review);
		when(modelMapper.map(review, ReviewDTO.class)).thenReturn(expected);

		ReviewDTO updated = reviewService.updateReview(updateReviewDTO, id);

		assertEquals(expected, updated);
		verify(reviewRepository).existsById(id);
		verify(reviewRepository).getById(id);
		verify(reviewRepository).save(review);
		verify(modelMapper).map(review, ReviewDTO.class);

	}
	
}
