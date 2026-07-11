package com.github.robsonrjunior.master.library.repository;

import com.github.robsonrjunior.master.library.dto.BookListItem;
import com.github.robsonrjunior.master.library.model.Book;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.persistence.criteria.Root;
import jakarta.persistence.criteria.Selection;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.primefaces.model.FilterMeta;
import org.primefaces.model.SortMeta;

@ApplicationScoped
public class BookRepository extends MediaRepository<Book> {

    private static final ProjectionSpec<Book, BookListItem> LIST_PROJECTION = new ProjectionSpec<>() {
        @Override
        public Class<BookListItem> projectionType() {
            return BookListItem.class;
        }

        @Override
        public List<Selection<?>> selections(Root<Book> root) {
            return List.of(
                root.get("id"),
                root.get("title"),
                root.get("author"),
                root.get("isbn"),
                root.get("pageCount"),
                root.get("publishedYear")
            );
        }

        @Override
        public Set<String> filterableFields() {
            return Set.of("title", "author", "isbn", "pageCount", "publishedYear");
        }

        @Override
        public Set<String> sortableFields() {
            return Set.of("title", "author", "isbn", "pageCount", "publishedYear");
        }

        @Override
        public Set<String> globalFilterFields() {
            return Set.of("title", "author", "isbn");
        }
    };

    public BookRepository() {
        super(Book.class);
    }

    public Page<BookListItem> findPage(
        int first,
        int pageSize,
        Map<String, SortMeta> sortBy,
        Map<String, FilterMeta> filterBy
    ) {
        return findPage(first, pageSize, sortBy, filterBy, LIST_PROJECTION);
    }
}
