package edu.java.bot.component;

public enum TrackedResource {
    GITHUB("github.com"),
    STACKOVERFLOW("stackoverflow.com");

    private final String host;

    TrackedResource(String host) {
        this.host = host;
    }

    public String getHost() {
        return host;
    }
}
