package com.qa.GameReview.dto;

import java.util.Objects;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

public class NewGameDTO {

	@NotNull
	@NotBlank
	private String title;

	@NotNull
	private String genre;

	@NotNull
	private int ageRating;

	@NotNull
	private Integer releaseYear;

	public NewGameDTO() {
		super();
	}
	
	public NewGameDTO(String title, String genre, int ageRating, Integer releaseYear) {
		super();
		this.title = title;
		this.genre = genre;
		this.ageRating = ageRating;
		this.releaseYear = releaseYear;
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
		return "NewGameDTO [title=" + title + ", genre=" + genre + ", ageRating=" + ageRating + ", releaseYear="
				+ releaseYear + "]";
	}

	@Override
	public int hashCode() {
		return Objects.hash(ageRating, genre, releaseYear, title);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		NewGameDTO other = (NewGameDTO) obj;
		return ageRating == other.ageRating && Objects.equals(genre, other.genre) && releaseYear == other.releaseYear
				&& Objects.equals(title, other.title);
	}

}
