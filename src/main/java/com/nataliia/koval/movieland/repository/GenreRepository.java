package com.nataliia.koval.movieland.repository;

import com.nataliia.koval.movieland.entity.Genre;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GenreRepository extends JpaRepository<Genre, Integer> {
}
