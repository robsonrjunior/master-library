package com.github.robsonrjunior.master.library.views;

import com.github.robsonrjunior.master.library.dto.SeriesListItem;
import com.github.robsonrjunior.master.library.repository.Page;
import com.github.robsonrjunior.master.library.service.SeriesService;
import java.util.List;
import java.util.Map;
import org.primefaces.model.FilterMeta;
import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortMeta;

public class SeriesLazyDataModel extends LazyDataModel<SeriesListItem> {

    private final SeriesService seriesService;
    private List<SeriesListItem> currentPage = List.of();

    public SeriesLazyDataModel(SeriesService seriesService) {
        this.seriesService = seriesService;
    }

    @Override
    public int count(Map<String, FilterMeta> filterBy) {
        return (int) seriesService.findPage(0, 0, null, filterBy).totalElements();
    }

    @Override
    public List<SeriesListItem> load(
        int first,
        int pageSize,
        Map<String, SortMeta> sortBy,
        Map<String, FilterMeta> filterBy
    ) {
        Page<SeriesListItem> page = seriesService.findPage(first, pageSize, sortBy, filterBy);
        setRowCount((int) page.totalElements());
        currentPage = page.content();
        return currentPage;
    }

    @Override
    public String getRowKey(SeriesListItem series) {
        return series == null || series.id() == null ? null : String.valueOf(series.id());
    }

    @Override
    public SeriesListItem getRowData(String rowKey) {
        if (rowKey == null || rowKey.isBlank()) {
            return null;
        }
        Long id = Long.valueOf(rowKey);
        return currentPage
            .stream()
            .filter(item -> id.equals(item.id()))
            .findFirst()
            .orElseGet(() -> new SeriesListItem(id, null, null, null, null, null));
    }
}
