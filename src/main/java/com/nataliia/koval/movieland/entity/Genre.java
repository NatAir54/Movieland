package com.nataliia.koval.movieland.entity;

import com.nataliia.koval.movieland.cache.ImmutableGenre;
import jakarta.persistence.Entity;
import jakarta.persistence.GenerationType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Id;
import jakarta.persistence.Column;
import jakarta.persistence.Table;

import lombok.*;

@Entity
@Getter
@Setter
@AllArgsConstructor
@RequiredArgsConstructor
@Table(name="genre")
public class Genre implements ImmutableGenre {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "genre_sequence")
    @SequenceGenerator(name = "genre_sequence", sequenceName = "genre_id_seq", allocationSize = 1)
    private Integer id;

    @Column(name = "name")
    private String name;
}
