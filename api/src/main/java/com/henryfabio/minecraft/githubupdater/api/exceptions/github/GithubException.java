package com.henryfabio.minecraft.githubupdater.api.exceptions.github;

public abstract class GithubException extends Exception {

    public GithubException(String message) {
        super(message);
    }

    public GithubException(String message, Throwable cause) {
        super(message, cause);
    }

}
