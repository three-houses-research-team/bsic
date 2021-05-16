import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

buildscript {
  repositories {
    maven("https://plugins.gradle.org/m2/")
  }
}

plugins {
  kotlin("jvm")
  application
  id("org.openjfx.javafxplugin") version "0.0.8"
  id("com.github.johnrengelman.shadow")
}

javafx {
  version = "11.0.2"
  modules = listOf("javafx.controls", "javafx.graphics")
}


repositories {
  mavenCentral()
  maven("https://kotlin.bintray.com/kotlinx")
}

sourceSets.main {
  java.srcDirs("src")
  resources.srcDirs("res")
}

tasks.withType<KotlinCompile> {
  kotlinOptions {
    jvmTarget = "11"
    freeCompilerArgs =
      freeCompilerArgs + "-Xuse-experimental=kotlin.ExperimentalUnsignedTypes" + "-Xuse-experimental=kotlin.ExperimentalStdlibApi"
  }
}

application {
  mainClassName  = "BsicAppKt"
}

tasks.withType<com.github.jengelman.gradle.plugins.shadow.tasks.ShadowJar> {
  manifest {
    attributes["Main-Class"] = application.mainClassName
  }
}


dependencies {
  implementation("org.jetbrains.kotlinx:kotlinx-cli:0.3.1")
  implementation(kotlin("reflect"))
  implementation("no.tornado:tornadofx:1.7.20")
  implementation(rootProject)

  implementation("org.controlsfx:controlsfx:8.40.14")
  api("com.github.thomasnield:rxkotlinfx:2.2.2")
  api("io.reactivex.rxjava2:rxkotlin:2.2.0")
  api("io.reactivex.rxjava2:rxjava:2.2.0")
  runtimeOnly("org.openjfx:javafx-graphics:${javafx.version}:win")
  runtimeOnly("org.openjfx:javafx-graphics:${javafx.version}:linux")
  runtimeOnly("org.openjfx:javafx-graphics:${javafx.version}:mac")

}
