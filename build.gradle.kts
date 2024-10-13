plugins {
    kotlin("jvm") version "2.0.20"
}

group = "de.olk90"
version = "1.0-SNAPSHOT"

repositories {
    google()
    mavenCentral()
    maven("https://maven.pkg.jetbrains.space/public/p/compose/dev")
}

java {
    sourceCompatibility = JavaVersion.VERSION_11
    targetCompatibility = JavaVersion.VERSION_11
}

dependencies {

    // https://mvnrepository.com/artifact/org.graphstream/gs-core
    implementation("org.graphstream:gs-core:2.0")

    // https://mvnrepository.com/artifact/org.graphstream/gs-ui-swing
    implementation("org.graphstream:gs-ui-swing:2.0")

    testImplementation("org.jetbrains.kotlin:kotlin-test-junit5")
}

tasks.test {
    useJUnitPlatform()
}
kotlin {
    jvmToolchain(11)
}