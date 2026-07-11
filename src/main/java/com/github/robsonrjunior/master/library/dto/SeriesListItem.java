package com.github.robsonrjunior.master.library.dto;

import com.github.robsonrjunior.master.library.model.SeriesStatus;

public record SeriesListItem(
    Long id,
    String title,
    Integer numberOfSeasons,
    Integer numberOfEpisodes,
    Integer startYear,
    SeriesStatus status
) {}