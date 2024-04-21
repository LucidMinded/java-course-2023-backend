package edu.java;

import lombok.experimental.UtilityClass;

@UtilityClass
public class StackoverflowPathParser {
    @SuppressWarnings("MagicNumber")
    public static ParsedPath parse(String path) {
        // path = /questions/id/title
        String[] parts = path.split("/");
        if (parts.length < 4) {
            return null;
        }
        return new ParsedPath(parts[2]);
    }

    public record ParsedPath(String id) {
    }
}
