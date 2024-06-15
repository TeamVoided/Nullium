@file:Suppress("PropertyName", "VariableNaming")

import org.jetbrains.kotlin.gradle.dsl.JvmTarget
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    alias(libs.plugins.fabric.loom)
    alias(libs.plugins.kotlin.jvm)
    alias(libs.plugins.kotlinx.serialization)
    alias(libs.plugins.iridium)
    alias(libs.plugins.iridium.publish)
    alias(libs.plugins.iridium.upload)
}

group = property("maven_group")!!
version = property("mod_version")!!
base.archivesName.set(property("archives_base_name") as String)
description = property("description") as String

val modid: String by project
val mod_name: String by project
val modrinth_id: String? by project
val curse_id: String? by project

repositories {
    maven("https://teamvoided.org/releases")
    maven("https://maven.nucleoid.xyz")
    mavenCentral()
}

modSettings {
    modId(modid)
    modName(mod_name)

    entrypoint("main", "org.teamvoided.nullium.Nullium::commonInit")
    entrypoint("fabric-datagen", "org.teamvoided.nullium.data.gen.NulliumData")
    mixinFile("$modid.mixins.json")
    accessWidener("$modid.accesswidener")
}

dependencies {
    modImplementation(fileTree("libs"))
    modImplementation(libs.farrow)
    include(libs.farrow)
    modImplementation(libs.server.translations.api)
    include(libs.server.translations.api)


//    modImplementation(libs.reef)
}

loom {
    splitEnvironmentSourceSets()

    runs {
        create("DataGen") {
            client()
            ideConfigGenerated(true)
            vmArg("-Dfabric-api.datagen")
            vmArg("-Dfabric-api.datagen.output-dir=${file("src/main/generated")}")
            vmArg("-Dfabric-api.datagen.modid=${modid}")
            runDir("build/datagen")
        }

        create("TestWorld") {
            client()
            ideConfigGenerated(true)
            runDir("run")
            programArgs("--quickPlaySingleplayer", "test")
        }
    }
}

sourceSets["main"].resources.srcDir("src/main/generated")

tasks {
    val targetJavaVersion = 21
    withType<JavaCompile> {
        options.encoding = "UTF-8"
        options.release.set(targetJavaVersion)
    }

    withType<KotlinCompile>().all {
        compilerOptions.jvmTarget = JvmTarget.JVM_21
    }

    java {
        toolchain.languageVersion.set(JavaLanguageVersion.of(JavaVersion.toVersion(targetJavaVersion).toString()))
        withSourcesJar()
    }
}

publishScript {
    releaseRepository("TeamVoided", "https://maven.teamvoided.org/releases")
    publication(modSettings.modId(), false)
    publishSources(true)
}

uploadConfig {
//    debugMode = true
    modrinthId = modrinth_id
    curseId = curse_id

    changeLog = " - ender eyes locate citys\n" +
            " - #wither_rose_placable tag added\n" +
            " - #enderman_placable tag added\n" +
            " - removed soul sand and soil from #cane_support tag\n" +
            " - addded #support/small/bottom and #support/small/top tags\n"

    versionOverrides = listOf("1.20.5", "1.20.6")

    // FabricApi
    modrinthDependency("P7dR8mSH", uploadConfig.REQUIRED)
    curseDependency("fabric-api", uploadConfig.REQUIRED)
    // Fabric Language Kotlin
    modrinthDependency("Ha28R6CL", uploadConfig.REQUIRED)
    curseDependency("fabric-language-kotlin", uploadConfig.REQUIRED)

}
