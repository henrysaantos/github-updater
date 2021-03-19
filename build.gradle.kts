plugins {
    kotlin("jvm") version "1.4.21" apply false
    id("maven")
}

allprojects {

    group = "com.henryfabio.minecraft"
    version = "1.0.0"

    repositories {
        mavenCentral()
    }

}

subprojects {

    apply(plugin = "org.jetbrains.kotlin.jvm")
    apply(plugin = "maven")

}