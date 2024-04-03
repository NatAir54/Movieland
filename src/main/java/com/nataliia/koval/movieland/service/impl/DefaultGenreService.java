package com.nataliia.koval.movieland.service.impl;

import com.nataliia.koval.movieland.dto.GenreDto;
import com.nataliia.koval.movieland.mapper.GenreMapper;
import com.nataliia.koval.movieland.repository.GenreRepository;
import com.nataliia.koval.movieland.service.GenreService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class DefaultGenreService implements GenreService {
    private final GenreRepository genreRepository;
    private final GenreMapper genreMapper;

    @Override
    public List<GenreDto> findAll() {
        return genreRepository.findAll().stream()
                .map(genreMapper::toGenreDto)
                .collect(Collectors.toList());
    }
}
