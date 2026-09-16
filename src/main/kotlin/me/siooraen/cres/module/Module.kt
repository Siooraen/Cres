package me.siooraen.cres.module

import taboolib.common.LifeCycle
import taboolib.common.platform.Awake
import taboolib.platform.BukkitPlugin
import java.io.File

/**
 * @author Siooraen
 * @since 2022/2/3 10:27
 */
interface Module {

    val name: String

    val id: String

    val priority: Int
        get() = 1

    val state: ModuleState

    data class ModuleInfo(
        val name: String,
        val id: String,
        val state: ModuleState
    )

    companion object {
        const val MODULE_PACKAGE = "me.siooraen.cres.module"
        val modules = mutableListOf<Module>()
        private val classLoader = BukkitPlugin.getPluginInstance()!!.javaClass.classLoader!!
        private val resources = classLoader.getResources(MODULE_PACKAGE.replace(".", "/"))

        fun getModules(): List<ModuleInfo> {
            return modules.map { ModuleInfo(it.name, it.id, it.state) }
        }
    }

    @Awake(LifeCycle.ENABLE)
    fun loadModules() {
        while (resources.hasMoreElements()) {
            val resource = resources.nextElement()
            if (resource.protocol == "file") {
                File(resource.file).listFiles()?.forEach {
                    if (it.name.endsWith(".kt")) {
                        val loadedClass = Class.forName("$MODULE_PACKAGE.${it.nameWithoutExtension}")
                        if (this.javaClass.isAssignableFrom(loadedClass)) {
                            modules.add(loadedClass.cast(Module) as Module)
                        }
                    }
                }
            }
        }
    }
}