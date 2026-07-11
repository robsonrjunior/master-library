package com.github.robsonrjunior.master.library.views;

import com.github.robsonrjunior.master.library.dto.BookListItem;
import com.github.robsonrjunior.master.library.repository.Page;
import com.github.robsonrjunior.master.library.service.BookService;
import java.util.List;
import java.util.Map;
import org.primefaces.model.FilterMeta;
import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortMeta;

public class BookLazyDataModel extends LazyDataModel<BookListItem> {

    private final BookService bookService;
    private List<BookListItem> currentPage = List.of();

    public BookLazyDataModel(BookService bookService) {
        this.bookService = bookService;
    }

    @Override
    public int count(Map<String, FilterMeta> filterBy) {
        return (int) bookService.findPage(0, 0, null, filterBy).totalElements();
    }

    @Override
    public List<BookListItem> load(
        int first,
        int pageSize,
        Map<String, SortMeta> sortBy,
        Map<String, FilterMeta> filterBy
    ) {
        Page<BookListItem> page = bookService.findPage(first, pageSize, sortBy, filterBy);
        setRowCount((int) page.totalElements());
        currentPage = page.content();
        return currentPage;
    }

    @Override
    public String getRowKey(BookListItem book) {
        return book == null || book.id() == null ? null : String.valueOf(book.id());
    }

    @Override
    public BookListItem getRowData(String rowKey) {
        if (rowKey == null || rowKey.isBlank()) {
            return null;
        }
        Long id = Long.valueOf(rowKey);
        return currentPage
            .stream()
            .filter(item -> id.equals(item.id()))
            .findFirst()
            .orElseGet(() -> new BookListItem(id, null, null, null, null, null));
    }
}
