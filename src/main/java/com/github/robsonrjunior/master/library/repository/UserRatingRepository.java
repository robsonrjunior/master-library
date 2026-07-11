package com.github.robsonrjunior.master.library.repository;

import com.github.robsonrjunior.master.library.dto.RatingListItem;
import com.github.robsonrjunior.master.library.model.MediaItem;
import com.github.robsonrjunior.master.library.model.User;
import com.github.robsonrjunior.master.library.model.UserRating;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.inject.Instance;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.criteria.Root;
import jakarta.persistence.criteria.Selection;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import org.primefaces.model.FilterMeta;
import org.primefaces.model.SortMeta;

@ApplicationScoped
public class UserRatingRepository {

    private static final ProjectionSpec<UserRating, RatingListItem> LIST_PROJECTION = new ProjectionSpec<>() {
        @Override
        public Class<RatingListItem> projectionType() {
            return RatingListItem.class;
        }

        @Override
        public List<Selection<?>> selections(Root<UserRating> root) {
            return List.of(
                root.get("id"),
                root.join("user").get("displayName"),
                root.join("media").get("title"),
                root.get("score"),
                root.get("comment")
            );
        }

        @Override
        public Set<String> filterableFields() {
            return Set.of("score");
        }

        @Override
        public Set<String> sortableFields() {
            return Set.of("score");
        }

        @Override
        public Set<String> globalFilterFields() {
            return Set.of("user.displayName", "media.title", "comment");
        }
    };

    @PersistenceContext(unitName = "masterLibraryPU")
    private EntityManager em;

    @Inject
    private Instance<PageableQuery> pageableQuery;

    public Optional<UserRating> findById(Long id) {
        return Optional.ofNullable(em.find(UserRating.class, id));
    }

    public UserRating save(UserRating rating) {
        if (rating.getId() == null) {
            em.persist(rating);
            return rating;
        }
        return em.merge(rating);
    }

    public void delete(UserRating rating) {
        em.remove(em.contains(rating) ? rating : em.merge(rating));
    }

    public List<UserRating> findByUserId(Long userId) {
        return em
            .createQuery("SELECT r FROM UserRating r WHERE r.user.id = :userId", UserRating.class)
            .setParameter("userId", userId)
            .getResultList();
    }

    public List<UserRating> findByMediaId(Long mediaId) {
        return em
            .createQuery("SELECT r FROM UserRating r WHERE r.media.id = :mediaId", UserRating.class)
            .setParameter("mediaId", mediaId)
            .getResultList();
    }

    public Optional<UserRating> findByUserAndMedia(User user, MediaItem media) {
        try {
            return Optional.of(
                em
                    .createQuery(
                        "SELECT r FROM UserRating r WHERE r.user = :user AND r.media = :media",
                        UserRating.class
                    )
                    .setParameter("user", user)
                    .setParameter("media", media)
                    .setMaxResults(1)
                    .getSingleResult()
            );
        } catch (NoResultException e) {
            return Optional.empty();
        }
    }

    public Page<RatingListItem> findPage(
        int first,
        int pageSize,
        Map<String, SortMeta> sortBy,
        Map<String, FilterMeta> filterBy
    ) {
        return pageableQuery.get().findPage(em, UserRating.class, first, pageSize, sortBy, filterBy, LIST_PROJECTION);
    }
}
