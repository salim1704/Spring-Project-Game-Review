package com.qa.GameReview.dto;

import java.util.Objects;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

public class GameDTO {

	private int id;

	@NotNull
	@NotBlank
	private String title;

	@NotNull
	private String genre;

	@NotNull
	private int ageRating;

	@NotNull
	private int releaseYear;

	public GameDTO() {
		super();
	}

	public GameDTO(int id, @NotNull @NotBlank String title, @NotNull String genre,
			@NotNull int ageRating,
			@NotNull int releaseYear) {
		super();
		this.id = id;
		this.title = title;
		this.genre = genre;
		this.ageRating = ageRating;
		this.releaseYear = releaseYear;
	}

	public GameDTO(@NotNull @NotBlank String title, @NotNull String genre,
			@NotNull int ageRating,
			@NotNull int releaseYear) {
		super();
		this.title = title;
		this.genre = genre;
		this.ageRating = ageRating;
		this.releaseYear = releaseYear;
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

	@Override
	public String toString() {
		return "GameDTO [id=" + id + ", title=" + title + ", genre=" + genre + ", ageRating=" + ageRating
				+ ", releaseYear=" + releaseYear + "]";
	}

	@Override
	public int hashCode() {
		return Objects.hash(ageRating, genre, id, releaseYear, title);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		GameDTO other = (GameDTO) obj;
		return ageRating == other.ageRating && Objects.equals(genre, other.genre) && id == other.id
				&& releaseYear == other.releaseYear && Objects.equals(title, other.title);
	}

}
