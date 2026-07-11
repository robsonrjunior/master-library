package com.github.robsonrjunior.master.library.dto;

import com.github.robsonrjunior.master.library.validation.HalfStep;
import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.math.BigDecimal;

public class RatingUpdateRequest {

    @NotNull
    @DecimalMin("0.0")
    @DecimalMax("10.0")
    @HalfStep
    private BigDecimal score;

    @Size(max = 1000)
    private String comment;

    public BigDecimal getScore() {
        return score;
    }

    public void setScore(BigDecimal score) {
        this.score = score;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }
}
