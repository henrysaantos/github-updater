import com.github.jengelman.gradle.plugins.shadow.tasks.ShadowJar

repositories {
    maven("https://hub.spigotmc.org/nexus/content/repositories/snapshots/")
    maven("https://oss.sonatype.org/content/groups/public/")
}

plugins {
    id("net.minecrell.plugin-yml.bukkit") version "0.3.0"
    id("com.github.johnrengelman.shadow") version "6.1.0"
}

dependencies {
    val spigotVersion: String by project

    implementation(project(":bukkit"))
    compileOnly("org.spigotmc:spigot-api:$spigotVersion")
}

tasks {
    withType<ShadowJar> {
        archiveFileName.set("${rootProject.name}-${project.name}-${project.version}.jar")
    }

    named("build") {
        dependsOn("shadowJar")
    }
}

bukkit {
    main = "com.henryfabio.minecraft.githubupdater.example.Main"
    author = "Henry Fábio"
    version = "0.0.0"
}