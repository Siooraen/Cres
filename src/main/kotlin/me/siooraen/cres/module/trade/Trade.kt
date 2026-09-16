package me.siooraen.cres.module.trade

import me.siooraen.cres.module.Module
import me.siooraen.cres.module.ModuleState

/**
 * @author Siooraen
 * @since 2023/8/7 17:40
 */
object Trade : Module {

    override val name: String
        get() = "交易"

    override val id: String
        get() = "Trade"

    override val state: ModuleState
        get() = ModuleState.RUNNING
}