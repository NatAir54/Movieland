package com.nataliia.koval.movieland.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.persistence.Index;
import jakarta.persistence.NamedAttributeNode;
import jakarta.persistence.NamedEntityGraph;
import jakarta.persistence.Id;
import jakarta.persistence.GenerationType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.NamedSubgraph;
import jakarta.persistence.Column;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.OneToMany;

import lombok.Getter;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

@Entity
@Getter
@Setter
@Table(name = "movie", indexes = {
        @Index(name = "idx_movie_rating", columnList = "rating"),
        @Index(name = "idx_movie_price", columnList = "price")
})
@NamedEntityGraph(name = "graph.MovieGenresCountriesReviewsUser",
        attributeNodes = {
                @NamedAttributeNode("genres"),
                @NamedAttributeNode("countries"),
                @NamedAttributeNode(value = "reviews", subgraph = "subgraph.review")
        },
        subgraphs = {
                @NamedSubgraph(name = "subgraph.review",
                        attributeNodes = @NamedAttributeNode("user"))
        }
)
public class Movie {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "movie_sequence")
    @SequenceGenerator(name = "movie_sequence", sequenceName = "movie_id_seq", allocationSize = 20)
    private int id;

    @Column(name = "name_russian")
    private String nameRussian;

    @Column(name = "name_native")
    private String nameNative;

    @Column(name = "year_of_release")
    private String yearOfRelease;

    @Column(name = "description")
    private String description;

    @Column(name = "rating")
    private double rating;

    @Column(name = "price")
    private double price;

    @Column(name= "picture_path")
    private String picturePath;

    @ManyToMany
    @JoinTable(name = "movie_country",
            joinColumns = @JoinColumn(name = "movie_id"),
            inverseJoinColumns = @JoinColumn(name = "country_id"))
    private Set<Country> countries = new HashSet<>();

    @ManyToMany
    @JoinTable(name = "movie_genre",
            joinColumns = @JoinColumn(name = "movie_id"),
            inverseJoinColumns = @JoinColumn(name = "genre_id"))
    private Set<Genre> genres = new HashSet<>();

    @OneToMany
    @JoinColumn(name = "movie_id")
    private Set<Review> reviews = new HashSet<>();
}
