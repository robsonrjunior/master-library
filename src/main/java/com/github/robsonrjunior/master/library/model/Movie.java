package com.github.robsonrjunior.master.library.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "movies")
@Getter
@Setter
public class Movie extends MediaItem {

    @Column(length = 150)
    private String director;

    @Column(name = "runtime_minutes")
    private Integer runtimeMinutes;

    @Column(name = "release_year")
    private Integer releaseYear;
}
