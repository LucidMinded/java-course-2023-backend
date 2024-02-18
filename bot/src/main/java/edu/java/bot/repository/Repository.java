package edu.java.bot.repository;

public interface Repository {
    boolean addResource(Long userId, String resource);

    boolean removeResource(Long userId, String resource);

    Iterable<String> getResources(Long userId);

    boolean isUserRegistered(Long userId);

    Long registerUser(Long userId);

    boolean checkResource(Long userId, String resource);
}
