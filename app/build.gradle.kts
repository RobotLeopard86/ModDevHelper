plugins {
    application
	id("org.openjfx.javafxplugin") version "0.1.0"
}

repositories {
    mavenCentral()
}

dependencies {
    implementation("com.google.guava:guava:32.1.1-jre")
	implementation("com.google.code.gson:gson:2.8.6")
}

java {
    toolchain {
        languageVersion.set(JavaLanguageVersion.of(17))
    }
}

application {
    mainClass.set("net.rl86.mdh.Main")
}

javafx {
    version = "17"    
    modules("javafx.controls")
}

tasks.jar {
    manifest {
        attributes(
            "Main-Class" to "net.rl86.mdh.Main"
        )
    }
}

tasks.distTar {
    archiveBaseName.set(rootProject.name + "-0.1.0")

    from("src/main/resources/images/appicon.png") {
        into(rootProject.name + "-0.1.0")
    }
}

tasks.distZip {
    archiveBaseName.set(rootProject.name + "-0.1.0")

    from("src/main/resources/images/appicon.png") {
        into(rootProject.name + "-0.1.0")
    }
}
