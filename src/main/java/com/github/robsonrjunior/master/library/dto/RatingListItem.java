package com.github.robsonrjunior.master.library.dto;

import java.math.BigDecimal;

public record RatingListItem(
    Long id,
    String userName,
    String mediaTitle,
    BigDecimal score,
    String comment
) {}