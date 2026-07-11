package com.github.robsonrjunior.master.library.repository;

import com.github.robsonrjunior.master.library.model.MediaItem;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Order;
import jakarta.persistence.criteria.Path;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import jakarta.persistence.criteria.Selection;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.primefaces.model.FilterMeta;
import org.primefaces.model.SortMeta;

public abstract class MediaRepository<T extends MediaItem> {

    @PersistenceContext(unitName = "masterLibraryPU")
    protected EntityManager em;

    private final Class<T> entityClass;

    protected MediaRepository(Class<T> entityClass) {
        this.entityClass = entityClass;
    }

    protected <D> Page<D> findPage(
        int first,
        int pageSize,
        java.util.Map<String, SortMeta> sortBy,
        java.util.Map<String, FilterMeta> filterBy,
        ProjectionSpec<T, D> projection
    ) {
        CriteriaBuilder cb = em.getCriteriaBuilder();

        CriteriaQuery<D> contentQuery = cb.createQuery(projection.projectionType());
        Root<T> contentRoot = contentQuery.from(entityClass);
        List<Selection<?>> selections = projection.selections(contentRoot);
        contentQuery.select(cb.construct(projection.projectionType(), selections.toArray(new Selection<?>[0])));
        List<Predicate> contentPredicates = buildPredicates(cb, contentRoot, filterBy, projection);
        if (!contentPredicates.isEmpty()) {
            contentQuery.where(contentPredicates.toArray(new Predicate[0]));
        }
        contentQuery.orderBy(buildOrder(cb, contentRoot, sortBy, projection));

        List<D> content;
        if (pageSize <= 0) {
            content = List.of();
        } else {
            TypedQuery<D> query = em.createQuery(contentQuery);
            query.setFirstResult(Math.max(first, 0));
            query.setMaxResults(pageSize);
            content = query.getResultList();
        }

        CriteriaQuery<Long> countQuery = cb.createQuery(Long.class);
        Root<T> countRoot = countQuery.from(entityClass);
        countQuery.select(cb.count(countRoot));
        List<Predicate> countPredicates = buildPredicates(cb, countRoot, filterBy, projection);
        if (!countPredicates.isEmpty()) {
            countQuery.where(countPredicates.toArray(new Predicate[0]));
        }
        long total = em.createQuery(countQuery).getSingleResult();

        return new Page<>(content, total);
    }

    private <D> List<Predicate> buildPredicates(
        CriteriaBuilder cb,
        Root<T> root,
        java.util.Map<String, FilterMeta> filterBy,
        ProjectionSpec<T, D> projection
    ) {
        List<Predicate> predicates = new ArrayList<>();
        if (filterBy == null) {
            return predicates;
        }
        for (FilterMeta filter : filterBy.values()) {
            Object rawValue = filter.getFilterValue();
            if (rawValue == null || rawValue.toString().isBlank()) {
                continue;
            }
            String value = rawValue.toString().trim();
            String field = filter.getField();

            if (filter.isGlobalFilter() || FilterMeta.GLOBAL_FILTER_KEY.equals(field)) {
                List<Predicate> ors = new ArrayList<>();
                for (String globalField : projection.globalFilterFields()) {
                    Predicate predicate = columnPredicate(cb, root.get(globalField), value);
                    if (predicate != null) {
                        ors.add(predicate);
                    }
                }
                if (!ors.isEmpty()) {
                    predicates.add(cb.or(ors.toArray(new Predicate[0])));
                }
            } else if (field != null && projection.filterableFields().contains(field)) {
                Predicate predicate = columnPredicate(cb, root.get(field), value);
                if (predicate != null) {
                    predicates.add(predicate);
                }
            }
        }
        return predicates;
    }

    private Predicate columnPredicate(CriteriaBuilder cb, Path<?> path, String value) {
        Class<?> type = path.getJavaType();
        if (type == String.class) {
            return cb.like(cb.lower(path.as(String.class)), "%" + value.toLowerCase() + "%");
        }
        try {
            if (type == Integer.class || type == int.class) {
                return cb.equal(path, Integer.valueOf(value));
            }
            if (type == Long.class || type == long.class) {
                return cb.equal(path, Long.valueOf(value));
            }
        } catch (NumberFormatException ignored) {
            return null;
        }
        return null;
    }

    private <D> List<Order> buildOrder(
        CriteriaBuilder cb,
        Root<T> root,
        java.util.Map<String, SortMeta> sortBy,
        ProjectionSpec<T, D> projection
    ) {
        List<Order> orders = new ArrayList<>();
        if (sortBy != null) {
            for (SortMeta sort : sortBy.values()) {
                String field = sort.getField();
                if (field == null || !projection.sortableFields().contains(field)) {
                    continue;
                }
                if (sort.getOrder() == null || sort.getOrder().isUnsorted()) {
                    continue;
                }
                orders.add(sort.getOrder().isAscending() ? cb.asc(root.get(field)) : cb.desc(root.get(field)));
            }
        }
        if (orders.isEmpty()) {
            orders.add(cb.asc(root.get("id")));
        }
        return orders;
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
