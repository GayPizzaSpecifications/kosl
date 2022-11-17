import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
  kotlin("jvm") version "1.7.21"
  kotlin("plugin.serialization") version "1.7.21"

  id("com.github.johnrengelman.shadow") version "7.1.2"

  application
}

repositories {
  mavenCentral()
}

fun kotlinx(name: String, version: String): String = "org.jetbrains.kotlinx:kotlinx-$name:$version"

dependencies {
  api("org.jetbrains.kotlinx:kotlinx-serialization-json:1.4.1")

  api("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.6.4")
  api("io.fabric8:kubernetes-client:6.2.0")

  implementation("com.github.ajalt.clikt:clikt:3.5.0")

  implementation("org.slf4j:slf4j-api:2.0.3")
  implementation("org.slf4j:slf4j-simple:2.0.3")

  testImplementation("org.junit.jupiter:junit-jupiter-api:5.9.1")
  testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine:5.9.1")
  testImplementation("org.testcontainers:testcontainers:1.17.6")
  testImplementation("org.testcontainers:junit-jupiter:1.17.4")
  testImplementation(kotlin("test", "1.7.21"))
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
  gradleVersion = "7.5.1"
  distributionType = Wrapper.DistributionType.ALL
}
