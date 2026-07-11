package com.github.robsonrjunior.master.library.controllers;

import com.github.robsonrjunior.master.library.exception.ResourceNotFoundException;
import com.github.robsonrjunior.master.library.model.MediaItem;
import com.github.robsonrjunior.master.library.model.User;
import com.github.robsonrjunior.master.library.model.UserRating;
import com.github.robsonrjunior.master.library.repository.MediaItemRepository;
import com.github.robsonrjunior.master.library.repository.UserRatingRepository;
import com.github.robsonrjunior.master.library.service.UserRatingService;
import com.github.robsonrjunior.master.library.service.UserService;
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
public class RatingController implements Serializable {

    private static final String CREATE = "create";
    private static final String EDIT = "edit";
    private static final String VIEW = "view";

    @Inject
    private UserRatingService ratingService;

    @Inject
    private UserService userService;

    @Inject
    private MediaItemRepository mediaItemRepository;

    @Inject
    private UserRatingRepository ratingRepository;

    private Long id;
    private String mode;
    private UserRating rating = new UserRating();
    private Long userId;
    private Long mediaId;

    public void loadForm() {
        FacesContext context = FacesContext.getCurrentInstance();
        if (context.isPostback()) {
            return;
        }
        readViewParameters(context);
        if (CREATE.equals(mode) || id == null) {
            mode = CREATE;
            rating = new UserRating();
            return;
        }
        if (mode == null) {
            mode = VIEW;
        }
        try {
            rating = ratingService.get(id);
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
        boolean creating = rating.getId() == null;
        if (creating) {
            ratingService.rate(userId, mediaId, rating.getScore(), rating.getComment());
        } else {
            ratingService.update(rating.getId(), rating.getScore(), rating.getComment());
        }
        FacesContext context = FacesContext.getCurrentInstance();
        context.getExternalContext().getFlash().setKeepMessages(true);
        context.addMessage(
            null,
            new FacesMessage(
                FacesMessage.SEVERITY_INFO,
                creating ? "Avaliacao criada" : "Avaliacao atualizada",
                "Avaliacao salva com sucesso."
            )
        );
        return "rating-list?faces-redirect=true";
    }

    private void redirectToListWithError() {
        FacesContext context = FacesContext.getCurrentInstance();
        context.getExternalContext().getFlash().setKeepMessages(true);
        context.addMessage(
            null,
            new FacesMessage(FacesMessage.SEVERITY_ERROR, "Avaliacao nao encontrada", "A avaliacao solicitada nao existe.")
        );
        try {
            context
                .getExternalContext()
                .redirect(context.getExternalContext().getRequestContextPath() + "/rating-list.xhtml");
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
            return "Nova Avaliacao";
        }
        if (isEditMode()) {
            return "Editar Avaliacao";
        }
        return "Detalhes da Avaliacao";
    }

    public List<SelectItem> getUserOptions() {
        return userService.list().stream()
            .map(u -> new SelectItem(u.getId(), u.getUsername()))
            .toList();
    }

    public List<SelectItem> getMediaOptions() {
        return mediaItemRepository.findAll().stream()
            .map(m -> new SelectItem(m.getId(), m.getTitle()))
            .toList();
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

    public UserRating getRating() {
        return rating;
    }

    public void setRating(UserRating rating) {
        this.rating = rating;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getMediaId() {
        return mediaId;
    }

    public void setMediaId(Long mediaId) {
        this.mediaId = mediaId;
    }
}
