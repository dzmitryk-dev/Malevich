import io.gitlab.arturbosch.detekt.Detekt
import org.jetbrains.compose.desktop.application.dsl.TargetFormat
import org.jetbrains.kotlin.gradle.ExperimentalWasmDsl
import org.jetbrains.kotlin.gradle.targets.js.webpack.KotlinWebpackConfig
import org.jetbrains.kotlin.gradle.dsl.JvmTarget

plugins {
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.androidApplication)
    alias(libs.plugins.jetbrainsCompose)
    alias(libs.plugins.compose.compiler)
    alias(libs.plugins.detekt)
    alias(libs.plugins.kotlinSerialization)
}

kotlin {
    @OptIn(ExperimentalWasmDsl::class)
    wasmJs {
        browser {
            commonWebpackConfig {
                outputFileName = "composeApp.js"
                devServer = (devServer ?: KotlinWebpackConfig.DevServer()).apply {
                    static = (static ?: mutableListOf()).apply {
                        // Serve sources to debug inside browser
                        add(project.projectDir.path)
                    }
                }
            }
        }
        binaries.executable()
    }
    
    androidTarget {
        @OptIn(org.jetbrains.kotlin.gradle.ExperimentalKotlinGradlePluginApi::class)
        compilerOptions {
            jvmTarget.set(JvmTarget.JVM_11)
        }
    }
    
    jvm("desktop")
    
    sourceSets {
        val commonMain by getting
        val desktopMain by getting
        val desktopTest by getting
        val androidMain by getting

        commonMain.dependencies {
            implementation(compose.runtime)
            implementation(compose.foundation)
            implementation(compose.material)
            implementation(compose.ui)
            implementation(compose.components.resources)
            implementation(compose.components.uiToolingPreview)
            implementation(libs.kotlinx.datetime)
            implementation(libs.jetbrains.navigation.compose)
            implementation(libs.kotlinx.serialization.json)
        }
        androidMain.dependencies {
            implementation(libs.compose.ui.tooling.preview)
            implementation(libs.androidx.activity.compose)
        }
        desktopMain.dependencies {
            implementation(compose.desktop.currentOs)
            implementation(libs.kotlin.logging.jvm)
            implementation(libs.slf4j.simple)
        }
        desktopTest.dependencies {
            implementation(libs.junit.jupiter.api)
            runtimeOnly(libs.junit.jupiter.engine)
        }
    }
}

android {
    namespace = "com.github.dzmitrykdev.malevich"
    compileSdk = libs.versions.android.compileSdk.get().toInt()

    sourceSets["main"].manifest.srcFile("src/androidMain/AndroidManifest.xml")
    sourceSets["main"].res.srcDirs("src/androidMain/res")
    sourceSets["main"].resources.srcDirs("src/commonMain/resources")

    defaultConfig {
        applicationId = "com.github.dzmitrykdev.malevich"
        minSdk = libs.versions.android.minSdk.get().toInt()
        targetSdk = libs.versions.android.targetSdk.get().toInt()
        versionCode = 1
        versionName = "1.0"
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
    buildTypes {
        getByName("release") {
            isMinifyEnabled = false
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    dependencies {
        debugImplementation(libs.compose.ui.tooling)
        coreLibraryDesugaring(libs.desugar.jdk.libs)
    }
}

compose.desktop {
    application {
        mainClass = "MainKt"

        nativeDistributions {
            targetFormats(TargetFormat.Dmg, TargetFormat.Msi, TargetFormat.Deb)
            packageName = "com.github.dzmitrykdev.malevich"
            packageVersion = "1.0.0"
        }
    }
}

detekt {
    buildUponDefaultConfig = true // preconfigure defaults
    allRules = false // activate all available (even unstable) rules.
    config.setFrom("${rootProject.projectDir}/config/detekt.yml") // point to your custom config defining rules to run, overwriting default behavior
    baseline = file("${rootProject.projectDir}/config/baseline.xml") // a way of suppressing issues before introducing detekt
    basePath = projectDir.absolutePath
    source.setFrom(files(projectDir, "src"))
}

tasks.withType<Detekt>().configureEach {
    reports {
        html.required.set(true) // observe findings in your browser with structure and code snippets
        xml.required.set(true) // checkstyle like format mainly for integrations like Jenkins
        sarif.required.set(false) // standardized SARIF format (https://sarifweb.azurewebsites.net/) to support integrations with GitHub Code Scanning
        md.required.set(true) // simple Markdown format
    }

    exclude("**/generated/**", "**/build/**", "**/*.gradle.kts")
}

tasks.withType<Detekt> {
    tasks.getByName("build").dependsOn(this)
}

tasks.register("detektAll") {
    allprojects {
        this@register.dependsOn(tasks.withType<Detekt>())
    }
}

dependencies {
    detektPlugins(libs.detekt.formatting)
}
