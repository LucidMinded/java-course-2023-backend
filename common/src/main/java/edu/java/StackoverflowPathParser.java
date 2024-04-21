package edu.java;

public class StackoverflowPathParser {
    public static ParsedPath parse(String path) {
        String[] parts = path.split("/");
        if (parts.length < 4) {
            return null;
        }
        return new ParsedPath(parts[2]);
    }

    public record ParsedPath(String id) {
    }
}
