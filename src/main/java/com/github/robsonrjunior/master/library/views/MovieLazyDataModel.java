package com.github.robsonrjunior.master.library.views;

import com.github.robsonrjunior.master.library.dto.MovieListItem;
import com.github.robsonrjunior.master.library.repository.Page;
import com.github.robsonrjunior.master.library.service.MovieService;
import java.util.List;
import java.util.Map;
import org.primefaces.model.FilterMeta;
import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortMeta;

public class MovieLazyDataModel extends LazyDataModel<MovieListItem> {

    private final MovieService movieService;
    private List<MovieListItem> currentPage = List.of();

    public MovieLazyDataModel(MovieService movieService) {
        this.movieService = movieService;
    }

    @Override
    public int count(Map<String, FilterMeta> filterBy) {
        return (int) movieService.findPage(0, 0, null, filterBy).totalElements();
    }

    @Override
    public List<MovieListItem> load(
        int first,
        int pageSize,
        Map<String, SortMeta> sortBy,
        Map<String, FilterMeta> filterBy
    ) {
        Page<MovieListItem> page = movieService.findPage(first, pageSize, sortBy, filterBy);
        setRowCount((int) page.totalElements());
        currentPage = page.content();
        return currentPage;
    }

    @Override
    public String getRowKey(MovieListItem movie) {
        return movie == null || movie.id() == null ? null : String.valueOf(movie.id());
    }

    @Override
    public MovieListItem getRowData(String rowKey) {
        if (rowKey == null || rowKey.isBlank()) {
            return null;
        }
        Long id = Long.valueOf(rowKey);
        return currentPage
            .stream()
            .filter(item -> id.equals(item.id()))
            .findFirst()
            .orElseGet(() -> new MovieListItem(id, null, null, null, null));
    }
}
