import org.gradle.api.tasks.testing.logging.TestLogEvent

plugins {
    alias(libs.plugins.dokka)
    alias(libs.plugins.gitSemVer)
    alias(libs.plugins.kotlin.jvm)
    alias(libs.plugins.kotlin.qa)
    alias(libs.plugins.taskTree)
}

buildscript {
    configurations.classpath {
        resolutionStrategy.activateDependencyLocking()
    }
}

group = "org.danilopianini"

repositories {
    mavenCentral()
}

dependencies {
    implementation(kotlin("stdlib"))
    testImplementation(libs.bundles.kotlin.testing)
}

dependencyLocking {
    lockAllConfigurations()
}

kotlin {
    compilerOptions {
        allWarningsAsErrors = true
        freeCompilerArgs = listOf("-opt-in=kotlin.RequiresOptIn")
    }
}

tasks.test {
    useJUnitPlatform()
    testLogging {
        showStandardStreams = true
        showCauses = true
        showStackTraces = true
        events(
            *TestLogEvent.entries.toTypedArray(),
        )
        exceptionFormat = org.gradle.api.tasks.testing.logging.TestExceptionFormat.FULL
    }
}
