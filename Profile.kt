package me.siooraen.cres.module.profile

import me.siooraen.cres.module.Module
import me.siooraen.cres.module.ModuleState
import org.bukkit.entity.Player

/**
 * @author Siooraen
 * @since 2022/4/30 10:29
 */
object Profile : Module {

    override val name: String
        get() = "个人资料"

    override val id: String
        get() = "Profile"

    override val state: ModuleState
        get() = ModuleState.RUNNING

    fun Player.getStatus(): ProfileStatusType {
        return if (!this.isOnline) {
            ProfileStatusType.OFFLINE
        } else {
            ProfileStatusType.ONLINE
        }
    }
}