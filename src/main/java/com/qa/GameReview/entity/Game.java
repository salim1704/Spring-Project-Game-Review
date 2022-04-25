package com.qa.GameReview.entity;

import java.util.List;
import java.util.Objects;


import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

@Entity
@Table(name = "game")
public class Game {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;

	@NotNull
	@NotBlank
	private String title;

	@NotNull
	private String genre;

	@NotNull
	@Pattern(regexp = "^(3|7|12|16|18)$", message = "Invalid Rating")
	private int ageRating;

	@NotNull
	private int releaseYear;

	@OneToMany(mappedBy = "game", targetEntity = Review.class, fetch = FetchType.LAZY)
	private List<Review> reviews;

	public Game(int id, @NotNull @NotBlank String title, @NotNull String genre,
			@NotNull @Pattern(regexp = "^(3|7|12|16|18)$", message = "Invalid Rating") int ageRating,
			@NotNull int releaseYear, List<Review> reviews) {
		super();
		this.id = id;
		this.title = title;
		this.genre = genre;
		this.ageRating = ageRating;
		this.releaseYear = releaseYear;
		this.reviews = reviews;
	}

	public Game(@NotNull @NotBlank String title, @NotNull String genre,
			@NotNull @Pattern(regexp = "^(3|7|12|16|18)$", message = "Invalid Rating") int ageRating,
			@NotNull int releaseYear, List<Review> reviews) {
		super();
		this.title = title;
		this.genre = genre;
		this.ageRating = ageRating;
		this.releaseYear = releaseYear;
		this.reviews = reviews;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getGenre() {
		return genre;
	}

	public void setGenre(String genre) {
		this.genre = genre;
	}

	public int getAgeRating() {
		return ageRating;
	}

	public void setAgeRating(int ageRating) {
		this.ageRating = ageRating;
	}

	public int getReleaseYear() {
		return releaseYear;
	}

	public void setReleaseYear(int releaseYear) {
		this.releaseYear = releaseYear;
	}

	public List<Review> getReviews() {
		return reviews;
	}

	public void setReviews(List<Review> reviews) {
		this.reviews = reviews;
	}

	@Override
	public String toString() {
		return "Game [id=" + id + ", title=" + title + ", genre=" + genre + ", ageRating=" + ageRating
				+ ", releaseYear=" + releaseYear + ", reviews=" + reviews + "]";
	}

	@Override
	public int hashCode() {
		return Objects.hash(ageRating, genre, id, releaseYear, reviews, title);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Game other = (Game) obj;
		return ageRating == other.ageRating && Objects.equals(genre, other.genre) && id == other.id
				&& releaseYear == other.releaseYear && Objects.equals(reviews, other.reviews)
				&& Objects.equals(title, other.title);
	}

	
}
