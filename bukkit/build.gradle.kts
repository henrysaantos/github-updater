repositories {
    maven("https://hub.spigotmc.org/nexus/content/repositories/snapshots/")
    maven("https://oss.sonatype.org/content/groups/public/")
}

dependencies {
    val spigotVersion: String by project

    api(project(":common"))
    compileOnly("org.spigotmc:spigot-api:$spigotVersion")
}
