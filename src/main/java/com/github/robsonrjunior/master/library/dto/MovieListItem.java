package com.github.robsonrjunior.master.library.dto;

public record MovieListItem(
    Long id,
    String title,
    String director,
    Integer runtimeMinutes,
    Integer releaseYear
) {}