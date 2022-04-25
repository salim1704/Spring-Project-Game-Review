package com.qa.GameReview.dto;

import java.util.Objects;

public class ReviewDTO {
	private int id;
	private int rating;
	private String review;
	private GameDTO gameDTO;

	public ReviewDTO() {
		super();
	}

	public ReviewDTO(int id, int rating, String review) {
		super();
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

	public GameDTO getGameDTO() {
		return gameDTO;
	}

	public void setGameDTO(GameDTO gameDTO) {
		this.gameDTO = gameDTO;
	}

	@Override
	public String toString() {
		return "ReviewDTO [id=" + id + ", rating=" + rating + ", review=" + review + ", gameDTO=" + gameDTO + "]";
	}

	@Override
	public int hashCode() {
		return Objects.hash(gameDTO, id, rating, review);
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
		return Objects.equals(gameDTO, other.gameDTO) && id == other.id && rating == other.rating
				&& Objects.equals(review, other.review);
	}

}
