package com.github.robsonrjunior.master.library.controllers;

import com.github.robsonrjunior.master.library.dto.BookListItem;
import com.github.robsonrjunior.master.library.service.BookService;
import com.github.robsonrjunior.master.library.views.BookLazyDataModel;
import jakarta.annotation.PostConstruct;
import jakarta.faces.application.FacesMessage;
import jakarta.faces.context.FacesContext;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Named
@ViewScoped
public class BookListController implements Serializable {

    @Inject
    private BookService bookService;

    private BookLazyDataModel lazyBooks;
    private List<BookListItem> selectedBooks;
    private BookListItem selectedBook;

    @PostConstruct
    public void init() {
        lazyBooks = new BookLazyDataModel(bookService);
    }

    public BookLazyDataModel getLazyBooks() {
        return lazyBooks;
    }

    public List<BookListItem> getSelectedBooks() {
        return selectedBooks;
    }

    public void setSelectedBooks(List<BookListItem> selectedBooks) {
        this.selectedBooks = selectedBooks;
    }

    public BookListItem getSelectedBook() {
        return selectedBook;
    }

    public void setSelectedBook(BookListItem selectedBook) {
        this.selectedBook = selectedBook;
    }

    public void deleteBook() {
        if (selectedBook != null && selectedBook.id() != null) {
            bookService.delete(selectedBook.id());
            addMessage(FacesMessage.SEVERITY_INFO, "Livro excluído", "Livro removido com sucesso.");
        }
    }

    public void deleteSelectedBooks() {
        if (hasSelectedBooks()) {
            for (BookListItem book : new ArrayList<>(selectedBooks)) {
                bookService.delete(book.id());
            }
            selectedBooks = null;
            addMessage(FacesMessage.SEVERITY_INFO, "Livros excluídos", "Livros selecionados removidos com sucesso.");
        }
    }

    public boolean hasSelectedBooks() {
        return selectedBooks != null && !selectedBooks.isEmpty();
    }

    public String getDeleteButtonMessage() {
        if (hasSelectedBooks()) {
            int size = selectedBooks.size();
            return size > 1 ? size + " livros selecionados" : "1 livro selecionado";
        }
        return "Excluir";
    }

    private void addMessage(FacesMessage.Severity severity, String summary, String detail) {
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(severity, summary, detail));
    }
}
