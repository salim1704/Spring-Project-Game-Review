package com.qa.GameReview.entity;

import java.util.Objects;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table
public class Review {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;

	@NotNull
	@Min(1)
	@Max(10)
	private int rating;

	@Size(min = 0, max = 500, message = "max 500 characters")
	private String review;

	@ManyToOne(optional = true, fetch = FetchType.EAGER)
	@JoinColumn(name = "game_id", referencedColumnName = "id")
	@JsonIgnore
	private Game game;

	public Review() {
		super();

	}

	public Review(int id, @NotNull @Min(1) @Max(10) int rating,
			@Size(min = 0, max = 500, message = "max 500 characters") String review) {
		super();
		this.id = id;
		this.rating = rating;
		this.review = review;
	}

	public Review(@NotNull @Min(1) @Max(10) int rating,
			@Size(min = 0, max = 500, message = "max 500 characters") String review) {
		super();
		this.rating = rating;
		this.review = review;
	}

	public Review(int id, @NotNull @Min(1) @Max(10) int rating,
			@Size(min = 0, max = 500, message = "max 500 characters") String review, Game game) {
		super();
		this.id = id;
		this.rating = rating;
		this.review = review;
		this.game = game;
	}

	public Review(@NotNull @Min(1) @Max(10) int rating,
			@Size(min = 0, max = 500, message = "max 500 characters") String review, Game game) {
		super();
		this.rating = rating;
		this.review = review;
		this.game = game;
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

	public Game getGame() {
		return game;
	}

	public void setGame(Game game) {
		this.game = game;
	}

	@Override
	public String toString() {
		return "Review [id=" + id + ", rating=" + rating + ", review=" + review + ", game=" + game + "]";
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
		Review other = (Review) obj;
		return Objects.equals(game, other.game) && id == other.id && rating == other.rating
				&& Objects.equals(review, other.review);
	}

}
