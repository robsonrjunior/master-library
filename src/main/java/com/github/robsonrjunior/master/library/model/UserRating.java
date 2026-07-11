package com.github.robsonrjunior.master.library.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import com.github.robsonrjunior.master.library.validation.HalfStep;
import java.math.BigDecimal;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(
    name = "user_ratings",
    uniqueConstraints = @UniqueConstraint(
        name = "uk_user_ratings_user_media",
        columnNames = { "user_id", "media_id" }
    )
)
@Getter
@Setter
public class UserRating {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @ManyToOne(optional = false)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @NotNull
    @ManyToOne(optional = false)
    @JoinColumn(name = "media_id", nullable = false)
    private MediaItem media;

    @NotNull
    @DecimalMin("0.0")
    @DecimalMax("10.0")
    @HalfStep
    @Column(nullable = false, precision = 3, scale = 1)
    private BigDecimal score;

    @Size(max = 1000)
    @Column(length = 1000)
    private String comment;
}
