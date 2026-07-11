package com.github.robsonrjunior.master.library.service;

import com.github.robsonrjunior.master.library.dto.MovieListItem;
import com.github.robsonrjunior.master.library.model.Movie;
import com.github.robsonrjunior.master.library.repository.MediaRepository;
import com.github.robsonrjunior.master.library.repository.MovieRepository;
import com.github.robsonrjunior.master.library.repository.Page;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import java.util.Map;
import org.primefaces.model.FilterMeta;
import org.primefaces.model.SortMeta;

@ApplicationScoped
public class MovieService extends MediaService<Movie> {

    @Inject
    private MovieRepository repository;

    @Override
    protected MediaRepository<Movie> repository() {
        return repository;
    }

    public Page<MovieListItem> findPage(
        int first,
        int pageSize,
        Map<String, SortMeta> sortBy,
        Map<String, FilterMeta> filterBy
    ) {
        return repository.findPage(first, pageSize, sortBy, filterBy);
    }
}
