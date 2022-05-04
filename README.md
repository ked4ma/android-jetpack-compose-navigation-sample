# android-jetpack-compose-navigation-sample

## :scroll: Description
This is a sample app try to use options for Jetpack Compose Navigation.

## :bulb: Motivation and Context
When use Jetpack Compose Navigation, there were difficulty to understand each option's behavior.<br>
To help checking this, I created this app.

## :factory: supported functions
- [x] navigate
  - [x] launchSingleTop
  - [x] popUpTo
    - [x] inclusive
    - [x] saveState
  - [x] restoreState
- [ ] popBackStack

## :camera_flash: Screenshots
<!-- You can add more screenshots here if you like -->
<img src="/screenshots/image1.png" width="260">&emsp;<img src="/screenshots/image2.png" width="260">

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
