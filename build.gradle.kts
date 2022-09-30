import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
  kotlin("jvm") version "1.5.31"
  kotlin("plugin.serialization") version "1.5.31"

  id("com.github.johnrengelman.shadow") version "7.1.0"

  application
}

repositories {
  mavenCentral()
}

fun kotlinx(name: String, version: String): String = "org.jetbrains.kotlinx:kotlinx-$name:$version"

dependencies {
  api("org.jetbrains.kotlinx:kotlinx-serialization-json:1.3.0")

  @Suppress("GradlePackageUpdate")
  api("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.5.2")
  api("io.fabric8:kubernetes-client:5.8.0")

  implementation("com.github.ajalt.clikt:clikt:3.3.0")

  implementation("org.slf4j:slf4j-api:1.7.32")
  implementation("org.slf4j:slf4j-simple:1.7.32")

  testImplementation("org.junit.jupiter:junit-jupiter-api:5.8.1")
  testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine:5.8.1")
  testImplementation("org.testcontainers:testcontainers:1.17.4")
  testImplementation("org.testcontainers:junit-jupiter:1.16.0")
  testImplementation(kotlin("test", "1.5.31"))
}

tasks.withType<KotlinCompile> {
  kotlinOptions.jvmTarget = "11"
}

tasks.test {
  useJUnitPlatform()
  reports.html.required.set(true)
  reports.junitXml.required.set(true)
}

application {
  mainClass.set("io.kosl.Main")
}

distributions.main.configure {
  contents {
    from("examples") {
      into("examples")
    }
  }
}

tasks.withType<Wrapper> {
  gradleVersion = "7.2"
  distributionType = Wrapper.DistributionType.ALL
}
