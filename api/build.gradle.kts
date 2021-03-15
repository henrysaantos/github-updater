dependencies {
    val lombokVersion: String by project
    val githubApiVersion: String by project
    val junitVersion: String by project

    api("org.kohsuke:github-api:$githubApiVersion")

    compileOnly("org.projectlombok:lombok:$lombokVersion")
    annotationProcessor("org.projectlombok:lombok:$lombokVersion")

    testImplementation("org.junit.jupiter:junit-jupiter-api:$junitVersion")
    testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine:$junitVersion")
}