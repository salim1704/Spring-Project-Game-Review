package com.qa.GameReview.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.qa.GameReview.entity.Game;

@Repository
public interface GameRepository extends JpaRepository<Game, Integer>{

}
