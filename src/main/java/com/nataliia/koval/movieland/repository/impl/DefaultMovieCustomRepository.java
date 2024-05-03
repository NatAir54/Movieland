package com.nataliia.koval.movieland.repository.impl;

import com.nataliia.koval.movieland.entity.Genre;
import com.nataliia.koval.movieland.entity.Movie;
import com.nataliia.koval.movieland.repository.MovieCustomRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;
import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.Predicate;

import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class DefaultMovieCustomRepository implements MovieCustomRepository {

    @PersistenceContext
    private EntityManager entityManager;


    @Override
    public List<Movie> findByGenre(int genreId) {
        CriteriaQuery<Movie> query = createBasicQuery();
        Root<Movie> root = query.from(Movie.class);

        Predicate genrePredicate = createGenrePredicate(entityManager.getCriteriaBuilder(), root, genreId);
        query.where(genrePredicate);

        return entityManager.createQuery(query).getResultList();
    }

    @Override
    public List<Movie> findByGenreAndSort(int genreId, String sortBy, Sort.Direction direction) {
        CriteriaQuery<Movie> query = createBasicQuery();
        Root<Movie> root = query.from(Movie.class);

        Predicate genrePredicate = createGenrePredicate(entityManager.getCriteriaBuilder(), root, genreId);
        query.where(genrePredicate);

        applySorting(query, root, sortBy, direction);

        return entityManager.createQuery(query).getResultList();
    }

    private void applySorting(CriteriaQuery<Movie> query, Root<Movie> root, String sortBy, Sort.Direction direction) {
        CriteriaBuilder builder = entityManager.getCriteriaBuilder();
        boolean sortDirection = (direction == Sort.Direction.ASC);

        if ("price".equals(sortBy)) {
            query.orderBy(sortDirection ?
                    builder.asc(root.get("price")) :
                    builder.desc(root.get("price")));
        } else {
            query.orderBy(sortDirection ?
                    builder.asc(root.get("rating")) :
                    builder.desc(root.get("rating")));
        }
    }

    private CriteriaQuery<Movie> createBasicQuery() {
        CriteriaBuilder builder = entityManager.getCriteriaBuilder();
        return builder.createQuery(Movie.class);
    }

    private Predicate createGenrePredicate(CriteriaBuilder builder, Root<Movie> root, int genreId) {
        Join<Movie, Genre> genreJoin = root.joinSet("genres");
        return builder.equal(genreJoin.get("id"), genreId);
    }
}
