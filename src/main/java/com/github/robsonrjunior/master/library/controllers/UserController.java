package com.github.robsonrjunior.master.library.controllers;

import com.github.robsonrjunior.master.library.exception.DuplicateResourceException;
import com.github.robsonrjunior.master.library.exception.ResourceNotFoundException;
import com.github.robsonrjunior.master.library.model.User;
import com.github.robsonrjunior.master.library.service.UserService;
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
public class UserController implements Serializable {

    private static final String CREATE = "create";
    private static final String EDIT = "edit";
    private static final String VIEW = "view";

    @Inject
    private UserService userService;

    private Long id;
    private String mode;
    private User user = new User();

    public void loadForm() {
        FacesContext context = FacesContext.getCurrentInstance();
        if (context.isPostback()) {
            return;
        }
        readViewParameters(context);
        if (CREATE.equals(mode) || id == null) {
            mode = CREATE;
            user = new User();
            return;
        }
        if (mode == null) {
            mode = VIEW;
        }
        try {
            user = userService.get(id);
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
        boolean creating = user.getId() == null;
        try {
            if (creating) {
                userService.create(user);
            } else {
                userService.update(user.getId(), user);
            }
        } catch (DuplicateResourceException e) {
            FacesContext.getCurrentInstance().addMessage(
                null,
                new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro de duplicidade", e.getMessage())
            );
            return null;
        }
        FacesContext context = FacesContext.getCurrentInstance();
        context.getExternalContext().getFlash().setKeepMessages(true);
        context.addMessage(
            null,
            new FacesMessage(
                FacesMessage.SEVERITY_INFO,
                creating ? "Usuario criado" : "Usuario atualizado",
                "Usuario salvo com sucesso."
            )
        );
        return "user-list?faces-redirect=true";
    }

    private void redirectToListWithError() {
        FacesContext context = FacesContext.getCurrentInstance();
        context.getExternalContext().getFlash().setKeepMessages(true);
        context.addMessage(
            null,
            new FacesMessage(FacesMessage.SEVERITY_ERROR, "Usuario nao encontrado", "O usuario solicitado nao existe.")
        );
        try {
            context
                .getExternalContext()
                .redirect(context.getExternalContext().getRequestContextPath() + "/user-list.xhtml");
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
            return "Novo Usuario";
        }
        if (isEditMode()) {
            return "Editar Usuario";
        }
        return "Detalhes do Usuario";
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

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
