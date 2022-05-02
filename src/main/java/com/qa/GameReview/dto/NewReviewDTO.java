package com.qa.GameReview.dto;

import java.util.Objects;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class NewReviewDTO {

	private GameDTO game;
	
	@NotNull
	@Min(1)
	@Max(10)
	private int rating;

	@Size(min = 0, max = 500, message = "max 500 characters")
	private String review;

	public NewReviewDTO() {

	}

	public NewReviewDTO(int rating, String review) {
		super();
		this.rating = rating;
		this.review = review;
	}

	public int getRating() {
		return rating;
	}

	public void setRating(int rating) {
		this.rating = rating;
	}

	public String getReview() {
		return review;
	}

	public void setReview(String review) {
		this.review = review;
	}

	public GameDTO getGame() {
		return game;
	}

	public void setGame(GameDTO game) {
		this.game = game;
	}

	@Override
	public String toString() {
		return "NewReviewDTO [game=" + game + ", rating=" + rating + ", review=" + review + "]";
	}

	@Override
	public int hashCode() {
		return Objects.hash(game, rating, review);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		NewReviewDTO other = (NewReviewDTO) obj;
		return Objects.equals(game, other.game) && rating == other.rating && Objects.equals(review, other.review);
	}
	

}
