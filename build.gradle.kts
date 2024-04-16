import org.jetbrains.compose.compose
import org.jetbrains.compose.desktop.application.dsl.TargetFormat
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    kotlin("multiplatform")
    id("org.jetbrains.compose").version("1.6.1")
    id("org.jetbrains.kotlin.plugin.serialization").version("1.9.21")
}

group = "no.agens.bingo"
version = "1.0-SNAPSHOT"

repositories {
    google()
    mavenCentral()
    maven("https://maven.pkg.jetbrains.space/public/p/compose/dev")
}

kotlin {
    jvm {
        compilations.all {
            kotlinOptions.jvmTarget = "17"
        }
        withJava()
    }
    sourceSets {
        val jvmMain by getting {
            dependencies {
                implementation(compose.desktop.currentOs)
                implementation("dev.gitlive:firebase-firestore:1.12.0")
                implementation("dev.gitlive:firebase-common:1.12.0")
                implementation("dev.gitlive:firebase-auth:1.12.0")
                implementation("dev.gitlive:firebase-functions:1.12.0")
                implementation ("org.jetbrains.kotlinx:kotlinx-coroutines-swing:1.8.0")
                implementation("org.slf4j:slf4j-api:1.6.1")
                implementation("org.slf4j:slf4j-simple:1.6.1")


            }
        }
        val jvmTest by getting
    }
}

compose.desktop {
    application {
        mainClass = "MainKt"
        nativeDistributions {
            targetFormats(TargetFormat.Dmg, TargetFormat.Msi, TargetFormat.Deb)
            packageName = "Bingo"
            packageVersion = "1.0.0"
        }
    }
}
