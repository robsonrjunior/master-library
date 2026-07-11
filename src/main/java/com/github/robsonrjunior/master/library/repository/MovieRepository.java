package com.github.robsonrjunior.master.library.repository;

import com.github.robsonrjunior.master.library.dto.MovieListItem;
import com.github.robsonrjunior.master.library.model.Movie;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.persistence.criteria.Root;
import jakarta.persistence.criteria.Selection;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.primefaces.model.FilterMeta;
import org.primefaces.model.SortMeta;

@ApplicationScoped
public class MovieRepository extends MediaRepository<Movie> {

    private static final ProjectionSpec<Movie, MovieListItem> LIST_PROJECTION = new ProjectionSpec<>() {
        @Override
        public Class<MovieListItem> projectionType() {
            return MovieListItem.class;
        }

        @Override
        public List<Selection<?>> selections(Root<Movie> root) {
            return List.of(
                root.get("id"),
                root.get("title"),
                root.get("director"),
                root.get("runtimeMinutes"),
                root.get("releaseYear")
            );
        }

        @Override
        public Set<String> filterableFields() {
            return Set.of("title", "director", "runtimeMinutes", "releaseYear");
        }

        @Override
        public Set<String> sortableFields() {
            return Set.of("title", "director", "runtimeMinutes", "releaseYear");
        }

        @Override
        public Set<String> globalFilterFields() {
            return Set.of("title", "director");
        }
    };

    public MovieRepository() {
        super(Movie.class);
    }

    public Page<MovieListItem> findPage(
        int first,
        int pageSize,
        Map<String, SortMeta> sortBy,
        Map<String, FilterMeta> filterBy
    ) {
        return findPage(first, pageSize, sortBy, filterBy, LIST_PROJECTION);
    }
}
