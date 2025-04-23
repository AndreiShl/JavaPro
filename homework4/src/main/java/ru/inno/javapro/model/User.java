package ru.inno.javapro.model;

import lombok.Builder;

@Builder(toBuilder = true)
public record User (Long id, String username) { }
