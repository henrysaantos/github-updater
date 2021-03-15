package com.henryfabio.minecraft.githubupdater.api.credentials;

import lombok.Builder;
import lombok.Data;
import lombok.experimental.Accessors;

@Accessors(fluent = true)
@Builder
@Data
public final class GithubCredentials {

    private final String username;
    private final String accessToken;

}
