package me.siooraen.cres.module.friend

import me.siooraen.cres.module.Module
import me.siooraen.cres.module.ModuleState

object Friend : Module {

    override val name: String
        get() = "好友"

    override val id: String
        get() = "Friend"

    override val state: ModuleState
        get() = ModuleState.RUNNING
}