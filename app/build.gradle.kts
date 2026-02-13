import org.jetbrains.kotlin.gradle.dsl.JvmTarget
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

repositories {
    google()
    mavenCentral()
}

plugins {
    id("com.android.application")
    id("kotlin-android")
}

android {
    namespace = "test.android.routes"
    compileSdk = 36

    defaultConfig {
        applicationId = namespace
        minSdk = 24
        targetSdk = compileSdk
        versionCode = 1
        versionName = "0.0.$versionCode"
    }

    buildTypes {
        getByName("debug") {
            applicationIdSuffix = ".$name"
            versionNameSuffix = "-$name"
            isMinifyEnabled = false
            isShrinkResources = false
        }
    }

    buildFeatures.buildConfig = true
}

androidComponents.onVariants { variant ->
    val output = variant.outputs.single()
    check(output is com.android.build.api.variant.impl.VariantOutputImpl)
    output.outputFileName = "${rootProject.name}-${output.versionName.get()}-${output.versionCode.get()}.apk"
    afterEvaluate {
        tasks.getByName<JavaCompile>("compile${variant.name.replaceFirstChar(Character::toUpperCase)}JavaWithJavac") {
            targetCompatibility = "17"
        }
        tasks.getByName<KotlinCompile>("compile${variant.name.replaceFirstChar(Character::toUpperCase)}Kotlin") {
            compilerOptions.jvmTarget = JvmTarget.fromTarget("17")
        }
    }
}

dependencies {
    implementation("androidx.activity:activity:1.12.4")
}
