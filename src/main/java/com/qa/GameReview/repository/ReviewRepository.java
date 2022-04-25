package com.qa.GameReview.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.qa.GameReview.entity.Review;

@Repository
public interface ReviewRepository extends JpaRepository<Review, Integer>{

}
