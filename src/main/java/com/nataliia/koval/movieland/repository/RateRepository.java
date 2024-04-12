package com.nataliia.koval.movieland.repository;

import com.nataliia.koval.movieland.entity.Rate;
import io.micrometer.common.lang.NonNullApi;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

@NonNullApi
public interface RateRepository extends JpaRepository<Rate, Integer> {
    @Override
    List<Rate> findAll();

    @Query("SELECT r FROM Rate r WHERE lower(r.name) = lower(:name)")
    Rate findByNameIgnoreCase(@Param("name") String name);
}
