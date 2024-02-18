package edu.java.bot.service;

import edu.java.bot.repository.Repository;
import lombok.AllArgsConstructor;

@org.springframework.stereotype.Service
@AllArgsConstructor
public class Service {
    private final Repository repository;

    public Long registerUser(Long id) {
        return repository.registerUser(id);
    }

    public void unregisterUser() {
        // remove from db
    }

    public boolean isUserRegistered(Long id) {
        return repository.isUserRegistered(id);
    }

    public boolean addResource(Long userId, String resource) {
        return repository.addResource(userId, resource);
    }

    public boolean removeResource(Long userId, String resource) {
        return repository.removeResource(userId, resource);
    }

    public Iterable<String> getResources(Long userId) {
        return repository.getResources(userId);
    }

    public boolean checkResource(Long userId, String resource) {
        return repository.checkResource(userId, resource);
    }
}
