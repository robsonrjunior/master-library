package com.github.robsonrjunior.master.library.service;

import com.github.robsonrjunior.master.library.exception.ResourceNotFoundException;
import com.github.robsonrjunior.master.library.model.MediaItem;
import com.github.robsonrjunior.master.library.repository.MediaRepository;
import com.github.robsonrjunior.master.library.repository.UserRatingRepository;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import java.util.List;

public abstract class MediaService<T extends MediaItem> {

    @Inject
    protected UserRatingRepository ratingRepository;

    protected abstract MediaRepository<T> repository();

    public List<T> list() {
        return repository().findAll();
    }

    public List<T> search(String title) {
        return repository().searchByTitle(title);
    }

    public T get(Long id) {
        return repository()
            .findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Media item not found: " + id));
    }

    @Transactional
    public T create(T entity) {
        entity.setId(null);
        return repository().save(entity);
    }

    @Transactional
    public T update(Long id, T entity) {
        get(id);
        entity.setId(id);
        return repository().save(entity);
    }

    @Transactional
    public void delete(Long id) {
        T entity = get(id);
        ratingRepository.findByMediaId(id).forEach(ratingRepository::delete);
        repository().delete(entity);
    }
}
