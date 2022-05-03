// Top-level build file where you can add configuration options common to all sub-projects/modules.
buildscript {
    repositories {
        google()
        mavenCentral()
    }
    dependencies {
        classpath(libs.android.build.gradle)
        classpath(libs.kotlin.gradle)
        classpath(libs.oss.license.classpath)

        // NOTE: Do not place your application dependencies here; they belong
        // in the individual module build.gradle files
    }
}

// ref: https://youtrack.jetbrains.com/issue/KTIJ-19369#focus=Comments-27-5181027.0-0
@Suppress("DSL_SCOPE_VIOLATION")
plugins {
    alias(libs.plugins.spotless)
    alias(libs.plugins.gradle.versions.management)
}

subprojects {
    repositories {
        google()
        mavenCentral()
        maven(url = "https://kotlin.bintray.com/kotlinx")
    }

    apply(plugin = rootProject.libs.plugins.spotless.get().pluginId)

    spotless {
        kotlin {
            target ("**/*.kt")
            targetExclude("bin/**/*.kt")
            ktlint(libs.versions.ktlint.get())
            licenseHeaderFile(rootProject.file("spotless/copyright.kt"))
        }
    }
}
