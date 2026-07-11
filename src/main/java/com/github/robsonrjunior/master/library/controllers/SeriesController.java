package com.github.robsonrjunior.master.library.controllers;

import com.github.robsonrjunior.master.library.exception.ResourceNotFoundException;
import com.github.robsonrjunior.master.library.model.Series;
import com.github.robsonrjunior.master.library.model.SeriesStatus;
import com.github.robsonrjunior.master.library.service.SeriesService;
import jakarta.faces.FacesException;
import jakarta.faces.application.FacesMessage;
import jakarta.faces.context.FacesContext;
import jakarta.faces.model.SelectItem;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import java.io.IOException;
import java.io.Serializable;
import java.util.List;
import java.util.Map;

@Named
@ViewScoped
public class SeriesController implements Serializable {

    private static final String CREATE = "create";
    private static final String EDIT = "edit";
    private static final String VIEW = "view";

    @Inject
    private SeriesService seriesService;

    private Long id;
    private String mode;
    private Series series = new Series();
    private List<SelectItem> statusOptions;

    public void loadForm() {
        FacesContext context = FacesContext.getCurrentInstance();
        if (context.isPostback()) {
            return;
        }
        readViewParameters(context);
        if (CREATE.equals(mode) || id == null) {
            mode = CREATE;
            series = new Series();
            return;
        }
        if (mode == null) {
            mode = VIEW;
        }
        try {
            series = seriesService.get(id);
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
        boolean creating = series.getId() == null;
        if (creating) {
            seriesService.create(series);
        } else {
            seriesService.update(series.getId(), series);
        }
        FacesContext context = FacesContext.getCurrentInstance();
        context.getExternalContext().getFlash().setKeepMessages(true);
        context.addMessage(
            null,
            new FacesMessage(
                FacesMessage.SEVERITY_INFO,
                creating ? "Serie criada" : "Serie atualizada",
                "Serie salva com sucesso."
            )
        );
        return "series-list?faces-redirect=true";
    }

    private void redirectToListWithError() {
        FacesContext context = FacesContext.getCurrentInstance();
        context.getExternalContext().getFlash().setKeepMessages(true);
        context.addMessage(
            null,
            new FacesMessage(FacesMessage.SEVERITY_ERROR, "Serie nao encontrada", "A serie solicitada nao existe.")
        );
        try {
            context
                .getExternalContext()
                .redirect(context.getExternalContext().getRequestContextPath() + "/series-list.xhtml");
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
            return "Nova Serie";
        }
        if (isEditMode()) {
            return "Editar Serie";
        }
        return "Detalhes da Serie";
    }

    public List<SelectItem> getStatusOptions() {
        if (statusOptions == null) {
            statusOptions = List.of(
                new SelectItem(SeriesStatus.ONGOING, "Em Andamento"),
                new SelectItem(SeriesStatus.ENDED, "Finalizada")
            );
        }
        return statusOptions;
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

    public Series getSeries() {
        return series;
    }

    public void setSeries(Series series) {
        this.series = series;
    }
}
