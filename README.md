# android-jetpack-compose-template

## Dev Info
### Dependencies
This project using [Version Catalog](https://docs.gradle.org/7.2/userguide/platforms.html#sub:central-declaration-of-dependencies).
For more information, check original document.

### Package version update
To check versions of libraries, run following command.
(This command does NOT update any gradle files. Only print info)

```bash
$ ./gradlew dependencyUpdates
```

This is a func provided by [gradle-versions-plugin](https://github.com/ben-manes/gradle-versions-plugin).
- Currently, we cannot detect update of ktlint. so it need to be checked manually.

### Code formatting
The CI uses [Spotless](https://github.com/diffplug/spotless) to check if your code is formatted correctly and contains the right licenses.
Internally, Spotless uses [ktlint](https://github.com/pinterest/ktlint) to check the formatting of your code.
To set up ktlint correctly with Android Studio, follow one of the [listed setup options](https://github.com/pinterest/ktlint#-with-intellij-idea).

Before committing your code, run `./gradlew app:spotlessApply` to automatically format your code.

(if you want to only check lint, `./gradlew app:spotlessCheck` is available)
