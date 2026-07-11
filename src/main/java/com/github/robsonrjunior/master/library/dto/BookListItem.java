package com.github.robsonrjunior.master.library.dto;

public record BookListItem(
    Long id,
    String title,
    String author,
    String isbn,
    Integer pageCount,
    Integer publishedYear
) {}
