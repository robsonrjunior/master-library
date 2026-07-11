package com.github.robsonrjunior.master.library.controllers;

import com.github.robsonrjunior.master.library.dto.RatingListItem;
import com.github.robsonrjunior.master.library.service.UserRatingService;
import com.github.robsonrjunior.master.library.views.RatingLazyDataModel;
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
public class RatingListController implements Serializable {

    @Inject
    private UserRatingService ratingService;

    private RatingLazyDataModel lazyRatings;
    private List<RatingListItem> selectedRatings;
    private RatingListItem selectedRating;

    @PostConstruct
    public void init() {
        lazyRatings = new RatingLazyDataModel(ratingService);
    }

    public RatingLazyDataModel getLazyRatings() {
        return lazyRatings;
    }

    public List<RatingListItem> getSelectedRatings() {
        return selectedRatings;
    }

    public void setSelectedRatings(List<RatingListItem> selectedRatings) {
        this.selectedRatings = selectedRatings;
    }

    public RatingListItem getSelectedRating() {
        return selectedRating;
    }

    public void setSelectedRating(RatingListItem selectedRating) {
        this.selectedRating = selectedRating;
    }

    public void deleteRating() {
        if (selectedRating != null && selectedRating.id() != null) {
            ratingService.delete(selectedRating.id());
            addMessage(FacesMessage.SEVERITY_INFO, "Avaliacao excluida", "Avaliacao removida com sucesso.");
        }
    }

    public void deleteSelectedRatings() {
        if (hasSelectedRatings()) {
            for (RatingListItem rating : new ArrayList<>(selectedRatings)) {
                ratingService.delete(rating.id());
            }
            selectedRatings = null;
            addMessage(FacesMessage.SEVERITY_INFO, "Avaliacoes excluidas", "Avaliacoes selecionadas removidas com sucesso.");
        }
    }

    public boolean hasSelectedRatings() {
        return selectedRatings != null && !selectedRatings.isEmpty();
    }

    public String getDeleteButtonMessage() {
        if (hasSelectedRatings()) {
            int size = selectedRatings.size();
            return size > 1 ? size + " avaliacoes selecionadas" : "1 avaliacao selecionada";
        }
        return "Excluir";
    }

    private void addMessage(FacesMessage.Severity severity, String summary, String detail) {
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(severity, summary, detail));
    }
}
