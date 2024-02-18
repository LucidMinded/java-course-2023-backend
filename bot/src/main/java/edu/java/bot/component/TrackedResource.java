package edu.java.bot.component;

public enum TrackedResource {
    GITHUB {
        @Override
        public String getHost() {
            return "github.com";
        }
    },
    STACKOVERFLOW {
        @Override
        public String getHost() {
            return "stackoverflow.com";
        }
    };

    public static TrackedResource getResourceFromHost(String host) {
        for (TrackedResource resource : TrackedResource.values()) {
            if (resource.getHost().equals(host)) {
                return resource;
            }
        }
        return null;
    }

    public abstract String getHost();
}
