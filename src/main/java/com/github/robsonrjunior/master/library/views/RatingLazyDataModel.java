package com.github.robsonrjunior.master.library.views;

import com.github.robsonrjunior.master.library.dto.RatingListItem;
import com.github.robsonrjunior.master.library.repository.Page;
import com.github.robsonrjunior.master.library.service.UserRatingService;
import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import org.primefaces.model.FilterMeta;
import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortMeta;

public class RatingLazyDataModel extends LazyDataModel<RatingListItem> {

    private final UserRatingService ratingService;
    private List<RatingListItem> currentPage = List.of();

    public RatingLazyDataModel(UserRatingService ratingService) {
        this.ratingService = ratingService;
    }

    @Override
    public int count(Map<String, FilterMeta> filterBy) {
        return (int) ratingService.findPage(0, 0, null, filterBy).totalElements();
    }

    @Override
    public List<RatingListItem> load(
        int first,
        int pageSize,
        Map<String, SortMeta> sortBy,
        Map<String, FilterMeta> filterBy
    ) {
        Page<RatingListItem> page = ratingService.findPage(first, pageSize, sortBy, filterBy);
        setRowCount((int) page.totalElements());
        currentPage = page.content();
        return currentPage;
    }

    @Override
    public String getRowKey(RatingListItem rating) {
        return rating == null || rating.id() == null ? null : String.valueOf(rating.id());
    }

    @Override
    public RatingListItem getRowData(String rowKey) {
        if (rowKey == null || rowKey.isBlank()) {
            return null;
        }
        Long id = Long.valueOf(rowKey);
        return currentPage
            .stream()
            .filter(item -> id.equals(item.id()))
            .findFirst()
            .orElseGet(() -> new RatingListItem(id, null, null, BigDecimal.ZERO, null));
    }
}
