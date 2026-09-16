import io.izzel.taboolib.gradle.*

plugins {
    java
    id("io.izzel.taboolib") version "2.0.38"
    id("org.jetbrains.kotlin.jvm") version "2.4.0"
}

taboolib {
    description {
        contributors {
            name("Siooraen")
        }
        dependencies {
            name("Adyeshach")
            name("PlaceholderAPI")
        }
    }
    env {
        install(Basic, CommandHelper)
        install(MinecraftChat, MinecraftEffect)
        install(Bukkit, BukkitHook, BukkitUtil, BukkitNavigation, BukkitUI)
        install(BukkitNMSUtil, BukkitNMSDataSerializer, BukkitNMSEntityAI, BukkitNMSItemTag)
        install(Kether)
    }
    version { taboolib = "6.3.0-75b18a2" }
}

dependencies {
    compileOnly("ink.ptms.core:v12004:12004:universal")
    compileOnly("ink.ptms.core:v12004:12004:mapped")
    compileOnly(kotlin("stdlib"))
    compileOnly(fileTree("libs"))
}