package com.github.robsonrjunior.master.library.service;

import com.github.robsonrjunior.master.library.dto.SeriesListItem;
import com.github.robsonrjunior.master.library.model.Series;
import com.github.robsonrjunior.master.library.repository.MediaRepository;
import com.github.robsonrjunior.master.library.repository.Page;
import com.github.robsonrjunior.master.library.repository.SeriesRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import java.util.Map;
import org.primefaces.model.FilterMeta;
import org.primefaces.model.SortMeta;

@ApplicationScoped
public class SeriesService extends MediaService<Series> {

    @Inject
    private SeriesRepository repository;

    @Override
    protected MediaRepository<Series> repository() {
        return repository;
    }

    public Page<SeriesListItem> findPage(
        int first,
        int pageSize,
        Map<String, SortMeta> sortBy,
        Map<String, FilterMeta> filterBy
    ) {
        return repository.findPage(first, pageSize, sortBy, filterBy);
    }
}
