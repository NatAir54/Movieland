package com.nataliia.koval.movieland.service.impl;

import com.nataliia.koval.movieland.dto.MovieDto;
import com.nataliia.koval.movieland.mapper.MovieMapper;
import com.nataliia.koval.movieland.repository.MovieRepository;
import com.nataliia.koval.movieland.service.MovieService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class DefaultMovieService implements MovieService {
    private final MovieRepository movieRepository;
    private final MovieMapper movieMapper;

    @Override
    public List<MovieDto> findAll() {
        return movieRepository.findAll().stream()
                .map(movieMapper::toMovieDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<MovieDto> findRandom(int count) {
        List<MovieDto> randomMovies = movieRepository.findRandomMovies(count).stream()
                .map(movieMapper::toMovieDto)
                .collect(Collectors.toList());
        return randomMovies;
    }
}
