plugins {
    alias(libs.plugins.android.application)
}

val generatedAssetsDir = layout.buildDirectory.dir("generated/sourceBackup/assets")

val selectedFiles = listOf(
    "src/main/java/com/my/mert/MainActivity.java",
    "src/main/res/layout/activity_main.xml",
    "src/main/java/com/my/mert/InputGridAdapter.java",
    "src/main/java/com/my/mert/OutputGridAdapter.java"
)

val copySelectedSourceFiles = tasks.register<Copy>("copySelectedSourceFiles") {
    selectedFiles.forEach { filePath ->
        val fileObj = file(filePath)
        val relativePath = filePath.removePrefix("src/main/")
        val parentDir = if (relativePath.contains("/")) relativePath.substringBeforeLast("/") else ""
        from(fileObj) {
            into("source_backup/$parentDir")
        }
    }
    into(generatedAssetsDir)
}

android {
    namespace = "laki.vinoxsingle.v1"
    compileSdk = 35

    defaultConfig {
        applicationId = "laki.vinoxsingle.v1"
        minSdk = 28
        targetSdk = 35
        versionCode = 1
        versionName = "2026.10.29"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    sourceSets {
        getByName("main") {
            assets.srcDir(generatedAssetsDir.get().asFile)
        }
    }

    signingConfigs {
        create("release") {
            storeFile = file("release-key.jks")
            storePassword = "password123"
            keyAlias = "mykey"
            keyPassword = "password123"
        }
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            signingConfig = signingConfigs.getByName("release")
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
}

tasks.named("preBuild") {
    dependsOn(copySelectedSourceFiles)
}

dependencies {
    implementation(libs.appcompat)
    implementation(libs.material)
    implementation(libs.activity)
    implementation(libs.constraintlayout)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
}
