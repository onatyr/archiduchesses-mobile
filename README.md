# turboplant mobile

**TurboPlant** is a mobile app to help people sharing the sames plants to take care of them together.
It is developped with Kotlin Multiplatform and target both Android and IOS.
You can check the [turboplant back repository](https://github.com/onatyr/turboplant-back) which contains the code of the API used by the app.

## Installation
1. For an initial setup with the required tools please refer to the [Jetbrain Documentation - Setup](https://www.jetbrains.com/help/kotlin-multiplatform-dev/multiplatform-setup.html)
2. Clone the repository
```
git clone git@github.com:onatyr/turboplant-mobile.git
```
4. Sync dependencies
```
./gradlew --refresh-dependencies
```

## Run tests

```
./gradlew test
```

## Architecture
* `/composeApp` is for code that will be shared across the Compose Multiplatform applications.
  It contains several subfolders:
  - `commonMain` is for code that’s common for all targets.
  - Other folders are for Kotlin code that will be compiled for only the platform indicated in the folder name.

* `/iosApp` contains iOS applications. Even if you’re sharing your UI with Compose Multiplatform, 
  you need this entry point for your iOS app.

## Deployment
The app is deployed automatically on each new push on the **prod** branch.
However if you need to deploy it manually these are the steps:

1. Add the following variables in the local.properties file with the right values
```
KEY_ALIAS=key-alias
KEY_PASSWORD=key-password
STORE_FILE_PATH=/Users/Me/StoreFilePath
STORE_PASSWORD=store-password
```
2. Upgrade the version code and the version name in the app level build.gradle file
```
defaultConfig {
    applicationId = "fr.onat.turboplant"
    minSdk = libs.versions.android.minSdk.get().toInt()
    targetSdk = libs.versions.android.targetSdk.get().toInt()
    versionCode = 2
    versionName = "1.1"
    }
```

3. Build the app with the following command
```
./gradlew assembleRelease
```
4. Append the version name to the file name and transfer it on the distant server at the following path
```
/usr/share/nginx/www/fdroid/repo
```
5. Update the metadata by executing the following command at the same path on the distant server
```
fdroid update -c
```
