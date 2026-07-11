package com.github.robsonrjunior.master.library.repository;

import com.github.robsonrjunior.master.library.dto.SeriesListItem;
import com.github.robsonrjunior.master.library.model.Series;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.persistence.criteria.Root;
import jakarta.persistence.criteria.Selection;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.primefaces.model.FilterMeta;
import org.primefaces.model.SortMeta;

@ApplicationScoped
public class SeriesRepository extends MediaRepository<Series> {

    private static final ProjectionSpec<Series, SeriesListItem> LIST_PROJECTION = new ProjectionSpec<>() {
        @Override
        public Class<SeriesListItem> projectionType() {
            return SeriesListItem.class;
        }

        @Override
        public List<Selection<?>> selections(Root<Series> root) {
            return List.of(
                root.get("id"),
                root.get("title"),
                root.get("numberOfSeasons"),
                root.get("numberOfEpisodes"),
                root.get("startYear"),
                root.get("status")
            );
        }

        @Override
        public Set<String> filterableFields() {
            return Set.of("title", "numberOfSeasons", "numberOfEpisodes", "startYear", "status");
        }

        @Override
        public Set<String> sortableFields() {
            return Set.of("title", "numberOfSeasons", "numberOfEpisodes", "startYear");
        }

        @Override
        public Set<String> globalFilterFields() {
            return Set.of("title");
        }
    };

    public SeriesRepository() {
        super(Series.class);
    }

    public Page<SeriesListItem> findPage(
        int first,
        int pageSize,
        Map<String, SortMeta> sortBy,
        Map<String, FilterMeta> filterBy
    ) {
        return findPage(first, pageSize, sortBy, filterBy, LIST_PROJECTION);
    }
}
