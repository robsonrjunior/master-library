package com.github.robsonrjunior.master.library.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "books")
@Getter
@Setter
public class Book extends MediaItem {

    @Column(length = 150)
    private String author;

    @Column(length = 20)
    private String isbn;

    @Column(name = "page_count")
    private Integer pageCount;

    @Column(name = "published_year")
    private Integer publishedYear;
}
