package com.github.robsonrjunior.master.library.repository;

import com.github.robsonrjunior.master.library.model.MediaItem;
import jakarta.enterprise.inject.Instance;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import org.primefaces.model.FilterMeta;
import org.primefaces.model.SortMeta;

public abstract class MediaRepository<T extends MediaItem> {

    @PersistenceContext(unitName = "masterLibraryPU")
    protected EntityManager em;

    @Inject
    private Instance<PageableQuery> pageableQuery;

    private final Class<T> entityClass;

    protected MediaRepository(Class<T> entityClass) {
        this.entityClass = entityClass;
    }

    protected <D> Page<D> findPage(
        int first,
        int pageSize,
        Map<String, SortMeta> sortBy,
        Map<String, FilterMeta> filterBy,
        ProjectionSpec<T, D> projection
    ) {
        return pageableQuery.get().findPage(em, entityClass, first, pageSize, sortBy, filterBy, projection);
    }

    public List<T> findAll() {
        return em
            .createQuery("SELECT e FROM " + entityClass.getSimpleName() + " e", entityClass)
            .getResultList();
    }

    public Optional<T> findById(Long id) {
        return Optional.ofNullable(em.find(entityClass, id));
    }

    public T save(T entity) {
        if (entity.getId() == null) {
            em.persist(entity);
            return entity;
        }
        return em.merge(entity);
    }

    public void delete(T entity) {
        em.remove(em.contains(entity) ? entity : em.merge(entity));
    }

    public List<T> searchByTitle(String title) {
        return em
            .createQuery(
                "SELECT e FROM " +
                entityClass.getSimpleName() +
                " e WHERE LOWER(e.title) LIKE LOWER(:title)",
                entityClass
            )
            .setParameter("title", "%" + title + "%")
            .getResultList();
    }
}
