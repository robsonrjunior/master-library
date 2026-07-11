package com.github.robsonrjunior.master.library.controllers;

import com.github.robsonrjunior.master.library.exception.ResourceNotFoundException;
import com.github.robsonrjunior.master.library.model.Movie;
import com.github.robsonrjunior.master.library.service.MovieService;
import jakarta.faces.FacesException;
import jakarta.faces.application.FacesMessage;
import jakarta.faces.context.FacesContext;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import java.io.IOException;
import java.io.Serializable;
import java.util.Map;

@Named
@ViewScoped
public class MovieController implements Serializable {

    private static final String CREATE = "create";
    private static final String EDIT = "edit";
    private static final String VIEW = "view";

    @Inject
    private MovieService movieService;

    private Long id;
    private String mode;
    private Movie movie = new Movie();

    public void loadForm() {
        FacesContext context = FacesContext.getCurrentInstance();
        if (context.isPostback()) {
            return;
        }
        readViewParameters(context);
        if (CREATE.equals(mode) || id == null) {
            mode = CREATE;
            movie = new Movie();
            return;
        }
        if (mode == null) {
            mode = VIEW;
        }
        try {
            movie = movieService.get(id);
        } catch (ResourceNotFoundException e) {
            redirectToListWithError();
        }
    }

    private void readViewParameters(FacesContext context) {
        Map<String, String> params = context.getExternalContext().getRequestParameterMap();
        String idParam = params.get("id");
        if (idParam != null && !idParam.isBlank()) {
            try {
                id = Long.valueOf(idParam.trim());
            } catch (NumberFormatException e) {
                id = null;
            }
        }
        String modeParam = params.get("mode");
        if (modeParam != null && !modeParam.isBlank()) {
            mode = modeParam.trim();
        }
    }

    public String save() {
        boolean creating = movie.getId() == null;
        if (creating) {
            movieService.create(movie);
        } else {
            movieService.update(movie.getId(), movie);
        }
        FacesContext context = FacesContext.getCurrentInstance();
        context.getExternalContext().getFlash().setKeepMessages(true);
        context.addMessage(
            null,
            new FacesMessage(
                FacesMessage.SEVERITY_INFO,
                creating ? "Filme criado" : "Filme atualizado",
                "Filme salvo com sucesso."
            )
        );
        return "movie-list?faces-redirect=true";
    }

    private void redirectToListWithError() {
        FacesContext context = FacesContext.getCurrentInstance();
        context.getExternalContext().getFlash().setKeepMessages(true);
        context.addMessage(
            null,
            new FacesMessage(FacesMessage.SEVERITY_ERROR, "Filme nao encontrado", "O filme solicitado nao existe.")
        );
        try {
            context
                .getExternalContext()
                .redirect(context.getExternalContext().getRequestContextPath() + "/movie-list.xhtml");
        } catch (IOException e) {
            throw new FacesException(e);
        }
    }

    public boolean isCreateMode() {
        return CREATE.equals(mode);
    }

    public boolean isEditMode() {
        return EDIT.equals(mode);
    }

    public boolean isViewMode() {
        return VIEW.equals(mode);
    }

    public String getPageTitle() {
        if (isCreateMode()) {
            return "Novo Filme";
        }
        if (isEditMode()) {
            return "Editar Filme";
        }
        return "Detalhes do Filme";
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getMode() {
        return mode;
    }

    public void setMode(String mode) {
        this.mode = mode;
    }

    public Movie getMovie() {
        return movie;
    }

    public void setMovie(Movie movie) {
        this.movie = movie;
    }
}
