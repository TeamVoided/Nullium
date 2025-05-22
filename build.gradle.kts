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

repositories {
    maven("https://teamvoided.org/releases")
    maven("https://teamvoided.org/snapshots")
    maven("https://maven.nucleoid.xyz")
    maven("https://maven.terraformersmc.com/") { name = "Terraformers" }
    maven("https://maven.fzzyhmstrs.me/") { name = "FzzyMaven" }
    maven("https://api.modrinth.com/maven")
    mavenCentral()
    mavenCentral()
}

modSettings {
    entrypoint("main", "org.teamvoided.nullium.Nullium::commonInit")
    entrypoint("fabric-datagen", "org.teamvoided.nullium.data.gen.NulliumData")
    mixinFile("${modId()}.mixins.json")
    accessWidener("${modId()}.accesswidener")
}

dependencies {
    modImplementation(fileTree("libs"))
    // Dependencies
    modImplementation(libs.server.translations.api)
    include(libs.server.translations.api)

    modImplementation(libs.fzzy.config)

    // QoL
    modImplementation(libs.modmenu)

    modCompileOnly("${libs.emi.get()}:api")
    modLocalRuntime(libs.emi)

    // Testing
    modImplementation(libs.creative.works)

}

val username = "vDev"
val uuid: String? = null

loom {
    splitEnvironmentSourceSets()
    runs {
        named("client") {
            programArgs("--username", username)
            uuid?.let { programArgs("--uuid", uuid) }
        }

        create("TestWorld") {
            client()
            ideConfigGenerated(true)
            runDir("run")
            programArgs("--quickPlaySingleplayer", "test", "--username", username)
            uuid?.let { programArgs("--uuid", uuid) }
        }

        create("DataGen") {
            client()
            ideConfigGenerated(true)
            vmArg("-Dfabric-api.datagen")
            vmArg("-Dfabric-api.datagen.output-dir=${file("src/main/generated")}")
            vmArg("-Dfabric-api.datagen.modid=${modSettings.modId()}")
            runDir("build/datagen")
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
    modrinthId = "hLiEnDPK"
    curseId = "1007835"

    changeLog = File("changelog.md").readText()

    // FabricApi
    modrinthDependency("P7dR8mSH", uploadConfig.REQUIRED)
    curseDependency("fabric-api", uploadConfig.REQUIRED)
    // Fabric Language Kotlin
    modrinthDependency("Ha28R6CL", uploadConfig.REQUIRED)
    curseDependency("fabric-language-kotlin", uploadConfig.REQUIRED)

}
