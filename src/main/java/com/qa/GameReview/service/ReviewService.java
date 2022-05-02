package com.qa.GameReview.service;

import org.springframework.stereotype.Service;

import com.qa.GameReview.dto.NewReviewDTO;
import com.qa.GameReview.dto.ReviewDTO;
import com.qa.GameReview.dto.UpdateReviewDTO;
import com.qa.GameReview.entity.Review;
import com.qa.GameReview.repository.ReviewRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.persistence.EntityNotFoundException;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;

@Service
public class ReviewService {

	private ReviewRepository reviewRepository;
	private ModelMapper modelMapper;

	@Autowired
	public ReviewService(ReviewRepository reviewRepository, ModelMapper modelMapper) {
		super();
		this.reviewRepository = reviewRepository;
		this.modelMapper = modelMapper;
	}

	private ReviewDTO mapToDTO(Review review) {
		return modelMapper.map(review, ReviewDTO.class);
	}

	// Read all Reviews
	public List<ReviewDTO> getReviews() {
		List<Review> reviews = reviewRepository.findAll();
		List<ReviewDTO> DTOs = new ArrayList<>();

		for (Review review : reviews) {
			DTOs.add(this.mapToDTO(review));
		}
		return DTOs;
	}

	// get review by id
	public ReviewDTO getReview(int id) {
		Optional<Review> review = reviewRepository.findById(id);

		if (review.isPresent()) {
			return this.mapToDTO(review.get());
		}
		throw new EntityNotFoundException("Review with id " + id + " not found");
	}

	// get reviews by game id
	public List<ReviewDTO> getReviewsByGameId(int id) {
		List<Review> reviews = reviewRepository.findByGameId(id);
		List<ReviewDTO> DTOs = new ArrayList<>();

		for (Review review : reviews) {
			DTOs.add(this.mapToDTO(review));
		}
		return DTOs;
	}

	// create a review
	public ReviewDTO createReview(NewReviewDTO review) {
		Review save = this.modelMapper.map(review, Review.class);
		Review createdReview = reviewRepository.save(save);
		return this.mapToDTO(createdReview);

	}

	// update review by id
	public ReviewDTO updateReview(UpdateReviewDTO reviewDTO, int id) {
		if (reviewRepository.existsById(id)) {
			Review savedReview = reviewRepository.getById(id);
			savedReview.setRating(reviewDTO.getRating());
			savedReview.setReview(reviewDTO.getReview());
			return this.mapToDTO(reviewRepository.save(savedReview));
		}
		throw new EntityNotFoundException("Review with id " + id + " not found");
	}
	
	// delete review by id
	public void deleteReview(int id) {
		if (reviewRepository.existsById(id)) {
			reviewRepository.deleteById(id);
			return;
		}
		throw new EntityNotFoundException("Review with id " + id + " not found");
	}
	
}
