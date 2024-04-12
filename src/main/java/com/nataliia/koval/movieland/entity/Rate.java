package com.nataliia.koval.movieland.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GenerationType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Column;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@Entity
public class Rate {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "rate_sequence")
    @SequenceGenerator(name = "rate_sequence", sequenceName = "rate_id_seq", allocationSize = 20)
    private int id;

    private String name;

    private double value;

    @Column(name = "last_updated")
    private LocalDateTime lastUpdated;
}
