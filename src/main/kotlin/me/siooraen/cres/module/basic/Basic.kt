package me.siooraen.cres.module.basic

import me.siooraen.cres.module.Module
import me.siooraen.cres.module.ModuleState
import taboolib.module.configuration.Config
import taboolib.module.configuration.Configuration

/**
 * @author Siooraen
 * @since 2022/2/5 16:48
 */
object Basic: Module {

    override val name: String
        get() = "基础"

    override val id: String
        get() = "Basic"

    override val priority: Int
        get() = -1

    override val state: ModuleState
        get() = ModuleState.RUNNING

    @Config(migrate = true, value = "module/basic.yml")
    lateinit var basicConf: Configuration
        private set

    val voidProtect by lazy {
        basicConf.getBoolean("void-protect", false)
    }

    val allowCraft by lazy {
        basicConf.getBoolean("allow-craft", false)
    }

    val allowCraftDisplay by lazy {
        basicConf.getBoolean("allow-craft-display", false)
    }

    val blockInventory by lazy {
        basicConf.getStringList("block-inventory")
    }


    val blockInteract by lazy {
        basicConf.getStringList("block-interact")
    }

    val blockFeatures by lazy {
        basicConf.getStringList("block-features")
    }

    val blockTeleport by lazy {
        basicConf.getStringList("block-teleport")
    }
}