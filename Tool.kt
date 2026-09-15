package me.siooraen.cres.module.tool

import me.siooraen.cres.module.Module
import me.siooraen.cres.module.ModuleState

/**
 * @author Siooraen
 * @since 2023/7/23 10:54
 */
object Tool : Module {

    override val name: String
        get() = "工具"

    override val id: String
        get() = "Tool"

    override val state: ModuleState
        get() = ModuleState.RUNNING
}