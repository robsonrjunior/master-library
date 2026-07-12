package com.github.robsonrjunior.master.library.dto;

import com.github.robsonrjunior.master.library.model.Role;
import java.time.LocalDateTime;

public record UserListItem(
    Long id,
    String username,
    String email,
    String displayName,
    Role role,
    LocalDateTime createdAt
) {}