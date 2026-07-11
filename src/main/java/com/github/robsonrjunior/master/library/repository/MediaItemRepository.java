package com.github.robsonrjunior.master.library.repository;

import com.github.robsonrjunior.master.library.model.MediaItem;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import java.util.Optional;

@ApplicationScoped
public class MediaItemRepository {

    @PersistenceContext(unitName = "masterLibraryPU")
    private EntityManager em;

    public Optional<MediaItem> findById(Long id) {
        return Optional.ofNullable(em.find(MediaItem.class, id));
    }

    public java.util.List<MediaItem> findAll() {
        return em.createQuery("SELECT m FROM MediaItem m ORDER BY m.title", MediaItem.class).getResultList();
    }
}
