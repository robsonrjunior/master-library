package com.github.robsonrjunior.master.library.service;

import com.github.robsonrjunior.master.library.dto.RatingListItem;
import com.github.robsonrjunior.master.library.exception.ResourceNotFoundException;
import com.github.robsonrjunior.master.library.model.MediaItem;
import com.github.robsonrjunior.master.library.model.User;
import com.github.robsonrjunior.master.library.model.UserRating;
import com.github.robsonrjunior.master.library.repository.MediaItemRepository;
import com.github.robsonrjunior.master.library.repository.Page;
import com.github.robsonrjunior.master.library.repository.UserRatingRepository;
import com.github.robsonrjunior.master.library.repository.UserRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import org.primefaces.model.FilterMeta;
import org.primefaces.model.SortMeta;

@ApplicationScoped
public class UserRatingService {

    @Inject
    private UserRatingRepository repository;

    @Inject
    private UserRepository userRepository;

    @Inject
    private MediaItemRepository mediaItemRepository;

    @Transactional
    public UserRating get(Long id) {
        return repository
            .findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Rating not found: " + id));
    }

    public Page<RatingListItem> findPage(
        int first,
        int pageSize,
        Map<String, SortMeta> sortBy,
        Map<String, FilterMeta> filterBy
    ) {
        return repository.findPage(first, pageSize, sortBy, filterBy);
    }

    public List<UserRating> listByUser(Long userId) {
        return repository.findByUserId(userId);
    }

    public List<UserRating> listByMedia(Long mediaId) {
        return repository.findByMediaId(mediaId);
    }

    @Transactional
    public UserRating rate(Long userId, Long mediaId, BigDecimal score, String comment) {
        User user = requireUser(userId);
        MediaItem media = requireMedia(mediaId);

        UserRating rating = repository
            .findByUserAndMedia(user, media)
            .orElseGet(() -> {
                UserRating created = new UserRating();
                created.setUser(user);
                created.setMedia(media);
                return created;
            });
        rating.setScore(score);
        rating.setComment(comment);
        return repository.save(rating);
    }

    @Transactional
    public UserRating update(Long id, BigDecimal score, String comment) {
        UserRating rating = get(id);
        rating.setScore(score);
        rating.setComment(comment);
        return repository.save(rating);
    }

    @Transactional
    public void delete(Long id) {
        repository.delete(get(id));
    }

    private User requireUser(Long userId) {
        return userRepository
            .findById(userId)
            .orElseThrow(() -> new ResourceNotFoundException("User not found: " + userId));
    }

    private MediaItem requireMedia(Long mediaId) {
        return mediaItemRepository
            .findById(mediaId)
            .orElseThrow(() -> new ResourceNotFoundException("Media item not found: " + mediaId));
    }
}
