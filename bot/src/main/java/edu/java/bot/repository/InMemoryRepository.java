package edu.java.bot.repository;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import org.springframework.stereotype.Component;

@Component
public class InMemoryRepository implements Repository {
    private final Map<Long, Set<String>> userToResource = new HashMap<>();

    public boolean addResource(Long userId, String resource) {
        if (!userToResource.containsKey(userId) || userToResource.get(userId) == null) {
            userToResource.put(userId, new HashSet<>());
        }
        return userToResource.get(userId).add(resource);
    }

    public boolean removeResource(Long userId, String resource) {
        if (userToResource.containsKey(userId) && userToResource.get(userId) != null) {
            return userToResource.get(userId).remove(resource);
        }
        return false;
    }

    public Iterable<String> getResources(Long userId) {
        return userToResource.getOrDefault(userId, new HashSet<>());
    }

    public boolean isUserRegistered(Long userId) {
        return userToResource.containsKey(userId);
    }

    public Long registerUser(Long userId) {
        userToResource.put(userId, new HashSet<>());
        return userId;
    }

    public boolean checkResource(Long userId, String resource) {
        return userToResource.get(userId).contains(resource);
    }
}
