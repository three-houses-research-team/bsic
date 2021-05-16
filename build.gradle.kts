import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

buildscript {
  repositories {
    maven("https://plugins.gradle.org/m2/")
  }
}

plugins {
  kotlin("jvm") version "1.4.21"
  application
  id("com.github.johnrengelman.shadow") version "6.1.0"
  id("com.github.breadmoirai.github-release") version "2.2.12"
}

version = "nightly-20210403"

repositories {
  mavenCentral()
  maven("https://kotlin.bintray.com/kotlinx")
}

sourceSets.main {
  java.srcDirs("src")
  resources.srcDirs("res")
}
sourceSets.test {
  java.srcDirs("test")
  resources.srcDirs("testRes")
}

tasks.withType<KotlinCompile> {
  kotlinOptions {
    jvmTarget = "11"
    freeCompilerArgs =
      freeCompilerArgs + "-Xuse-experimental=kotlin.ExperimentalUnsignedTypes" + "-Xuse-experimental=kotlin.ExperimentalStdlibApi"
  }
}

tasks.withType<Test> {
  useJUnitPlatform()
}

application {
  mainClassName  = "MainKt"
}

dependencies {
  implementation("org.jetbrains.kotlinx:kotlinx-cli:0.3.1")
  implementation(kotlin("reflect"))
//  implementation(kotlin("stdlib-jdk8"))

  testImplementation("org.junit.jupiter:junit-jupiter-api:5.7.1")
  testImplementation("io.kotest:kotest-runner-junit5:4.4.3")
  testImplementation("io.kotest:kotest-assertions-core:4.4.3")
  testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine:5.7.1")
}

githubRelease {
  token( property("bsic.github.token").toString())
  owner( "three-houses-research-team")
  repo("bsic")
  tagName( version.toString())
  targetCommitish( "main")
  releaseName(version.toString())
  body( "")
  draft( true)
  prerelease( false)
  releaseAssets( "build/libs/bsic-${version}-all.jar")
  overwrite( false)
  dryRun( false)
  apiEndpoint( "https://api.github.com")
}
tasks["githubRelease"].dependsOn(":shadowJar")
