package com.henryfabio.githubupdater.commons.configuration;

import lombok.Builder;
import lombok.Data;

/**
 * @author Henry Fábio
 * Github: https://github.com/HenryFabio
 */
@Builder(builderClassName = "Builder")
@Data
public final class UpdaterConfiguration {

    private final String
            githubToken,
            githubUser;

}
