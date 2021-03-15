package com.henryfabio.minecraft.githubupdater.api.exceptions.github;

public class GithubAuthenticationException extends GithubException {

    private static final String EXCEPTION_MESSAGE = "Github authentication failed!";

    public GithubAuthenticationException() {
        super(EXCEPTION_MESSAGE);
    }

    public GithubAuthenticationException(Throwable cause) {
        super(EXCEPTION_MESSAGE, cause);
    }

}
