plugins {
    kotlin("jvm") version "2.0.20"
    id("org.jetbrains.compose") version "1.7.3"
    id("org.jetbrains.kotlin.plugin.compose") version "2.0.20"
}

group = "de.olk90"
version = "1.0-SNAPSHOT"

repositories {
    google()
    mavenCentral()
    maven("https://maven.pkg.jetbrains.space/public/p/compose/dev")
}

java {
    sourceCompatibility = JavaVersion.VERSION_21
    targetCompatibility = JavaVersion.VERSION_21
}

dependencies {
    // GraphStream libraries for graph rendering
    implementation("org.graphstream:gs-core:2.0")
    implementation("org.graphstream:gs-ui-swing:2.0")

    // JetBrains Compose for Desktop
    implementation(compose.desktop.currentOs)

    // Material Icons for Compose
    implementation("androidx.compose.material:material-icons-extended:1.6.0")

    // Kotlin testing with JUnit 5
    testImplementation("org.jetbrains.kotlin:kotlin-test-junit5")
}

tasks.test {
    useJUnitPlatform()
}
kotlin {
    jvmToolchain(21)
}