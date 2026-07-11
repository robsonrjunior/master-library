package com.github.robsonrjunior.master.library.dto;

public record UserListItem(
    Long id,
    String username,
    String email,
    String displayName
) {}