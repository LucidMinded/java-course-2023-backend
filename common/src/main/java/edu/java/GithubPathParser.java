package edu.java;

import lombok.experimental.UtilityClass;

@UtilityClass
public class GithubPathParser {
    @SuppressWarnings("MagicNumber")
    public static ParsedPath parse(String path) {
        // path = /owner/repo
        String[] parts = path.split("/");
        if (parts.length < 3) {
            return null;
        }
        return new ParsedPath(parts[1], parts[2]);
    }

    public record ParsedPath(String owner, String repo) {
    }
}
