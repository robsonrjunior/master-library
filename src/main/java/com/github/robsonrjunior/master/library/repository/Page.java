package com.github.robsonrjunior.master.library.repository;

import java.util.List;

public record Page<T>(List<T> content, long totalElements) {}
