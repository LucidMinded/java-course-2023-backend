package edu.java;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum TrackedLink {
    GITHUB("github.com"),
    STACKOVERFLOW("stackoverflow.com");

    private final String host;
}
