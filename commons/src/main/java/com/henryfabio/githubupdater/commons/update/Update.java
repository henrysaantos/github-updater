package com.henryfabio.githubupdater.commons.update;

import lombok.Data;

import java.io.File;

/**
 * @author Henry Fábio
 * Github: https://github.com/HenryFabio
 */
@Data
public final class Update {

    private final String version;
    private final File file;

}
