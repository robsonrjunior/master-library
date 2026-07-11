package com.github.robsonrjunior.master.library.controllers;

import com.github.robsonrjunior.master.library.dto.SeriesListItem;
import com.github.robsonrjunior.master.library.service.SeriesService;
import com.github.robsonrjunior.master.library.views.SeriesLazyDataModel;
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
public class SeriesListController implements Serializable {

    @Inject
    private SeriesService seriesService;

    private SeriesLazyDataModel lazySeries;
    private List<SeriesListItem> selectedSeries;
    private SeriesListItem selectedSerie;

    @PostConstruct
    public void init() {
        lazySeries = new SeriesLazyDataModel(seriesService);
    }

    public SeriesLazyDataModel getLazySeries() {
        return lazySeries;
    }

    public List<SeriesListItem> getSelectedSeries() {
        return selectedSeries;
    }

    public void setSelectedSeries(List<SeriesListItem> selectedSeries) {
        this.selectedSeries = selectedSeries;
    }

    public SeriesListItem getSelectedSerie() {
        return selectedSerie;
    }

    public void setSelectedSerie(SeriesListItem selectedSerie) {
        this.selectedSerie = selectedSerie;
    }

    public void deleteSerie() {
        if (selectedSerie != null && selectedSerie.id() != null) {
            seriesService.delete(selectedSerie.id());
            addMessage(FacesMessage.SEVERITY_INFO, "Serie excluida", "Serie removida com sucesso.");
        }
    }

    public void deleteSelectedSeries() {
        if (hasSelectedSeries()) {
            for (SeriesListItem series : new ArrayList<>(selectedSeries)) {
                seriesService.delete(series.id());
            }
            selectedSeries = null;
            addMessage(FacesMessage.SEVERITY_INFO, "Series excluidas", "Series selecionadas removidas com sucesso.");
        }
    }

    public boolean hasSelectedSeries() {
        return selectedSeries != null && !selectedSeries.isEmpty();
    }

    public String getDeleteButtonMessage() {
        if (hasSelectedSeries()) {
            int size = selectedSeries.size();
            return size > 1 ? size + " series selecionadas" : "1 serie selecionada";
        }
        return "Excluir";
    }

    private void addMessage(FacesMessage.Severity severity, String summary, String detail) {
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(severity, summary, detail));
    }
}
