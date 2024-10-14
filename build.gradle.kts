plugins {
    kotlin("jvm") version "2.0.20"

    kotlin("plugin.compose")
    id("org.jetbrains.compose")
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

    // https://mvnrepository.com/artifact/org.graphstream/gs-core
    implementation("org.graphstream:gs-core:2.0")

    // https://mvnrepository.com/artifact/org.graphstream/gs-ui-swing
    implementation("org.graphstream:gs-ui-swing:2.0")

    implementation(compose.desktop.currentOs)

    testImplementation("org.jetbrains.kotlin:kotlin-test-junit5")

    implementation("androidx.compose.material:material-icons-extended:1.7.3")
}

tasks.test {
    useJUnitPlatform()
}
kotlin {
    jvmToolchain(21)
}