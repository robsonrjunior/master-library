package com.github.robsonrjunior.master.library.repository;

import com.github.robsonrjunior.master.library.model.DatabaseConfig;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

@ApplicationScoped
public class DatabaseConfigRepository {

    @PersistenceContext(unitName = "masterLibraryPU")
    private EntityManager em;

    public DatabaseConfig get() {
        DatabaseConfig config = em.find(DatabaseConfig.class, 1L);
        if (config == null) {
            config = new DatabaseConfig();
        }
        return config;
    }

    public void save(DatabaseConfig config) {
        if (em.find(DatabaseConfig.class, config.getId()) == null) {
            em.persist(config);
        } else {
            em.merge(config);
        }
    }
}
