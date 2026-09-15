package me.siooraen.cres.module

/**
 * @author Siooraen
 * @since 2022/2/2 18:44
 */
enum class ModuleState(val display: String) {

    RUNNING("运行中"), DEVELOPING("开发中"), TESTING("测试中"), REWRITING("重写中")
}