package com.qa.GameReview.dto;

import java.util.Objects;

public class ReviewDTO {
	private int id;
	private GameDTO game;
	private int rating;
	private String review;

	public ReviewDTO() {
	}

	public ReviewDTO(int id, GameDTO game, int rating, String review) {
		super();
		this.game = game;
		this.id = id;
		this.rating = rating;
		this.review = review;
		
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
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
		return "ReviewDTO [id=" + id + ",  game=" + game + ",rating=" + rating + ", review=" + review + "]";
	}

	@Override
	public int hashCode() {
		return Objects.hash(game, id, rating, review);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ReviewDTO other = (ReviewDTO) obj;
		return Objects.equals(game, other.game) && id == other.id && rating == other.rating
				&& Objects.equals(review, other.review);
	}

}
