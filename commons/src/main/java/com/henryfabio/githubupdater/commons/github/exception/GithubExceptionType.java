package com.henryfabio.githubupdater.commons.github.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * @author Henry Fábio
 * Github: https://github.com/HenryFabio
 */
@Getter
@RequiredArgsConstructor
public enum GithubExceptionType {

    AUTH_FAILED("The github auth has failed"),
    REPOSITORY_NOT_FOUND("The repository not found"),
    RELEASE_LIST_NOT_FOUND("The repository release list not found"),
    RELEASE_NOT_FOUND("The repository release not found"),
    ASSET_LIST_NOT_FOUND("The release asset list not found");

    private final String message;

    public GithubException create() {
        return new GithubException(message);
    }

}
