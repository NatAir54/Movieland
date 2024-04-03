package com.nataliia.koval.movieland.repository;

import com.nataliia.koval.movieland.entity.Genre;
import io.micrometer.common.lang.NonNullApi;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

@NonNullApi
public interface GenreRepository extends JpaRepository<Genre, Integer> {
    @Override
    List<Genre> findAll();
}
