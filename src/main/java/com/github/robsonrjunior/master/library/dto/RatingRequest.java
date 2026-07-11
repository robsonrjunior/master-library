package com.github.robsonrjunior.master.library.dto;

import com.github.robsonrjunior.master.library.validation.HalfStep;
import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.math.BigDecimal;

public class RatingRequest {

    @NotNull
    private Long userId;

    @NotNull
    private Long mediaId;

    private String mediaType;

    @NotNull
    @DecimalMin("0.0")
    @DecimalMax("10.0")
    @HalfStep
    private BigDecimal score;

    @Size(max = 1000)
    private String comment;

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getMediaId() {
        return mediaId;
    }

    public void setMediaId(Long mediaId) {
        this.mediaId = mediaId;
    }

    public String getMediaType() {
        return mediaType;
    }

    public void setMediaType(String mediaType) {
        this.mediaType = mediaType;
    }

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
