package edu.java;

import lombok.experimental.UtilityClass;

@UtilityClass
public class GithubPathParser {
    public static ParsedPath parse(String path) {
        String[] parts = path.split("/");
        if (parts.length < 3) {
            return null;
        }
        return new ParsedPath(parts[1], parts[2]);
    }

    public record ParsedPath(String owner, String repo) {
    }
}
