dependencyResolutionManagement {
    // enable feature preview
    // ref. https://docs.gradle.org/7.0.2/userguide/platforms.html#sub:central-declaration-of-dependencies
    enableFeaturePreview("VERSION_CATALOGS")
    // default name is "libs", it only shows to set value here. (yes, it's a redundant line)
    defaultLibrariesExtensionName.set("libs")

    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
    }
}
rootProject.name = "android-jetpack-compose-template"
include(":app")
