import java.util.*

plugins {
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.shadowJar)
    alias(libs.plugins.jetbrainsCompose)
    application
    java
}

kotlin {
    jvm {
        withJava()
    }
    jvmToolchain(16)
    
    sourceSets {
        val jvmMain by getting
        
        commonMain.dependencies {
            implementation(compose.material)
            implementation(compose.ui)
        }
        jvmMain.dependencies {
            implementation(libs.poet)
            implementation(libs.jackson.kotlin)
            implementation(libs.jackson.xml)
            implementation(libs.clikt)
        }
    }
}

application {
    mainClass.set("com.github.trueddd.MainKt")
}

tasks.shadowJar {
    archiveFileName.set("vektr.jar")
    minimize {
        exclude("META-INF/*.DSA")
        exclude("META-INF/*.SF")
        exclude(dependency("com.fasterxml.woodstox:.*:.*"))
        exclude(dependency("org.jetbrains.kotlin:kotlin-reflect:.*"))
    }
    doLast {
        val propertiesFile = project.rootProject.file("local.properties")
        if (!propertiesFile.exists()) {
            return@doLast
        }
        val targetPath = Properties().apply {
            load(propertiesFile.inputStream())
        }.getProperty("target_path") ?: return@doLast
        copy {
            from(archiveFile)
            into(targetPath)
        }
    }
}
