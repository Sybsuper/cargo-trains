plugins {
    kotlin("jvm") version "2.3.0"
    application
}

group = "com.sybsuper"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    testImplementation(kotlin("test"))
}

kotlin {
    jvmToolchain(25)
}

tasks.test {
    useJUnitPlatform()
}

tasks.run {
    standardInput = System.`in`
}

application {
    mainClass.set("com.sybsuper.MainKt")
}
