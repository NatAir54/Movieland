package com.nataliia.koval.movieland.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;


@Entity
@Getter
@Setter
@RequiredArgsConstructor
public class Review {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "review_sequence")
    @SequenceGenerator(name = "review_sequence", sequenceName = "review_id_seq", allocationSize = 20)
    private Integer id;

    @Column(name = "movie_name_russian")
    private String movieNameRussian;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    private String text;
}
