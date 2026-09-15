package me.siooraen.cres.module.motd

import me.siooraen.cres.module.Module
import me.siooraen.cres.module.ModuleState
import taboolib.module.configuration.Config
import taboolib.module.configuration.Configuration

/**
 * @author Siooraen
 * @since 2022/2/5 12:18
 */
object Motd: Module {

    override val name: String
        get() = "Motd"

    override val id: String
        get() = "Motd"

    override val state: ModuleState
        get() = ModuleState.RUNNING

    @Config(value = "module/motd/motd.yml")
    lateinit var motdConf: Configuration
        private set
}