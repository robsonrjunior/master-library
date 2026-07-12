package com.github.robsonrjunior.master.library.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "database_config")
@Getter
@Setter
public class DatabaseConfig {

    @Id
    private Long id = 1L;

    @Column(nullable = false)
    private boolean seeded;
}
