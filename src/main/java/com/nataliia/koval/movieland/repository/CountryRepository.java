package com.nataliia.koval.movieland.repository;

import com.nataliia.koval.movieland.entity.Country;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CountryRepository extends JpaRepository<Country, Integer> {
}
