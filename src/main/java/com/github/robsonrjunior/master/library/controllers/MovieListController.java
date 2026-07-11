package com.github.robsonrjunior.master.library.controllers;

import com.github.robsonrjunior.master.library.dto.MovieListItem;
import com.github.robsonrjunior.master.library.service.MovieService;
import com.github.robsonrjunior.master.library.views.MovieLazyDataModel;
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
public class MovieListController implements Serializable {

    @Inject
    private MovieService movieService;

    private MovieLazyDataModel lazyMovies;
    private List<MovieListItem> selectedMovies;
    private MovieListItem selectedMovie;

    @PostConstruct
    public void init() {
        lazyMovies = new MovieLazyDataModel(movieService);
    }

    public MovieLazyDataModel getLazyMovies() {
        return lazyMovies;
    }

    public List<MovieListItem> getSelectedMovies() {
        return selectedMovies;
    }

    public void setSelectedMovies(List<MovieListItem> selectedMovies) {
        this.selectedMovies = selectedMovies;
    }

    public MovieListItem getSelectedMovie() {
        return selectedMovie;
    }

    public void setSelectedMovie(MovieListItem selectedMovie) {
        this.selectedMovie = selectedMovie;
    }

    public void deleteMovie() {
        if (selectedMovie != null && selectedMovie.id() != null) {
            movieService.delete(selectedMovie.id());
            addMessage(FacesMessage.SEVERITY_INFO, "Filme excluido", "Filme removido com sucesso.");
        }
    }

    public void deleteSelectedMovies() {
        if (hasSelectedMovies()) {
            for (MovieListItem movie : new ArrayList<>(selectedMovies)) {
                movieService.delete(movie.id());
            }
            selectedMovies = null;
            addMessage(FacesMessage.SEVERITY_INFO, "Filmes excluidos", "Filmes selecionados removidos com sucesso.");
        }
    }

    public boolean hasSelectedMovies() {
        return selectedMovies != null && !selectedMovies.isEmpty();
    }

    public String getDeleteButtonMessage() {
        if (hasSelectedMovies()) {
            int size = selectedMovies.size();
            return size > 1 ? size + " filmes selecionados" : "1 filme selecionado";
        }
        return "Excluir";
    }

    private void addMessage(FacesMessage.Severity severity, String summary, String detail) {
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(severity, summary, detail));
    }
}
