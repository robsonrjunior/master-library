package com.github.robsonrjunior.master.library.service;

import com.github.robsonrjunior.master.library.dto.BookListItem;
import com.github.robsonrjunior.master.library.model.Book;
import com.github.robsonrjunior.master.library.repository.BookRepository;
import com.github.robsonrjunior.master.library.repository.MediaRepository;
import com.github.robsonrjunior.master.library.repository.Page;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import java.util.Map;
import org.primefaces.model.FilterMeta;
import org.primefaces.model.SortMeta;

@ApplicationScoped
public class BookService extends MediaService<Book> {

    @Inject
    private BookRepository repository;

    @Override
    protected MediaRepository<Book> repository() {
        return repository;
    }

    public Page<BookListItem> findPage(
        int first,
        int pageSize,
        Map<String, SortMeta> sortBy,
        Map<String, FilterMeta> filterBy
    ) {
        return repository.findPage(first, pageSize, sortBy, filterBy);
    }
}
