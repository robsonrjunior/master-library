package com.github.robsonrjunior.master.library.service;

import com.github.robsonrjunior.master.library.dto.UserListItem;
import com.github.robsonrjunior.master.library.exception.DuplicateResourceException;
import com.github.robsonrjunior.master.library.exception.ResourceNotFoundException;
import com.github.robsonrjunior.master.library.model.User;
import com.github.robsonrjunior.master.library.repository.Page;
import com.github.robsonrjunior.master.library.repository.UserRatingRepository;
import com.github.robsonrjunior.master.library.repository.UserRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import java.util.List;
import java.util.Map;
import org.primefaces.model.FilterMeta;
import org.primefaces.model.SortMeta;

@ApplicationScoped
public class UserService {

    @Inject
    private UserRepository repository;

    @Inject
    private UserRatingRepository ratingRepository;

    public List<User> list() {
        return repository.findAll();
    }

    public Page<UserListItem> findPage(
        int first,
        int pageSize,
        Map<String, SortMeta> sortBy,
        Map<String, FilterMeta> filterBy
    ) {
        return repository.findPage(first, pageSize, sortBy, filterBy);
    }

    public User get(Long id) {
        return repository
            .findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("User not found: " + id));
    }

    @Transactional
    public User create(User user) {
        user.setId(null);
        checkUnique(user, null);
        return repository.save(user);
    }

    @Transactional
    public User update(Long id, User user) {
        get(id);
        checkUnique(user, id);
        user.setId(id);
        return repository.save(user);
    }

    @Transactional
    public void delete(Long id) {
        User user = get(id);
        ratingRepository.findByUserId(id).forEach(ratingRepository::delete);
        repository.delete(user);
    }

    private void checkUnique(User user, Long currentId) {
        repository
            .findByUsername(user.getUsername())
            .filter(existing -> !existing.getId().equals(currentId))
            .ifPresent(existing -> {
                throw new DuplicateResourceException(
                    "Username already in use: " + user.getUsername()
                );
            });
        repository
            .findByEmail(user.getEmail())
            .filter(existing -> !existing.getId().equals(currentId))
            .ifPresent(existing -> {
                throw new DuplicateResourceException("Email already in use: " + user.getEmail());
            });
    }
}
