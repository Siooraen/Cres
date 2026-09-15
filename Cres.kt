package me.siooraen.cres

import me.siooraen.cres.util.event.PlayerLeaveServerEvent
import org.bukkit.Bukkit
import taboolib.common.platform.Plugin
import taboolib.module.configuration.Config
import taboolib.module.configuration.Configuration


object Cres : Plugin() {

    @Config
    lateinit var conf: Configuration
        private set

    override fun onDisable() {
        Bukkit.getOnlinePlayers().forEach { PlayerLeaveServerEvent(it).call() }
    }
}